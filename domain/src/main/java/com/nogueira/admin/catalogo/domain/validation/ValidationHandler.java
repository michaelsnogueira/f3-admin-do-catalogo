package com.nogueira.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler anHandler);
    ValidationHandler validate(Validation aValidation);
    List<Error> getErrors();

    default boolean hasErrors() {
        return !getErrors().isEmpty() && getErrors() != null;
    }

    interface Validation {
        void validate();
    }
}