package com.ztt.stockinhome.category.ui;

import com.ztt.stockinhome.category.model.Category;

import java.util.List;

/**
 * Created by vtcmer on 04/09/2016.
 */
public interface CategoryView {
    /**
     * Establecer las categorias
     * @param categories
     */
    void setCategories(final List<Category> categories);

    /**
     * Confirmación de categoría creada correctamente
     * @param category
     */
    void onCreateCategorySuccess(final Category category);

    /**
     * Mensaje de error en la creación de la categoría
     */
    void onCreateCategoryError();

    /**
     * Actualización de una categoría
     * @param category
     */
    void onUpdateCategorySuccess(final Category category);

    void onUpdateCategoryError();

    /**
     * Eliminación de una categoría
     * @param category
     */
    void onDeleteCategorySuccess(Category category);

    void onDeleteCategoryError();

    /**
     * Eliminación lista de categorias
     * @param categories
     */
    void onDeleteCategoriesSuccess(List<Category> categories);

    void onDeleteCategoriesError();
}
