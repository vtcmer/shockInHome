package com.ztt.stockinhome.category.di;

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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 04/09/2016.
 */
@Module
public class CategoryModule {

    private CategoryView view;
    private OnCategoryListener onCategoryListener;

    public CategoryModule(CategoryView view, OnCategoryListener onCategoryListener) {
        this.view = view;
        this.onCategoryListener = onCategoryListener;
    }

    public CategoryModule(){

    }



    @Provides
    @Singleton
    CategoryView providesCategoryView(){
        return this.view;
    }

    @Provides
    @Singleton
    OnCategoryListener providesOnCategoryListener(){
        return this.onCategoryListener;
    }


    @Provides
    @Singleton
    CategoryAdapter providesCategoryAdapter(final List<Category> categories, final OnCategoryListener onCategoryListener){
        return new CategoryAdapter(categories, onCategoryListener);
    }

    @Provides
    @Singleton
    CategoryPresenter providesCategoryPresenter(final EventBus eventBus, final CategoryInteractor interactor, final CategoryView view){
        return new CategoryPresenterImpl(eventBus, interactor, view);
    }

    @Provides
    @Singleton
    CategoryInteractor providesCategoryInteractor(final CategoryRepository repository){
        return new CategoryInteractorImpl(repository);
    }

    @Provides
    @Singleton
    CategoryRepository providesCategoryRepository(final EventBus eventBus){
        return new CategoryRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    CategoryItemFragment providesCategoryItemFragment(){
        return new CategoryItemFragment();
    }

    @Provides @Singleton
    List<Category> provideCategoryList() {
        return new ArrayList<Category>();
    }



}
