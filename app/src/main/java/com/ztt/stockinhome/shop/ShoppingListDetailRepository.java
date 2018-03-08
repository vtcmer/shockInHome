package com.ztt.stockinhome.shop;

import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 30/10/2016.
 */

public interface ShoppingListDetailRepository {

    /**
     * Recuperación de los detalles por el identificador de la lista
     * @param shoppingListId
     */
    void findByShoppinList(final Long shoppingListId);

    /**
     * Comprueba si existe un producto añadido a la lista
     * @param shoppigListId
     * @param productId
     * @return
     */
    boolean existsProduct(final Long shoppigListId, final Long productId);


    /**
     * Añade una lista de productos
     * @param details
     */
    void add(final List<ShoppingListDetail> details);

    /**
     * Añade un nuevo producto a la lista
     * @param detail
     */
    void add(final ShoppingListDetail detail);



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
     * Eliminación de todos los productos de una lista de la compra
     * @param shoppingListId
     */
    void removeAllProductInShoppingList(final Long shoppingListId);

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

}
