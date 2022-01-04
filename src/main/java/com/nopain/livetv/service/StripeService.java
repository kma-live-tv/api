package com.nopain.livetv.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeService {
    public PaymentIntent createPaymentIntent(long amount) throws StripeException {
        List<Object> paymentMethodTypes =
                new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", "vnd");
        params.put(
                "payment_method_types",
                paymentMethodTypes
        );

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return paymentIntent;
    }
}
