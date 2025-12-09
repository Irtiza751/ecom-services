package com.aspire.product.dtos;

import java.util.List;

public record PaginatedResult<T>(
        List<T> data,
        int page,
        int size,
        long totalElements,
        int totalPage,
        boolean first,
        boolean last
) {}
