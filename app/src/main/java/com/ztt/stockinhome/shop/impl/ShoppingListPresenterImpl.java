package com.ztt.stockinhome.shop.impl;

import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.shop.ShoppingListInteractor;
import com.ztt.stockinhome.shop.ShoppingListPresenter;
import com.ztt.stockinhome.shop.events.ShoppingListEvent;
import com.ztt.stockinhome.shop.events.ShoppingListSingleEvent;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.ui.ShoppingListView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public class ShoppingListPresenterImpl implements ShoppingListPresenter {

    private EventBus eventBus;
    private ShoppingListInteractor interactor;
    private ShoppingListView view;

    public ShoppingListPresenterImpl(EventBus eventBus, ShoppingListInteractor interactor, ShoppingListView view) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
    }


    @Override
    public void subscribe() {
        this.eventBus.register(this);
    }

    @Override
    public void unsubscribe() {
        this.eventBus.unregister(this);
        this.view = null;
    }

    @Override
    public void findAll() {
        this.interactor.findlAll();
    }

    @Override
    public void save(ShoppingList shoppingList) {
        this.interactor.save(shoppingList);
    }

    @Override
    public void delete(List<ShoppingList> deleteItems) {
        this.interactor.delete(deleteItems);
    }


    @Override
    @Subscribe
    public void onEventMainThread(ShoppingListSingleEvent event) {

        if (this.view != null){

            switch (event.getType()){
                case ShoppingListSingleEvent.CREATED_SUCCESS:
                    this.view.onCreatedSuccess(event.getShoppingList());
                    break;
                case ShoppingListSingleEvent.UPDATED_SUCCESS:
                    this.view.onUpdatedSuccess(event.getShoppingList());
                case ShoppingListSingleEvent.DELETED_SUCCESS:
            }

        }

    }

    @Override
    @Subscribe
    public void onEventMainThread(ShoppingListEvent event) {
        if (this.view != null){

            switch (event.getType()){
                case ShoppingListEvent.READ_EVENT:
                    this.view.onReadItems(event.getShoppingList());
                    break;
                case ShoppingListEvent.DELETE_ITEMS:
                    this.view.onDeleteItems(event.getShoppingList());
                    break;

            }

        }
    }
}
