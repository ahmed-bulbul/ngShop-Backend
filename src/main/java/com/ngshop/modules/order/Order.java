package com.ngshop.modules.order;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngshop.modules.acl.auth.role.Role;
import com.ngshop.modules.order.OrderItem.OrderItem;
import com.ngshop.modules.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import com.ngshop.modules.acl.auth.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String status;
    private String shippingAddress1;
    private String shippingAddress2;
    private String city;
    private String zip;
    private String country;
    private String phone;
    private Double totalPrice;

    private String username;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "DATE_ORDERED")
    private Date dateOrdered;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


}
