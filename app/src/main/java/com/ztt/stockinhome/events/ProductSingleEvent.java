package com.ztt.stockinhome.events;

import com.ztt.stockinhome.products.entities.Product;

/**
 * Created by vtcmer on 11/08/2016.
 */
public class ProductSingleEvent extends ProductEvent {


    public final static int READ_PRODUCT = 0;
    public final static int CREATE_PRODUCT = 1;
    public final static int DELETE_PRODUCT = 2;
    public final static int UPDATE_PRODUCT = 3;
    public final static int ADD_PRODUCT_SCAN = 4;
    public final static int REMOVE_PRODUCT_SCAN = 5;
    public final static int PRODUCT_NOT_FOUND = 6;
    public final static int PRODUCT_ADD_NOT_FOUND = 7;


    /**Producto*/
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
