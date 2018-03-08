package com.ztt.stockinhome.shop.impl;

import com.ztt.stockinhome.shop.ShoppingListInteractor;
import com.ztt.stockinhome.shop.ShoppingListRepository;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public class ShoppingListInteractorImpl implements ShoppingListInteractor {

    private ShoppingListRepository repository;

    public ShoppingListInteractorImpl(ShoppingListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ShoppingList shoppingList) {

        if (shoppingList.getId() != null){
            this.repository.update(shoppingList);
        } else {
            this.repository.create(shoppingList);
        }
    }

    @Override
    public void delete(ShoppingList shoppingList) {
        this.repository.delete(shoppingList);
    }

    @Override
    public void delete(List<ShoppingList> items) {
        this.repository.delete(items);
    }

    @Override
    public void get(Long id) {
        this.repository.get(id);
    }

    @Override
    public void findlAll() {
        this.repository.findAll();
    }


}
