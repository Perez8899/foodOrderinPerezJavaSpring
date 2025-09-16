package com.perez.service;

import com.perez.Exception.CartException;
import com.perez.Exception.OrderException;
import com.perez.Exception.RestaurantException;
import com.perez.Exception.UserException;
import com.perez.model.*;
import com.perez.reporsitory.*;
import com.perez.request.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService{

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;


    //----------------------------methods----------------------------------------------------
    @Override
    public Order createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException { //, StripeException

        // Check if the address already exists for this user
// 1. Search for existing address
        Optional<Address> existingAddress = user.getAddresses().stream()
                .filter(addr ->
                        addr.getStreetAddress() != null &&
                                order.getDeliveryAddress() != null &&
                                order.getDeliveryAddress().getStreetAddress() != null &&
                                addr.getStreetAddress().equals(order.getDeliveryAddress().getStreetAddress())
                )
                .filter(addr ->
                        addr.getPostalCode() != null &&
                                order.getDeliveryAddress() != null &&
                                order.getDeliveryAddress().getPostalCode() != null &&
                                addr.getPostalCode().equals(order.getDeliveryAddress().getPostalCode())
                )
                .findFirst();


        // 2.Use existing address or save new one
        Address savedAddress;
        if (existingAddress.isPresent()) {
            savedAddress = existingAddress.get();
        } else {
            savedAddress = addressRepository.save(order.getDeliveryAddress());
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }


        Optional<Restaurant> restaurant = restaurantRepository.findById(order.getRestaurantId());
        if(restaurant.isEmpty()) {
            throw new RestaurantException("Restaurant not found with id "+order.getRestaurantId());
        }

        Order createdOrder = new Order();

        createdOrder.setCustomer(user);
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDIENTE");
        createdOrder.setRestaurant(restaurant.get());

        Cart cart = cartService.findCartByUserId(user.getId());


        double ivaRate =0.13;          // 13% IVA
        double platformFee = 200.0;       // platform commission
        double deliveryFee = 500.0;       // delivery cost

        double subtotal = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());

            double itemTotal = cartItem.getFood().getPrice() * cartItem.getQuantity();
            orderItem.setTotalPrice(itemTotal);

            subtotal += itemTotal;

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // Calculate total with VAT and fees
        double ivaAmount = subtotal * ivaRate;
        double totalAmount = subtotal + ivaAmount + platformFee + deliveryFee;

        createdOrder.setTotalAmount((long) totalAmount);
        createdOrder.setItems(orderItems);
        // --- End of calculation ---

        Order savedOrder = orderRepository.save(createdOrder);

        restaurant.get().getOrders().add(savedOrder);

        restaurantRepository.save(restaurant.get());



        return  createdOrder;

    }

    @Override
    public void cancelOrder(Long orderId) throws OrderException {
        Order order =findOrderById(orderId);
        if(order==null) {
            throw new OrderException("Order not found with the id "+orderId);
        }

        orderRepository.deleteById(orderId);

    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()) return order.get();

        throw new OrderException("Order not found with the id "+orderId);
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws OrderException {
        List<Order> orders=orderRepository.findAllUserOrders(userId);
        return orders;
    }

    @Override
    public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException {

        List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurantId);

        if(orderStatus!=null) {
            orders = orders.stream()
                    .filter(order->order.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws OrderException {
        Order order=findOrderById(orderId);

        System.out.println("--------- "+orderStatus);

        if(orderStatus.equals("EN CAMINO") || orderStatus.equals("ENTREGADO")
                || orderStatus.equals("COMPLETADO") || orderStatus.equals("PENDIENTE")) {

            order.setOrderStatus(orderStatus);
            Notification notification=notificationService.sendOrderStatusNotification(order);
            return orderRepository.save(order);
        }
        else throw new OrderException("Please Select A Valid Order Status");


    }

    @Override
    public List<Order> getOrdersByRestaurantAndDateRange(Long restaurantId, Date startDate, Date endDate) throws OrderException {
        List<Order> orders = orderRepository.findOrdersByRestaurantAndDateRange(restaurantId, startDate, endDate);
        if (orders.isEmpty()) {
            throw new OrderException("No hay órdenes en este rango de fechas para este restaurante.");
        }
        return orders;
    }



}
