package com.aspire.product.controllers;

import com.aspire.product.dtos.CreateProductDto;
import com.aspire.product.dtos.PaginatedResult;
import com.aspire.product.responses.ProductResponse;
import com.aspire.product.responses.SuccessResponse;
import com.aspire.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @NullMarked
    @PostMapping
    ResponseEntity<SuccessResponse> createProduct(@RequestBody @Valid CreateProductDto productDto) {
        return ResponseEntity.created(URI.create("/product")).body(productService.createProduct(productDto));
    }

    @NullMarked
    @GetMapping
    ResponseEntity<PaginatedResult<ProductResponse>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @NullMarked
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getSingleProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getSingleProduct(id));
    }

    @NullMarked
    @PutMapping("/{id}")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable long id, @RequestBody @Valid CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.updateProduct(id, createProductDto));
    }

    @NullMarked
    @DeleteMapping("/{id}")
    ResponseEntity<SuccessResponse> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

}
