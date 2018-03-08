package com.ztt.stockinhome.category;

import com.ztt.stockinhome.category.events.CategoryEvent;
import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.category.model.Category;

import java.util.List;

/**
 * Created by vtcmer on 04/09/2016.
 */
public interface CategoryPresenter {

    /**
     * Create del presentador
     */
    void onCreate();

    /**
     * Destrucción del presentador
     */
    void onDestroy();

    /**Recupera la lista de todas las categorias*/
    void findAll();

    /**
     * Guardar inforamción de la categoria
     * @param category
     */
    void save(final Category category);

    /**
     * Eliminación de un elemento
     * @param id
     */
    void delete(final Long id);

    /**
     * Eliminación de una lista de elementos
     * @param categories
     */
    void deleteItems(final List<Category> categories);


    /**
     * Captura de evento para una categoria
     * @param event
     */
    void onEventMainThread(CategoryEvent event);

    /**
     * Captura del evento para listado de categorias
     * @param event
     */
    void onEventMainThread(CategoryListEvent event);
}
