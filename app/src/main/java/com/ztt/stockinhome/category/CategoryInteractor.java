package com.ztt.stockinhome.category;

import com.ztt.stockinhome.category.model.Category;

import java.util.List;

/**
 * Created by vtcmer on 02/09/2016.
 */
public interface CategoryInteractor {


    /**
     * Creación de una categoria
     * @param category
     */
    void create(final Category category);

    /**
     * Actualización de una categoria
     * @param category
     */
    void update(final Category category);

    /**
     * Eliminación de una categoria
     * @param id
     */
    void delete(final Long id);

    /**
     * Eliminación de un listado de categorias
     * @param categories
     */
    void deleteItems(final List<Category> categories);

    /**
     * Recuperación de un categoría por el id
     * @param id
     */
    void get(final Long id);

    /**
     * Recuperación de todas la categorías
     */
    void findAll();

    /**
     * Comprobación si existe la categoría
     * @param category
     */
    boolean exist(final Category category);


}
