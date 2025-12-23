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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprintly.backend.dtos.ProductCreateDto;
import com.sprintly.backend.dtos.ProductDto;
import com.sprintly.backend.entities.Products;
import com.sprintly.backend.mappers.ProductMapper;
import com.sprintly.backend.repositories.CategoryRepository;
import com.sprintly.backend.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;



    // ===== GET ALL =====
    @GetMapping
    public List<ProductDto> getAllProducts() {

        return productRepository.findAllWithIsSale()
                .stream()
                .map(row -> {
                        Long id = (Long) row[0];
                        Long categoryId = (Long) row[1];
                        String image = (String) row[2];
                        String name = (String) row[3];
                        String categoryName = (String) row[4];
                        BigDecimal price = (BigDecimal) row[5];
                        Double rating = (Double) row[7];
                        Long reviews = (Long) row[8];
                        Boolean isNew = (Boolean) row[9];
                        Boolean isSale = (Boolean) row[10];
                        Integer stockQuantity = (Integer) row[11];
                        System.out.println(row[5]);

                        ProductDto dto = new ProductDto();
                        dto.setId(id);
                        dto.setTitle(name);
                        dto.setPrice(price);
                        dto.setCategory(categoryName);
                        dto.setIsSale(isSale);
                        dto.setCategoryId(categoryId);
                        dto.setRating(rating);
                        dto.setImage(image);
                        dto.setReviews(reviews != null ? reviews.intValue() : 0);
                        dto.setIsNew(isNew);
                        dto.setStockQuantity(stockQuantity);
                        return dto;
                })

                .toList();
    }

    // ===== GET BY ID =====
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductCreateDto dto) {

        var category = categoryRepository.findById(dto.getCategoryId()).orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        Products product = productMapper.toEntity(dto);
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        product.setStockQuantity(dto.getStockQuantity());

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update( @PathVariable Long id, @RequestBody ProductDto dto) {

        Products product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        var category = categoryRepository.findById(dto.getCategoryId()).orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        product.setName(dto.getTitle());
        // product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


 //=================search=================================
    @Transactional
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> search(
            @RequestParam String keyword) {

        var products = productRepository.search(keyword);

        var result = products.stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }
}
