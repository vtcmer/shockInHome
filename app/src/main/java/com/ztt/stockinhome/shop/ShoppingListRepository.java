package com.ztt.stockinhome.shop;

import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public interface ShoppingListRepository  {

    /**
     * Creación de la lista de la compra
     * @param shoppingList
     */
    void create(final ShoppingList shoppingList);

    /**
     * Actualización de la lista de la compra
     * @param shoppingList
     */
    void update (final ShoppingList shoppingList);

    /**
     * Eliminación de la lista de la compra
     * @param shoppingList
     */
    void delete (final ShoppingList shoppingList);

    /**
     * Recuperación de la lista de la compra
     * @param id
     */
    void get(final Long id);

    /**
     * Recuperación de todas las listas de la compra
     */
    void findAll();

    /**
     * Eliminación de un listado de items
     * @param items
     */
    void delete(List<ShoppingList> items);

}
