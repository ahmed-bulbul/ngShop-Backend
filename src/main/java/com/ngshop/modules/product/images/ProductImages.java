package com.ngshop.modules.product.images;


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
@Table(name = "PRODUCT_IMAGES")
public class ProductImages {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "PRODUCT_ID")
    private Product product;

}
