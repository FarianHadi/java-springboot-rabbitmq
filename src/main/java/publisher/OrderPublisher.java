package publisher;

import config.MessagingConfig;
import dto.Order;
import dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {
    @Autowired
    private RabbitTemplate template;

    @PostMapping()
    public String bookOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        // restaurantService
        // paymentService
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed successfully in ");
        template.convertAndSend(MessagingConfig.EX, MessagingConfig.ROUTING, orderStatus);
        return "Success !!";
    }
}
