package com.ngshop.modules.product.review;


import com.ngshop.modules.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REVIEWS")
public class Review {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String avatar;
    private String name;
    private String review;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "PRODUCT_ID")
    private Product product;


}
