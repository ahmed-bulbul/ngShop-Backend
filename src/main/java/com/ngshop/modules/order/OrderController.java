package com.ngshop.modules.order;


import com.ngshop.modules.order.OrderItem.OrderItem;
import com.ngshop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = {"/orders" }, method = POST)
    public Order createOrder(@RequestBody Order order) {
        order.setUsername(UserUtil.getLoginUser());
        return orderService.createOrder(order);
    }

    @RequestMapping(value = {"/orders" }, method = GET)
    public List<Order> orders(){
        return orderService.getOrders();
    }

    @RequestMapping(value = "/orders/get/count", method = GET)
    public ResponseEntity<?> getTotalOrders(){
        Integer totalOrders = orderService.getTotalOrders();
        Map<String, Object> response = new HashMap<>();
        response.put("orderCount", totalOrders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
