package com.luv2code.ResturantSystem.payment;

import com.luv2code.ResturantSystem.entity.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {

//    @Value("${stripe.success.url}")
//    private String successUrl;
//
//    @Value("${stripe.cancel.url}")
//    private String cancelUrl;
    @Value("${stripe.secret.key}")
    private String secretKey;

    public String createCheckoutSession(Order order) throws StripeException {
        Stripe.apiKey = secretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment-success?orderId=" + order.getId())
                .setCancelUrl("http://localhost:8080/payment-cancel?orderId=" + order.getId())

                .putMetadata("orderId", String.valueOf(order.getId()))

                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("egp")
                                                .setUnitAmount((long) (order.getTotalAmount() * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData
                                                                .builder()
                                                                .setName("Restuarant Order # " + order.getId())
                                                                .build()
                                                ).build()
                                ).build()
                )
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }





}
