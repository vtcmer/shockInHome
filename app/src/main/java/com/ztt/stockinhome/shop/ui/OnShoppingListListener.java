package com.ztt.stockinhome.shop.ui;

import com.ztt.stockinhome.shop.model.ShoppingList;

/**
 * Created by vtcmer on 26/10/2016.
 */
public interface OnShoppingListListener {


    /**
     * Guardado de una lista de la compra
     * @param shoppingList
     */
    void onSave(final ShoppingList shoppingList);

    /**
     * Cancelar
     */
    void onCancel();

    /**
     * Editar una lista de la compra
     * @param shoppingList
     */
    void onEdit(final ShoppingList shoppingList);

    /**
     * Al seleccionar una lista de la compra
     * @param position
     * @param shoppingList
     */
    void onItemSelected(final int position, final ShoppingList shoppingList);

    /**
     * Muestra la lista de productos asignados una lista de la compra
     * @param shoppingList
     */
    void showDetail(final ShoppingList shoppingList);
}
