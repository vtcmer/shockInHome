package com.ztt.stockinhome.shop;

import com.ztt.stockinhome.shop.events.ShoppingListDetailEvent;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 12/11/2016.
 */
public interface ShoppingListDetailPresenter {
    /**
     * Subscripción al bus de eventos
     */
    void subscribe();


    /**
     * Unsubscribe del bus de eventos
     */
    void unsubscribe();

    /**
     * Recuperación del detalle de la lista de la ocmpra
     */
    void findAllDetail(final Long shoppingListId);


    /**
     * Eliminación de items del detalle
     * @param deleteItems
     */
    void delete(List<ShoppingListDetail> deleteItems);


    /**
     * Captura el evento
     * @param event
     */
    void onEventMainThread(final ShoppingListDetailEvent event);

    /**
     * Marca el elemento como añadido
     * @param detail
     */
    void checked(ShoppingListDetail detail);

    /**
     * Desmarca el elementos
     * @param detail
     */
    void unchecked(ShoppingListDetail detail);

    /**
     * Actualización del número de unidades
     * @param id
     * @param units
     */
    void updatedUnits(final Long id, final Integer units);

    /**
     * Actualiza el stock de producto con las unidades seleccionadas
     * @param shoppingListId Identificador de la lista de la compra
     * @param checkeItems
     */
    void updateStock(final Long shoppingListId, final List<ShoppingListDetail> checkeItems);

    /**
     * Eliminación de todos los producto de la lista
     * @param shoppingListId
     */
    void cleanShoppingList(final Long shoppingListId);
}
