package com.sprintly.backend;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintly.backend.dtos.CheckoutRequest;
import com.sprintly.backend.dtos.CheckoutResponse;
import com.sprintly.backend.entities.OrderItems;
import com.sprintly.backend.entities.OrderStatus;
import com.sprintly.backend.entities.Orders;
import com.sprintly.backend.repositories.CartRepository;
import com.sprintly.backend.repositories.OrderRepository;
import com.sprintly.backend.services.AuthService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;

    @Value("${stripe.websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecret}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) throws StripeException {
        System.out.println(request.getCartId());
        var cart = cartRepository.getCartWithItems(request.getCartId());
        
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart not found"));
        }

        if (cart.get().getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty"));
        }
        
        // IF valid cart then ceate order
        var order = new Orders();
        // order.setTotalPrice(cart.get().getPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(authService.getCurrentUser());
        
        BigDecimal totalPrice = cart.get().getItems().stream()
                .map(item ->
                    item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);


        cart.get().getItems().forEach(item -> {
            var orderItem = new OrderItems();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            orderItem.setPriceAtOrder(item.getProduct().getPrice());
            order.getItems().add(orderItem);
        });

        orderRepository.save(order);

        // CALL CLEAR CART SERVICE HERE (NOT IMPLEMENTED YET)


        try {
            var builder = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(websiteUrl + "/checkout/success?orderId=" + order.getId())
            .setCancelUrl(websiteUrl + "/checkout/cancel?orderId=" + order.getId())
            .putMetadata("order_id", order.getId().toString())
            .setPaymentIntentData(
            SessionCreateParams.PaymentIntentData.builder()
            .putMetadata("order_id", order.getId().toString())
            .build());
                
            // adds order items to stripe payment order
            order.getItems().forEach((orderItem) -> {
                var LineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(orderItem.getQuantity()))
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("usd")
                    .setUnitAmount(orderItem.getPriceAtOrder().multiply(BigDecimal.valueOf(100)).longValue())
                    .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(orderItem.getProduct().getName())
                        .build()
                    )
                    .build()
                ).build();
            builder.addLineItem(LineItem);
            });

            var session = Session.create(builder.build());
            // cartService.clearCart(cart.getId());

            return ResponseEntity.ok(new CheckoutResponse(order.getId(), session.getUrl()));
        }
        catch (StripeException ex) {
            orderRepository.delete(order);
            throw ex;
        }

    }
    
    @PostMapping("/webhook")
    public ResponseEntity<Void> handWebhook(
        @RequestHeader("Stripe-Signature") String signature,
        @RequestBody String payload
    ) {
        
        try {
            var event = Webhook.constructEvent(payload, signature, webhookSecret);
                    
            var deserializer = event.getDataObjectDeserializer();

            var stripeObject = deserializer
                .deserializeUnsafe(); // we can be sure of the type based on event type
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    var paymentIntent = (PaymentIntent) stripeObject;
                    if (paymentIntent != null) {
                        var orderId = paymentIntent.getMetadata().get("order_id");
                        var order = orderRepository.findById(Long.valueOf(orderId)).orElseThrow();
                        
                        order.setStatus(OrderStatus.PAID);
                        orderRepository.save(order);
                    }
                    System.out.println(paymentIntent);

                    break;
                case "payment_intent.payment_failed":
                    var failedSession = (Session) stripeObject;
                    var failedOrderId = Long.valueOf(failedSession.getCancelUrl()
                        .split("orderId=")[1]
                    );
                    var failedOrder = orderRepository.findById(failedOrderId).orElse(null);
                    if (failedOrder != null) {
                        failedOrder.setStatus(OrderStatus.CANCELLED);
                        orderRepository.save(failedOrder);
                    }
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
                }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return null;
    }


}
