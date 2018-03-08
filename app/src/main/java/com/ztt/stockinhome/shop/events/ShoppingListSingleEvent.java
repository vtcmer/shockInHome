package com.ztt.stockinhome.shop.events;

import com.ztt.stockinhome.shop.model.ShoppingList;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public class ShoppingListSingleEvent {

    public final static int READ_SUCCESS = 0;
    public final static int CREATED_SUCCESS = 1;
    public final static int UPDATED_SUCCESS = 2;
    public final static int DELETED_SUCCESS = 3;

    private int type;

    private ShoppingList shoppingList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
