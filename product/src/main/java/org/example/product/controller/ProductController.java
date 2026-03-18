package org.example.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.product.dto.ProductDto;
import org.example.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void>addProduct(@RequestBody ProductDto dto,
                                          @RequestHeader("X-User-Id")Long userId,
                                          @RequestHeader("X-Role")String role){
        if(!"ADMIN".equals(role)){
            throw new RuntimeException("Only admin can create product");
        }
        dto.setUserId(userId);
        productService.addProduct(dto);
        System.out.println(userId);
        System.out.println(role);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>>getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
}
