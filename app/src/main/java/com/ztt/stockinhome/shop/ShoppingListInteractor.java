package com.ztt.stockinhome.shop;

import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public interface ShoppingListInteractor {

    /**
     * Método para creación y actualización
     * @param shoppingList
     */
    void save(final ShoppingList shoppingList);

    /**
     * Método para eliminación
     * @param shoppingList
     */
    void delete(final ShoppingList shoppingList);

    /**
     * Eliminación de un listado de productos
     * @param items
     */
    void delete(final List<ShoppingList> items);

    /**
     * Método para recuperación
     * @param id
     */
    void get(final Long id);

    /**
     * Recuperación de todas las listas de la compra
     */
    void findlAll();

}
