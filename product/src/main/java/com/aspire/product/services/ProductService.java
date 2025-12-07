package com.aspire.product.services;

import com.aspire.product.dtos.PaginatedResult;
import com.aspire.product.models.Product;
import com.aspire.product.repositories.ProductRepository;
import com.aspire.product.dtos.CreateProductDto;
import com.aspire.product.responses.ProductResponse;
import com.aspire.product.responses.SuccessResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    @Transactional
    public SuccessResponse createProduct(CreateProductDto createProductDto) {
        if(productRepo.existsBySlug(createProductDto.getSlug())) {
            throw new DuplicateKeyException("Slug already exist");
        }
        Product newProduct = Product.builder()
                .name(createProductDto.getName())
                .slug(createProductDto.getSlug())
                .userId(createProductDto.getUserId())
                .description(createProductDto.getDescription())
                .price(createProductDto.getPrice())
                .ratings(createProductDto.getRating())
                .images(createProductDto.getImages())
                .colors(createProductDto.getColors())
                .sizes(createProductDto.getSizes())
                .category(createProductDto.getCategory())
                .build();

        productRepo.save(newProduct);
        return new SuccessResponse("Product successfully created");
    }

    public PaginatedResult<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var result = productRepo.findAll(pageable).map(ProductResponse::from);
        return new PaginatedResult<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    public ProductResponse getSingleProduct(long id) {
        return productRepo.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
    }

    @Transactional
    public ProductResponse updateProduct(long id, CreateProductDto productDto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
        product.setId(product.getId());
        product.setName(productDto.getName());
        product.setSlug(productDto.getSlug());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setImages(productDto.getImages());
        product.setColors(productDto.getColors());
        product.setSizes(productDto.getSizes());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepo.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public SuccessResponse deleteProduct(long id) {
        productRepo.deleteById(id);
        return new SuccessResponse("Product successfully deleted");
    }
}
