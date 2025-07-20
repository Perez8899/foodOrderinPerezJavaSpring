package com.perez.service;

import com.perez.model.Order;
import com.perez.model.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    public PaymentResponse generatePaymentLink(Order order) throws StripeException;
}
