package com.ngshop.modules.product;


import com.ngshop.exception.CustomException;
import com.ngshop.modules.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = {"/products"}, method = POST)
    public Product product(@RequestParam(name = "name") String  name,
                           @RequestParam(name="brand") String brand,
                           @RequestParam(name="price") Double price,
                           @RequestParam(name="category") Category category,
                           @RequestParam(name="countInStock") Integer countInStock,
                           @RequestParam(name="description") String description,
                           @RequestParam(name="richDescription") String richDescription,
                           @RequestParam(name="image") MultipartFile image,
                           @RequestParam(name="isFeatured") Boolean isFeatured
                           ){
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setCategory(category);
        product.setCountInStock(countInStock);
        product.setDescription(description);
        product.setRichDescription(richDescription);
        product.setImage(this.service.uploadImage(image));
        product.setIsFeatured(isFeatured);
        return this.service.create(product);
    }

    @RequestMapping(value = {"/products", "/products/index" }, method = GET)
    public List<Product> products(){
        return this.service.getProducts();
    }

    @RequestMapping(value = {"/products/{id}" }, method = GET)
    public Product getProduct(@PathVariable Long id) throws CustomException {
        return this.service.getProduct(id);
    }

    @RequestMapping(value = {"/products/{id}" }, method = PUT)
    public Product updateProduct(@PathVariable Long id,
                                 @RequestParam(name = "name") String  name,
                                 @RequestParam(name="brand") String brand,
                                 @RequestParam(name="price") Double price,
                                 @RequestParam(name="category") Category category,
                                 @RequestParam(name="countInStock") Integer countInStock,
                                 @RequestParam(name="description") String description,
                                 @RequestParam(name="richDescription") String richDescription,
                                 @RequestParam(name="image",required = false) MultipartFile image,
                                 @RequestParam(name="isFeatured") Boolean isFeatured) throws CustomException {

        Product entity = productRepository.findById(id).orElseThrow(()
                -> new CustomException("Product not found for this id :: " + id));
        entity.setName(name);
        entity.setBrand(brand);
        entity.setPrice(price);
        entity.setCategory(category);
        entity.setCountInStock(countInStock);
        entity.setDescription(description);
        entity.setRichDescription(richDescription);
        if(image!=null) {
            entity.setImage(this.service.uploadImage(image));
        }
        entity.setIsFeatured(isFeatured);
        return this.service.updateProduct(entity);
    }

    @RequestMapping(value = {"/products/{id}" }, method = DELETE)
    public ResponseEntity<?> delete (@PathVariable Long id) throws CustomException {
        return this.service.delete(id);
    }

    //get featured products
    @RequestMapping(value = {"/products/get/featured/{count}"}, method = GET)
    public List<Product> getFeaturedProducts(@PathVariable Integer count){
        return this.service.getFeaturedProducts(count);
    }

}
