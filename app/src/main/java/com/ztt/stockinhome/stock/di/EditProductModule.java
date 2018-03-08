package com.ztt.stockinhome.stock.di;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.shop.ShopInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailInteractor;
import com.ztt.stockinhome.shop.ShoppingListInteractor;
import com.ztt.stockinhome.stock.EditProductPresenter;
import com.ztt.stockinhome.stock.impl.EditProductPresenterImpl;
import com.ztt.stockinhome.stock.ui.EditProductView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 16/10/2016.
 */
@Module
public class EditProductModule {

    private EditProductView view;

    public EditProductModule(EditProductView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    EditProductView providesEditProductView(){
        return this.view;
    }


    @Provides
    @Singleton
    EditProductPresenter providesEditProductPresenter(EventBus eventBus, EditProductView view,
                                                      ProductInteractor productInteractor,
                                                      CategoryInteractor categoryInteractor,
                                                      ShopInteractor shopInteractor,
                                                      ShoppingListInteractor shoppingListInteractor,
                                                      ShoppingListDetailInteractor detailInteractor){
        return new EditProductPresenterImpl(eventBus, view, productInteractor,categoryInteractor,
                                            shopInteractor, shoppingListInteractor,detailInteractor);
    }
}
