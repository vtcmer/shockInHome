package com.ztt.stockinhome.shop.ui;

import com.ztt.stockinhome.shop.model.ShoppingList;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */
public interface ShoppingListView {
    /**
     * Listado creado correctamente
     * @param shoppingList
     */
    void onCreatedSuccess(ShoppingList shoppingList);

    /**
     * Recuperación de las listas guardadas
     * @param shoppingList
     */
    void onReadItems(List<ShoppingList> shoppingList);

    /**
     * Actualización de un listado
     * @param shoppingList
     */
    void onUpdatedSuccess(ShoppingList shoppingList);

    /**
     * Elimnación de listados
     * @param shoppingList
     */
    void onDeleteItems(List<ShoppingList> shoppingList);
}
