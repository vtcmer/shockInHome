package com.ztt.stockinhome.events;

import com.ztt.stockinhome.products.entities.Product;

import java.util.List;

/**
 * Created by vtcmer on 11/08/2016.
 */
public class ProductListEvent extends ProductEvent {

    public final static int READ_PRODUCTS = 0;
    public final static int MORE_SCAN_PRODUCTS = 1;
    public final static int DELETE_PRODUCTS = 2;


    /**Listado de productos*/
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
