package com.nogueira.admin.catalogo.domain.cateogory;

import com.nogueira.admin.catalogo.domain.AggregateRoot;
import com.nogueira.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryID anId,
                     final String aName,
                     final String aDescription,
                     final boolean active,
                     final Instant aCreationDate,
                     final Instant aUpdatedAt,
                     final Instant aDeletedAt) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = active;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Category newCategory(final String aName,
                                       final String aDescription,
                                       final boolean isActive) {
        var id = CategoryID.unique();
        var deletedAt = isActive ? null : Instant.now();

        return new Category(id, aName, aDescription, isActive, Instant.now(), Instant.now(), deletedAt);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public CategoryID getId() {
        return super.getId();
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(Category aCategory) {
        if (aCategory.isActive()) {
            activate();
        } else {
            deactivate();
        }
        this.name = aCategory.getName();
        this.description = aCategory.getDescription();
        this.updatedAt = Instant.now();
        return this;
    }
}