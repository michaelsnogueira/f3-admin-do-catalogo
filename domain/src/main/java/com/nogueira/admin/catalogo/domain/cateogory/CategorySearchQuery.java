package com.nogueira.admin.catalogo.domain.cateogory;

public record CategorySearchQuery(
        int page,
        int parPage,
        String terms,
        String sort,
        String direction
) {
}
