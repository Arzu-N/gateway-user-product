package org.example.product.service;

import lombok.RequiredArgsConstructor;
import org.example.product.dao.entity.Product;
import org.example.product.dao.repository.ProductRepository;
import org.example.product.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;

    public void addProduct(ProductDto dto){
        productRepository.save(Product.builder().
                productName(dto.getProductName())
                        .price(dto.getPrice())
                                .userId(dto.getUserId())
                .build());
    }

    public List<ProductDto>getProducts(){
        return productRepository.findAll().stream().map(p->ProductDto.builder()
                .productName(p.getProductName())
                .price(p.getPrice())
                .userId(p.getUserId())
                .build()).toList();
    }
}
