package com.ztt.stockinhome.shop.ui;

import com.ztt.stockinhome.shop.model.ShoppingListDetail;

/**
 * Created by vtcmer on 12/11/2016.
 */
public interface OnShoppingListDetailListener {

    /**
     * Elemento seleccinado
     * @param position
     * @param detail
     */
    void onItemSelected(int position, ShoppingListDetail detail);

    /**
     * Check el item
     * @param detail
     */
    void onCheck(ShoppingListDetail detail);

    /**
     * Se deselecciona el elemento
     * @param detail
     */
    void onUnCheck(ShoppingListDetail detail);

    /**
     * Actualizador de la unidades
     * @param detail
     */
    void onShowUpdaterUnit(ShoppingListDetail detail);
}
