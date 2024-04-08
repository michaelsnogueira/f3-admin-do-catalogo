package com.nogueira.admin.catalogo.application;

import com.nogueira.admin.catalogo.domain.cateogory.Category;

public class UseCase {
    public Category execute() {
        return Category.newCategory("Filmes", "Filmes de todos os gêneros", true);
    }
}