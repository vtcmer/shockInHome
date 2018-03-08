package com.ztt.stockinhome.shop;

import com.ztt.stockinhome.shop.events.ShoppingListEvent;
import com.ztt.stockinhome.shop.events.ShoppingListSingleEvent;
import com.ztt.stockinhome.shop.model.ShoppingList;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public interface ShoppingListPresenter {


    /**
     * Se subscribe al evento
     */
    void subscribe();

    /**
     * Eliminación de subscripción al evento
     */
    void unsubscribe();

    /**
     * Recuperación de todas las listas de la compra
     */
    void findAll();

    /**
     * Creación de una listado
     * @param shoppingList
     */
    void save(ShoppingList shoppingList);


    /**
     * Eliminación de elementos
     * @param deleteItems
     */
    void delete(List<ShoppingList> deleteItems);

    /**
     * Captura el evento
     * @param event
     */
    void onEventMainThread(final ShoppingListSingleEvent event);

    /**
     * Captura del evento
     * @param event
     */
    void onEventMainThread(final ShoppingListEvent event);



}
