package com.ztt.stockinhome.shop.di;

import com.ztt.stockinhome.libs.di.LibsModule;
import com.ztt.stockinhome.products.di.ProductsModule;
import com.ztt.stockinhome.shop.ShoppingListDetailPresenter;
import com.ztt.stockinhome.shop.ui.adapters.ShoppingListDetailAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 12/11/2016.
 */
@Singleton
@Component(modules={ShoppingListDetailModule.class, ProductsModule.class,LibsModule.class})
public interface ShoppingListDetailComponent {

    /**
     * Recuperación del presentador
     * @return
     */
    ShoppingListDetailPresenter getPresenter();

    /**
     * Recuperación del adaptador
     * @return
     */
    ShoppingListDetailAdapter getAdapter();
}
