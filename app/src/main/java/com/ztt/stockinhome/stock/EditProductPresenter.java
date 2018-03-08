package com.ztt.stockinhome.stock;

import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.events.ProductSingleEvent;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.events.ShopEvent;
import com.ztt.stockinhome.shop.events.ShoppingListEvent;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

/**
 * Created by vtcmer on 16/10/2016.
 */

public interface EditProductPresenter {

    /**
     * Método para el registro de eventos
     */
    void subscribe();


    /**
     * Método para destruir los eventos
     * */
    void unsubscribe();

    /**
     * Recuperación de las categorias
     */
    void getAllCategories();

    /**
     * Recuperación de las tiendas
     */
    void getAllShops();

    /**
     * Recuperación de todas las lista de la compra
     */
    void findAllShoppingList();

    /**
     * Guardado de un producto
     * @param product
     */
    void save(final Product product);


    /**
     * Recuperación de un producto
     * @param id
     */
    void getProduct(Long id);

    /**
     *
     * Eventos para la recuperación de las categorias
     * @param events
     */
    void onEventMainThread(CategoryListEvent events);

    /**
     * Evento para la recuperación de las tiendas
     * @param events
     */
    void onEventMainThread(ShopEvent events);

    /**
     * Evento para productos
     * @param event
     */
    void onEventMainThread(ProductSingleEvent event);

    /**
     * Evento para el listado de la compra
     * @param event
     */
    void onEventMainThread(ShoppingListEvent event);

    /**
     * Añadir producto a la lista
     * @param detail
     */
    void addProductsToShoppingList(ShoppingListDetail detail);
}
