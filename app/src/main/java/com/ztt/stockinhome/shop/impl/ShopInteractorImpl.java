package com.ztt.stockinhome.shop.impl;

import com.ztt.stockinhome.shop.ShopInteractor;
import com.ztt.stockinhome.shop.ShopRepository;

/**
 * Created by vtcmer on 15/10/2016.
 */

public class ShopInteractorImpl implements ShopInteractor {

    private ShopRepository repository;

    public ShopInteractorImpl(ShopRepository repository) {
        this.repository = repository;
    }

    @Override
    public void findAll() {
        this.repository.getAll();
    }
}
