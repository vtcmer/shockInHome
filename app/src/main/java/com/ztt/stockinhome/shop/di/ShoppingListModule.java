package com.ztt.stockinhome.shop.di;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.category.CategoryPresenter;
import com.ztt.stockinhome.category.CategoryRepository;
import com.ztt.stockinhome.category.impl.CategoryInteractorImpl;
import com.ztt.stockinhome.category.impl.CategoryPresenterImpl;
import com.ztt.stockinhome.category.impl.CategoryRepositoryImpl;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.category.ui.CategoryItemFragment;
import com.ztt.stockinhome.category.ui.CategoryView;
import com.ztt.stockinhome.category.ui.OnCategoryListener;
import com.ztt.stockinhome.category.ui.adapters.CategoryAdapter;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.shop.ShoppingListInteractor;
import com.ztt.stockinhome.shop.ShoppingListPresenter;
import com.ztt.stockinhome.shop.ShoppingListRepository;
import com.ztt.stockinhome.shop.impl.ShoppingListInteractorImpl;
import com.ztt.stockinhome.shop.impl.ShoppingListPresenterImpl;
import com.ztt.stockinhome.shop.impl.ShoppingListRepositoryImpl;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.ui.OnShoppingListListener;
import com.ztt.stockinhome.shop.ui.ShoppingListItemFragment;
import com.ztt.stockinhome.shop.ui.ShoppingListView;
import com.ztt.stockinhome.shop.ui.adapters.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 04/09/2016.
 */
@Module
public class ShoppingListModule {

    private ShoppingListView view;
    private OnShoppingListListener onShoppingListListener;

    public ShoppingListModule(ShoppingListView view, OnShoppingListListener onShoppingListListener) {
        this.view = view;
        this.onShoppingListListener = onShoppingListListener;
    }

    public ShoppingListModule(){

    }



    @Provides
    @Singleton
    ShoppingListView providesShoppingListView(){
        return this.view;
    }

    @Provides
    @Singleton
    OnShoppingListListener providesOnShoppingListListener(){
        return this.onShoppingListListener;
    }


    @Provides
    @Singleton
    ShoppingListAdapter providesShoppingListAdapter(final OnShoppingListListener onShoppingListListener){
        return new ShoppingListAdapter(onShoppingListListener);
    }

    @Provides
    @Singleton
    ShoppingListPresenter providesShoppingListPresenter(final EventBus eventBus, final ShoppingListInteractor interactor, final ShoppingListView view){
        return new ShoppingListPresenterImpl(eventBus, interactor, view);
    }

    @Provides
    @Singleton
    ShoppingListInteractor providesShoppingListInteractor(final ShoppingListRepository repository){
        return new ShoppingListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ShoppingListRepository providesShoppingListRepository(final EventBus eventBus){
        return new ShoppingListRepositoryImpl(eventBus);
    }


    @Provides
    @Singleton
    ShoppingListItemFragment providesShoppingListItemFragment(){
        return new ShoppingListItemFragment();
    }




}
