package com.ztt.stockinhome.stock.di;

import com.ztt.stockinhome.category.di.CategoryModule;
import com.ztt.stockinhome.libs.di.LibsModule;
import com.ztt.stockinhome.products.di.ProductsModule;
import com.ztt.stockinhome.shop.di.ShopModule;
import com.ztt.stockinhome.shop.di.ShoppingListDetailModule;
import com.ztt.stockinhome.shop.di.ShoppingListModule;
import com.ztt.stockinhome.stock.EditProductPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 16/10/2016.
 */
@Singleton
@Component(modules = {EditProductModule.class, ProductsModule.class, LibsModule.class, CategoryModule.class, ShopModule.class, ShoppingListModule.class,ShoppingListDetailModule.class})
public interface EditProductComponent {

    EditProductPresenter getPresenter();

}
