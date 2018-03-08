package com.ztt.stockinhome.shop.ui;

import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 12/11/2016.
 */
public interface ShoppingListDetailView {

    /**
     * Recupereración del detalle de un producto
     * @param details
     */
    void onReadDetails(List<ShoppingListDetail> details);

    /**
     * Se marca o desmarcar el producto
     * @param detail
     */
    void onCheckDetail(ShoppingListDetail detail);

    /**
     * Evento a ejecutar cuando el borrado es correcto
     */
    void onDeletedSuccess(List<ShoppingListDetail> details);

    /**
     * Actualización de las unidades
     * @param detail
     */
    void onUpdatedUnitsSuccess(ShoppingListDetail detail);

    /**
     * Actualización del stock correctamente
     * @param shoppingListId
     */
    void onUpdatedStockSuccess(final Long shoppingListId);

    /**
     * Eliminación de todos los productos de la lista correctamente
     */
    void onCleanSuccess();
}
