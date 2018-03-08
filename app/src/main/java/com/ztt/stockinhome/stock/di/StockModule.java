package com.ztt.stockinhome.stock.di;

import android.app.Activity;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.shop.ShopInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailInteractor;
import com.ztt.stockinhome.shop.ShoppingListInteractor;
import com.ztt.stockinhome.stock.StockPresenter;
import com.ztt.stockinhome.stock.impl.StockPresenterImpl;
import com.ztt.stockinhome.stock.ui.OnStockListener;
import com.ztt.stockinhome.stock.ui.StockActivity;
import com.ztt.stockinhome.stock.ui.StockView;
import com.ztt.stockinhome.stock.ui.adapters.StockAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 10/08/2016.
 */
@Module
public class StockModule {


    private StockView view;
    private OnStockListener onStockListener;

    public StockModule(StockView view, OnStockListener onStockListener) {
        this.view = view;
        this.onStockListener = onStockListener;
    }

    @Provides
    @Singleton
    StockView providesStockView(){
        return this.view;
    }



    @Provides
    @Singleton
    OnStockListener providesOnStockListener(){
        return this.onStockListener;
    }

    @Provides
    @Singleton
    StockPresenter providesStockPresenter(EventBus eventBus, StockView view,
                                          ProductInteractor productInteractor,
                                          ShoppingListInteractor shoppingListInteractor,
                                          ShoppingListDetailInteractor detailInteractor,
                                          ShopInteractor shopInteractor,
                                          CategoryInteractor categoryInteractor){
        return new StockPresenterImpl(eventBus, view, productInteractor, shoppingListInteractor, detailInteractor,shopInteractor,categoryInteractor);
    }

    @Provides
    @Singleton
    StockAdapter providesStockAdapter(final OnStockListener onStockListener, final Activity context){
        return new StockAdapter(onStockListener, context);
    }



}
