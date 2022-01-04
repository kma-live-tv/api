package com.nopain.livetv.controller;

import com.nopain.livetv.properties.StripeProperties;
import com.nopain.livetv.service.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/stripe")
@RequiredArgsConstructor
@Slf4j
public class StripeController {
    private final OrderService orderService;
    private final StripeProperties properties;

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> webhook(HttpServletRequest request, @RequestBody String payload) throws SignatureVerificationException {
        String sigHeader = request.getHeader("Stripe-Signature");
        var endpointSecret = properties.getEndpointSecret();

        Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        if (event.getType().equals("payment_intent.succeeded")) {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            var object = dataObjectDeserializer.getObject();

            if (object.isPresent()) {
                stripeObject = object.get();
            }

            if (stripeObject != null) {
                var intent = (PaymentIntent) stripeObject;
                orderService.completeOrder(intent.getId());
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("");
    }
}
