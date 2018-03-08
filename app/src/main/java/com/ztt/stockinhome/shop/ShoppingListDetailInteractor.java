package com.ztt.stockinhome.shop;

import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 10/11/2016.
 */

public interface ShoppingListDetailInteractor {

    /**
     * Recuperación del detalle de una lista de la compra
     * @param shoppinListId
     */
    void findByShoppinList(final Long shoppinListId);

    /**
     * Añade una lista de productos
     * @param details
     */
    void add(List<ShoppingListDetail> details);

    /**
     * Añade un producto
     * @param detail
     */
    void add(ShoppingListDetail detail);



    /**
     * Actualiza el número de unidades
     * @param id
     * @param units
     */
    void updateUnit(final Long id, final int units);

    /**
     * Eliminación de un detalle
     * @param details
     */
    void delete(final List<ShoppingListDetail> details);

    /**
     * Marca si el producto añadido o no
     * @param id
     * @param check
     */
    void check (final Long id, final boolean check);

    /**
     * Marca o desmarca todos los producto de la lista
     * @param shoppingListId
     * @param check
     */
    void checkAll (final Long shoppingListId, final boolean check);

    /**
     * Eliminación de todos los productos de una lista de la compra
     * @param shoppingListId
     */
    void removeAllProductInShoppingList(final Long shoppingListId);


}
