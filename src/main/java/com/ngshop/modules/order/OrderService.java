package com.ngshop.modules.order;

import com.ngshop.modules.order.OrderItem.OrderItem;
import com.ngshop.modules.order.OrderItem.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    public JdbcTemplate jdbcTemplate;



    public Order createOrder(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        Order entity =  repository.save(order);
        this.orderItemService.saveOrderItems(orderItems, entity);
        return entity;

    }
    public Integer getTotalOrders() {
        String sqlStr = "SELECT COUNT(ID) AS TOTAL_ORDER FROM ORDERS";
        return jdbcTemplate.queryForObject(sqlStr, Integer.class);
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }
}
