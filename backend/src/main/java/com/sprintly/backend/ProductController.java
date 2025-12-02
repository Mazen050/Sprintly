package com.sprintly.backend;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sprintly.backend.entities.Products;
import com.sprintly.backend.repositories.ProductRepository;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class ProductController{
    @Autowired
    ProductRepository productrepo;
    @GetMapping("/products")
    public List<Products> index() {
        return productrepo.findAll();
    }
 

    @PostMapping("/products")
    public Products postindex(@RequestBody Products prod) {
        return productrepo.save(prod);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Products> prodid(@PathVariable long id) {
        return productrepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable long id){
         productrepo.deleteById(id);
         return "Deleted successfully";
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<Products> putMethodName(@PathVariable long id, @RequestBody Products newProd) {
        return productrepo.findById(id)
            .map(product->{
                product.setName(newProd.getName());
                product.setCategory(newProd.getCategory());
                product.setDescription(newProd.getDescription());
                product.setPrice(newProd.getPrice());
                product.setStockQuantity(newProd.getStockQuantity());
                return ResponseEntity.ok(productrepo.save(product));
            })
            .orElse(ResponseEntity.notFound().build());


        
        
    }
}
