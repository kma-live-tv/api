package com.nopain.livetv.service;

import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.model.Order;
import com.nopain.livetv.model.User;
import com.nopain.livetv.repository.OrderRepository;
import com.nopain.livetv.repository.UserRepository;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository repository;
    private final StripeService stripeService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final LivestreamService livestreamService;

    public Order create(Livestream livestream, User user, int amount) throws StripeException {
        var paymentIntent = stripeService.createPaymentIntent(amount * 20000L);

        var order = Order
                .builder()
                .livestream(livestream)
                .user(user)
                .amount(amount)
                .intentId(paymentIntent.getId())
                .intentClientSecret(paymentIntent.getClientSecret())
                .build();
        repository.save(order);

        return order;
    }

    public void completeOrder(String intentId) {
        var found = repository.findByIntentId(intentId);

        if (found.isEmpty()) {
            return;
        }

        var order = found.get();
        var livestream = order.getLivestream();
        var user = livestream.getUser();

        var balance = user.getBalance();
        user.setBalance(balance + order.getAmount() * 20000L);
        userRepository.save(user);

        notificationService.pushDonateReceived(user, order);
        livestreamService.commentGiveStars(livestream, order);

        var donator = order.getUser();

        log.info(
                String.format(
                        "User %d - %s give %d stars for User %d - %s",
                        donator.getId(),
                        donator.getUsername(),
                        order.getAmount(),
                        user.getId(),
                        user.getUsername()
                )
        );
    }
}
