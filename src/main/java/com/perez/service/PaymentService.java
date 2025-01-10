package com.perez.service;

import com.perez.model.Order;
import com.perez.model.PaymentResponse;

public interface PaymentService {

    public PaymentResponse generatePaymentLink(Order order) throws Exception; //hrows StripeException;
}
