package com.ztt.stockinhome.shop.events;

import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 12/11/2016.
 */
public class ShoppingListDetailEvent {

    public final static int READ_EVENT = 0;
    public final static int CHECKED_SUCCESS = 1;
    public final static int DELETED_SUCCESS = 2;
    public final static int UPDATED_UNITS_SUCCESS = 3;
    public final static int UPDATED_STOCK_SUCCESS = 4;
    public final static int CLEAN_SUCCESS = 5;

    private int type;

    private List<ShoppingListDetail> details;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ShoppingListDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ShoppingListDetail> details) {
        this.details = details;
    }
}
