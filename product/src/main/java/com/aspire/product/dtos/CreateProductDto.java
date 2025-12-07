package com.aspire.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    @NotBlank
    private String name;
    @NotNull
    private Long userId;
    private String slug;

    private String description;
    @NotNull
    private Double price;
    private Float rating;

    private String category;
    private List<String> images;
    private List<String> colors;
    private List<String> sizes;
}
