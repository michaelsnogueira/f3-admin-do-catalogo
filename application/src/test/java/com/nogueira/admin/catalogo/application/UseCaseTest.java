package com.nogueira.admin.catalogo.application;

import com.nogueira.admin.catalogo.domain.cateogory.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCaseTest {

    @Test
    public void testExecute() {
        UseCase useCase = new UseCase();
        Category execute = useCase.execute();
        Assertions.assertNotNull(execute);
    }
}
