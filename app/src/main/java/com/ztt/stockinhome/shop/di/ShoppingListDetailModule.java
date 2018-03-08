package com.ztt.stockinhome.shop.di;

import android.app.Activity;

import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailPresenter;
import com.ztt.stockinhome.shop.ShoppingListDetailRepository;
import com.ztt.stockinhome.shop.impl.ShoppingListDetailInteractorImpl;
import com.ztt.stockinhome.shop.impl.ShoppingListDetailPresenterImpl;
import com.ztt.stockinhome.shop.impl.ShoppingListDetailRepositoryImpl;
import com.ztt.stockinhome.shop.ui.OnShoppingListDetailListener;
import com.ztt.stockinhome.shop.ui.ShoppingListDetailView;
import com.ztt.stockinhome.shop.ui.adapters.ShoppingListDetailAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 10/11/2016.
 */
@Module
public class ShoppingListDetailModule {

    private ShoppingListDetailView view;
    private OnShoppingListDetailListener onShoppingListDetailListener;

    public ShoppingListDetailModule(ShoppingListDetailView view, OnShoppingListDetailListener onShoppingListDetailListener) {
        this.view = view;
        this.onShoppingListDetailListener = onShoppingListDetailListener;
    }

    public ShoppingListDetailModule() {
    }


    @Provides
    @Singleton
    ShoppingListDetailView providesShoppingListDetailView(){
        return this.view;
    }

    @Provides
    @Singleton
    OnShoppingListDetailListener providesOnShoppingListDetailListener(){
        return this.onShoppingListDetailListener;
    }

    @Provides
    @Singleton
    ShoppingListDetailAdapter providesShoppingListDetailAdapter(final Activity context, final OnShoppingListDetailListener listener ){
        return new ShoppingListDetailAdapter(listener, context);

    }

    @Provides
    @Singleton
    ShoppingListDetailPresenter providesShoppingListPresenter(final EventBus eventBus,
                                                              final ProductInteractor productInteractor,
                                                              final ShoppingListDetailInteractor shoppingListDetailInteractor,
                                                              final ShoppingListDetailView shoppingListDetailView){
        return new ShoppingListDetailPresenterImpl(eventBus, productInteractor, shoppingListDetailInteractor,shoppingListDetailView);
    }

    @Provides
    @Singleton
    ShoppingListDetailInteractor providesShoppingListDetailInteractor(final ShoppingListDetailRepository repository){
        return new ShoppingListDetailInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ShoppingListDetailRepository providesShoppingListDetailRepository(final EventBus eventBus){
        return new ShoppingListDetailRepositoryImpl(eventBus);
    }
}
