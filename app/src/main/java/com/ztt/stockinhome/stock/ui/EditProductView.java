package com.ztt.stockinhome.stock.ui;

import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.model.Shop;
import com.ztt.stockinhome.shop.model.ShoppingList;

import java.util.List;

/**
 * Created by vtcmer on 16/10/2016.
 */
public interface EditProductView {

    /**
     * Setea las categorias recuperadas
     * @param categories
     */
    void setCategories(final List<Category> categories);

    /**
     * Setea las tiendas
     * @param shops
     */
    void setShops(final List<Shop> shops);

    /**
     * Muestra el spin de progreso
     */
    void showProgressBar();

    /**
     * Oculta la spin de progreso
     */
    void hideProgressBar();

    /**
     * Creación del producto correctamente
     * @param product
     */
    void onCreateSuccess(final Product product);

    /**
     * Actualización del producto correctamente
     * @param product
     */
    void onUpdateSuccess(final Product product);

    /**
     * Recuperación del producto
     * @param product
     */
    void onEditSuccess(Product product);

    /**
     * Recuperación de listas de la compra
     * @param shoppingList
     */
    void onFindShoppingList(List<ShoppingList> shoppingList);
}
