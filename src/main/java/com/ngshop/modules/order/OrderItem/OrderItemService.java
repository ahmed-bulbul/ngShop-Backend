package com.ngshop.modules.order.OrderItem;

import com.ngshop.modules.order.Order;
import com.ngshop.modules.product.Product;
import com.ngshop.modules.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public void saveOrderItems(List<OrderItem> orderItems, Order order) {

        List<OrderItem> entity = orderItems.stream().map(item -> {
            item.setOrder(order);
            item.setProduct(item.getProduct());
            Optional<Product> product = productRepository.findById(item.getProduct());
            product.ifPresent(item::setProductInstance);
            return item;
        } ).collect(Collectors.toList());
        repository.saveAll(entity);
    }
}
