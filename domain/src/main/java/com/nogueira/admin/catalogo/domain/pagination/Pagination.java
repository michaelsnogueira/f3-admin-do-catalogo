package com.nogueira.admin.catalogo.domain.pagination;

import java.util.List;

public record Pagination<T>(
        int currentPage,
        int parPage,
        long total,
        List<T> items
) {
}
