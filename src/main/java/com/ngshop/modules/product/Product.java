package com.ngshop.modules.product;

import com.ngshop.modules.category.Category;
import com.ngshop.modules.product.images.ProductImages;
import com.ngshop.modules.product.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String image;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String richDescription;
    private Integer countInStock;
    private String brand;
    private Double price;
    private String rating;
    private Integer numReviews;
    private Boolean isFeatured;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ProductImages> images;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "CATEGORY_ID",referencedColumnName = "ID")
    private Category category;

    //System log fields
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "CREATED_AT",updatable = false)
    private Date dateCreated;
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "UPDATED_AT",updatable = false)
    private Date dateUpdated;
    @Column(name = "CREATION_USER")
    String creationUser;
    @Column(name = "LAST_UPDATE_USER")
    String lastUpdateUser;

}
