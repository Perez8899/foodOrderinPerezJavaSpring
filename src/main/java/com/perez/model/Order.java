package com.perez.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne                     //many order same user
    private User customer;

    @JsonIgnore                    //ignore I don't need it within the order
    @ManyToOne                     //many orders, a restaurant
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;           //creation date, for when the order arrives

    @ManyToOne                        //many orders, in the same address
    private Address deliveryAddress;

    //	@JsonIgnore
    @OneToMany
    private List<OrderItem> items;

    @OneToOne
    private Payment payment;

    private int totalItem;

    private int totalPrice;


}
