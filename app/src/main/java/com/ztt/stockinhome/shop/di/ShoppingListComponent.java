package com.ztt.stockinhome.shop.di;

import com.ztt.stockinhome.category.CategoryPresenter;
import com.ztt.stockinhome.category.ui.CategoryItemFragment;
import com.ztt.stockinhome.category.ui.adapters.CategoryAdapter;
import com.ztt.stockinhome.libs.di.LibsModule;
import com.ztt.stockinhome.shop.ShoppingListPresenter;
import com.ztt.stockinhome.shop.ui.ShoppingListItemFragment;
import com.ztt.stockinhome.shop.ui.adapters.ShoppingListAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 04/09/2016.
 */
@Singleton
@Component(modules={ShoppingListModule.class, LibsModule.class})
public interface ShoppingListComponent {

    /**
     * Recuperación del presenter
     * @return
     */
    ShoppingListPresenter getPresenter();

    /**
     * Recuperación del adaptador
     * @return
     */
    ShoppingListAdapter getAdapter();

    /**
     * Fragmento para crear un listado
     * @return
     */
    ShoppingListItemFragment getShoppingListItemFragment();
}
