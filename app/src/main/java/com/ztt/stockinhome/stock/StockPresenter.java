package com.ztt.stockinhome.stock;

import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.events.ProductListEvent;
import com.ztt.stockinhome.events.ProductSingleEvent;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.events.ShopEvent;
import com.ztt.stockinhome.shop.events.ShoppingListEvent;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 09/08/2016.
 */
public interface StockPresenter {

    /**
     * Método para registrar los eventos
     */
    void onCreate();

    /**
     * Método para eliminar los eventos y las vistas
     */
    void onDestroy();



    /**
     * Recepción de los eventos
     * @param event
     */
    void onEventMainThread(ProductListEvent event);

    /**
     * Recepción de evento para un producto
     * @param event
     */
    void onEventMainThread(ProductSingleEvent event);



    /**
     * Captura del evento
     * @param event
     */
    void onEventMainThread(final ShoppingListEvent event);


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
     * Carga inicial de los productos
     */
    void loadProducts();

    /**
     * Filtrado de productos
     * @param filter
     */
    void findProductByFilter(final StockFilter filter);

    /**
     * Recuperación de un producto
     * @param id
     */
    void getProduct(final Long id);

    /**
     * Recuperación de producto por código
     * @param product
     */
    void getProductByCode(final Product product);

    /**
     * Añadir un producto
     * @param product
     */
    void addProduct(final Product product);

    /**
     * Eliminación de un producto
     * @param product
     */
    void deleteProduct(final Product product);

    /**
     * Eliminación de una lista de productos
     * @param products
     */
    void deleteProducts(final List<Product> products);

    /**
     * Recuperación las listas de la compra
     */
    void findAllShoppingList();

    /**
     * Añade una lista de productos a una lista de la compra
     * @param details
     */
    void addProductsToShoppingList(final List<ShoppingListDetail> details);

    /**
     * Actualización del stock
     * @param id
     * @param units
     */
    void updatedStock(Long id, Integer units);

    /**
     * Recuperación de todas las tiendas
     */
    void getAllShops();

    /**
     * Recuperacion de todas las categorias
     */
    void getAllCategories();

    /**
     * Añadir producto escaneado
     * @param product
     */
    void addScanProduct(final Product product);


    /**
     * Editar el producto mediante escaner
     * @param product
     */
    void editScanProduct(Product product);

    /**
     * Eliminación de un producto mediante escaner
     * @param product
     */
    void removeScanProduct(Product product);
}
