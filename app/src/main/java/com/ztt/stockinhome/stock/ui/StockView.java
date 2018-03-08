package com.ztt.stockinhome.stock.ui;

import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.model.Shop;
import com.ztt.stockinhome.shop.model.ShoppingList;

import java.util.List;

/**
 * Created by vtcmer on 09/08/2016.
 */
public interface StockView {

    /**
     * Renderiza la lista de productos
     *
     * @param products
     */
    void renderProducts(List<Product> products);

    /**
     * Recuperación de un producto
     *
     * @param product
     */
    void renderProduct(Product product);

    /**
     * Muestra la barra de progreso
     */
    void showProgressBar();

    /**
     * Oculta la barra de progreso
     */
    void hideProgressBar();

    /**
     * Recuperación de las listas de la compra
     *
     * @param shoppingList
     */
    void onFindShoppingList(List<ShoppingList> shoppingList);

    /**
     * Producto actualizado
     *
     * @param product
     */
    void updatedProduct(Product product);

    /**
     * Setea las categorias
     *
     * @param categories
     */
    void setCategories(List<Category> categories);

    /**
     * Setea las tiendas
     *
     * @param shops
     */
    void setShops(List<Shop> shops);

    /**
     * Evento creado al escanear el producto no encontrado
     *
     * @param product
     */
    void onAddScanProductNotFound(final Product product);

    /**
     * Evento creado al escanera un producto no encontrado en la edición
     * @param product
     */
    void onScanProductNotFound(Product product);
}
