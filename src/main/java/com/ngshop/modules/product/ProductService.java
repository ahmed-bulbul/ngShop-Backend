package com.ngshop.modules.product;

import com.ngshop.exception.CustomException;
import com.ngshop.modules.multimedia.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private MultimediaService storageService;

    @Autowired
    private ProductRepository repository;


    public Product create(Product product) {
        return this.repository.save(product);
    }

    public String uploadImage(MultipartFile file) {
        String saveFileName="";
        String savePathFileName="";
        String fileName = file.getOriginalFilename();
        if (fileName !=null){
            int index = fileName.lastIndexOf('.');
            if (index>0){
                String extension = fileName.substring(index+ 1);
                extension=extension.toLowerCase();
                saveFileName=System.currentTimeMillis()+"." + extension;
                savePathFileName="multimedia/product-pic/" + saveFileName;
            }
        }
        // store file in disk
        storageService.uploadFile(file,saveFileName);
        return savePathFileName;
    }

    public List<Product> getProducts() {
        return this.repository.findAll();
    }

    public Product updateProduct(Product entity) throws CustomException {
        return this.repository.save(entity);
    }

    public Product getProduct(Long id) throws CustomException {
        Product prodInst = repository.findById(id).orElseThrow(()
                -> new CustomException("Product not found for this id :: " + id));
        return this.repository.findById(id).get();
    }

    public ResponseEntity<?> delete(Long id) throws CustomException {
        Product prodInst = repository.findById(id).orElseThrow(()
                -> new CustomException("Product not found for this id :: " + id));
        repository.delete(prodInst);
        return ResponseEntity.ok(new CustomException("Successfully Deleted Data"));
    }

    public List<Product> getFeaturedProducts(Integer count) {
        return this.repository.findFirst4ByIsFeaturedTrue();
    }
}
