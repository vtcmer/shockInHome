package com.ztt.stockinhome.stock.di;

import com.ztt.stockinhome.category.di.CategoryModule;
import com.ztt.stockinhome.libs.di.LibsModule;
import com.ztt.stockinhome.products.di.ProductsModule;
import com.ztt.stockinhome.shop.di.ShopModule;
import com.ztt.stockinhome.shop.di.ShoppingListDetailModule;
import com.ztt.stockinhome.shop.di.ShoppingListModule;
import com.ztt.stockinhome.stock.StockPresenter;
import com.ztt.stockinhome.stock.ui.adapters.StockAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 10/08/2016.
 */
@Singleton
@Component(modules = {StockModule.class, ProductsModule.class, ShoppingListModule.class,  ShoppingListDetailModule.class, LibsModule.class,CategoryModule.class, ShopModule.class})
public interface StockComponent {

    /**
     * Recuperación del presentador
     * @return
     */
    StockPresenter getPresenter();

    /**
     * Recuperación de la instancia del adaptador
     * @return
     */
    StockAdapter getAdapter();



}
