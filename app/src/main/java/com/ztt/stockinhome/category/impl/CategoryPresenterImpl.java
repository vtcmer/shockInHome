package com.ztt.stockinhome.category.impl;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.category.CategoryPresenter;
import com.ztt.stockinhome.category.events.CategoryEvent;
import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.category.ui.CategoryView;
import com.ztt.stockinhome.libs.EventBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class CategoryPresenterImpl implements CategoryPresenter {

    private EventBus eventBus;
    private CategoryInteractor interactor;
    private CategoryView view;

    public CategoryPresenterImpl(EventBus eventBus, CategoryInteractor interactor, CategoryView view) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onCreate() {
        this.eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.eventBus.unregister(this);
        this.view = null;
    }

    @Override
    public void findAll() {
        this.interactor.findAll();
    }

    @Override
    public void save(Category category) {
        if (category.getId() == null) {
            this.interactor.create(category);
        } else {
            this.interactor.update(category);
        }
    }

    @Override
    public void delete(final Long id){
        this.interactor.delete(id);
    }

    @Override
    public void deleteItems(List<Category> categories) {
        this.interactor.deleteItems(categories);
    }


    @Override
    @Subscribe
    public void onEventMainThread(CategoryEvent event) {
        if (this.view != null){
         switch (event.getType()){
             case CategoryEvent.CREATE_SUCCESS:
                 this.view.onCreateCategorySuccess(event.getCategory());
                 break;
             case CategoryEvent.UPDATE_SUCCESS:
                 this.view.onUpdateCategorySuccess(event.getCategory());
                 break;
             case CategoryEvent.DELETE_SUCCESS:
                 this.view.onDeleteCategorySuccess(event.getCategory());
                 break;
         }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(CategoryListEvent event) {
        if (this.view != null){
            switch (event.getType()){
                case CategoryListEvent.READ_EVENT:
                    this.view.setCategories(event.getCategories());
                    break;
                case CategoryListEvent.DELETE_ITEMS:
                    this.view.onDeleteCategoriesSuccess(event.getCategories());
                    break;
            }
        }
    }
}
