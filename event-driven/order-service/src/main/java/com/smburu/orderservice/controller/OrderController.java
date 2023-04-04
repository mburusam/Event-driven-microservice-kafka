package com.smburu.orderservice.controller;

import com.smburu.basedomains.dto.Order;
import com.smburu.basedomains.dto.OrderEvent;
import com.smburu.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private  final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }


    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent =new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order status in pending state");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return "Order Placed successfully...";

    }

}
