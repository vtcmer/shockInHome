package com.ztt.stockinhome.shop.impl;

import com.ztt.stockinhome.shop.ShoppingListDetailInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailRepository;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 10/11/2016.
 */

public class ShoppingListDetailInteractorImpl implements ShoppingListDetailInteractor {

    private ShoppingListDetailRepository repository;

    public ShoppingListDetailInteractorImpl(ShoppingListDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public void findByShoppinList(Long shoppinListId) {
        this.repository.findByShoppinList(shoppinListId);
    }

    @Override
    public void add(List<ShoppingListDetail> details) {

        for (ShoppingListDetail detail: details){
            this.repository.add(detail);
        }
    }

    @Override
    public void add(ShoppingListDetail detail) {
        this.repository.add(detail);
    }

    @Override
    public void updateUnit(Long id, int units) {
        this.repository.updateUnit(id,units);
    }

    @Override
    public void delete(List<ShoppingListDetail> details) {
        this.repository.delete(details);
    }

    @Override
    public void check(Long id, boolean check) {
        this.repository.check(id,check);
    }

    @Override
    public void checkAll(Long shoppingListId, boolean check) {
        this.repository.checkAll(shoppingListId,check);
    }

    @Override
    public void removeAllProductInShoppingList(Long shoppingListId) {
        this.repository.removeAllProductInShoppingList(shoppingListId);
    }
}
