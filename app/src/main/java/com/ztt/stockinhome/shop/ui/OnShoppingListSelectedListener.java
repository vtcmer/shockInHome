package com.ztt.stockinhome.shop.ui;

import com.ztt.stockinhome.shop.model.ShoppingList;

/**
 * Created by vtcmer on 07/11/2016.
 */

public interface OnShoppingListSelectedListener {

    /**
     *
     * Método a ejecutar cuando se seleccionado una lista de la compra
     * @param shoppingList
     */
    void onShoppingListItemSelected(final ShoppingList shoppingList);
}
