package com.ztt.stockinhome.category.di;

import com.ztt.stockinhome.category.CategoryPresenter;
import com.ztt.stockinhome.category.ui.CategoryItemFragment;
import com.ztt.stockinhome.category.ui.adapters.CategoryAdapter;
import com.ztt.stockinhome.libs.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 04/09/2016.
 */
@Singleton
@Component(modules={CategoryModule.class, LibsModule.class})
public interface CategoryComponent {

    /**
     * Recuperación del presenter
     * @return
     */
    CategoryPresenter getPresenter();

    /**
     * Recuperación del adaptador
     * @return
     */
    CategoryAdapter getAdapter();

    /**
     * Fragmento para editar la categoría
     * @return
     */
    CategoryItemFragment getCategoryItemFragment();
}
