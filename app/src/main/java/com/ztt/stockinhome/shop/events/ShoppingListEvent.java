package com.ztt.stockinhome.shop.events;

import com.ztt.stockinhome.shop.model.ShoppingList;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public class ShoppingListEvent {

    public final static int READ_EVENT = 0;
    public final static int DELETE_ITEMS = 1 ;

    private int type;

    private List<ShoppingList> shoppingList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ShoppingList> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingList> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
