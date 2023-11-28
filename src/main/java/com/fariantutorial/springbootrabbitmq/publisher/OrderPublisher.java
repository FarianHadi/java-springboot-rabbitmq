package com.fariantutorial.springbootrabbitmq.publisher;

import com.fariantutorial.springbootrabbitmq.config.MessagingConfig;
import com.fariantutorial.springbootrabbitmq.dto.Order;
import com.fariantutorial.springbootrabbitmq.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {
    @Autowired
    private RabbitTemplate template;

    @PostMapping
    public String bookOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        // restaurantService
        // paymentService
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed successfully in ");
        template.convertAndSend(MessagingConfig.EX, MessagingConfig.ROUTING, orderStatus);
        return "Success !!";
    }
}
