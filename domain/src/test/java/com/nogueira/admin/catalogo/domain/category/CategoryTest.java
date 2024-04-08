package com.nogueira.admin.catalogo.domain.category;

import com.nogueira.admin.catalogo.domain.cateogory.Category;
import com.nogueira.admin.catalogo.domain.exceptions.DomainException;
import com.nogueira.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void givenAValidParamsWhenCallNewCategoryThenInstantiateACategory() {
        //given
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedIsActive = true;

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }
    @Test
    public void givenAnInvalidNullNameWhenCallNewCategoryAndValidateThenShouldRecieveError() {

        //given
        final String expectedName = null;
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final DomainException actualException = assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());

    }
    @Test
    public void givenAnInvalidEmptyNameWhenCallNewCategoryAndValidateThenShouldRecieveError() {

        //given
        final String expectedName = "  ";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be empty";

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final DomainException actualException = assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());

    }
    @Test
    public void givenAnInvalidNameLengthLessThan3WhenCallNewCategoryAndValidateThenShouldRecieveError() {

        //given
        final String expectedName = "Fi ";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final DomainException actualException = assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());

    }
    @Test
    public void givenAnInvalidNameLengthMoreThan255WhenCallNewCategoryAndValidateThenShouldRecieveError() {

        //given
        final String expectedName = "c".repeat(256);
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final DomainException actualException = assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
    @Test
    public void givenAvalidEmptyDescriptionWhenCallNewCategoryAndValidateThenShouldRecieveError() {

        //given
        final String expectedName = "Filme";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> Category.newCategory(expectedName, expectedDescription, expectedIsActive));

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }
    @Test
    public void givenAValidIsActiveEmptyDescriptionWhenCallNewCategoryAndValidateThenShouldRecieveError() {

        //given
        final String expectedName = "Filme";
        final var expectedDescription = " ";
        final var expectedIsActive = false;

        var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> Category.newCategory(expectedName, expectedDescription, expectedIsActive));

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());
    }
    @Test
    public void givenAValidActiveCategoryWhenCallDeactivateThenReturnCategoryInactivated() throws InterruptedException {

        //given
        final String expectedName = "Filme";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedIsActive = true;

        var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var updatedAt = aCategory.getUpdatedAt();

        assertNull(aCategory.getDeletedAt());
        assertTrue(aCategory.isActive());

        //when
        Thread.sleep(1000);
        final var actualCategory = aCategory.deactivate();

        //then
        assertFalse(actualCategory.isActive());
        assertNotNull(actualCategory.getDeletedAt());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(aCategory.getName(), actualCategory.getName());
        assertEquals(aCategory.getDescription(), actualCategory.getDescription());
        assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
    }
    @Test
    public void givenAValidInactiveCategoryWhenCallActivateThenReturnCategoryActivated() {

        //given
        final String expectedName = "Filme";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedIsActive = false;

        var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        assertNotNull(aCategory.getDeletedAt());
        assertFalse(aCategory.isActive());

        //when
        final var actualCategory = aCategory.activate();

        //then
        assertTrue(actualCategory.isActive());
        assertNull(actualCategory.getDeletedAt());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(aCategory.getName(), actualCategory.getName());
        assertEquals(aCategory.getDescription(), actualCategory.getDescription());
        assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
    }
    @Test
    public void givenAValidCategoryWhenCallUpdateThenReturnCategoryUpdated() throws InterruptedException {

        //given
        final String expectedName = "Filme e Series";
        final var expectedDescription = "Filmes de todos os gêneros e séries";

        var aCategory = Category.newCategory("Filme", "Generos", true);

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        //when
        aCategory.setDescription(expectedDescription);
        aCategory.setName(expectedName);
        Thread.sleep(1000);
        final var actualCategory = aCategory.update(aCategory);

        //then
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        assertNull(actualCategory.getDeletedAt());
    }
    @Test
    public void givenAValidCategoryWhenCallUpdateToInactiveThenReturnCategoryUpdated() throws InterruptedException {

        //given
        final String expectedName = "Filme e Series";
        final var expectedDescription = "Filmes de todos os gêneros e séries";
        final var expectedIsActive = false;

        var aCategory = Category.newCategory("Filme", "Generos", true);

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        //when
        aCategory.setDescription(expectedDescription);
        aCategory.setName(expectedName);
        aCategory.setActive(expectedIsActive);
        Thread.sleep(1000);
        final var actualCategory = aCategory.update(aCategory);

        //then
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getDeletedAt());
    }
    @Test
    public void givenAValidCategoryWhenCallUpdateWithInvalidParamsThenReturnCategoryUpdated() throws InterruptedException {

        //given
        final String expectedName = null;
        final var expectedDescription = "Filmes de todos os gêneros e séries";
        final var expectedIsActive = true;

        var aCategory = Category.newCategory("Filme", "Generos", expectedIsActive);

        assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        //when
        aCategory.setDescription(expectedDescription);
        aCategory.setName(expectedName);
        Thread.sleep(1000);
        final var actualCategory = aCategory.update(aCategory);

        //then
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertTrue(actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        assertNull(actualCategory.getDeletedAt());
    }
}
