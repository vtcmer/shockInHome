package com.ztt.stockinhome.shop.events;

import com.ztt.stockinhome.shop.model.Shop;

import java.util.List;

/**
 * Created by vtcmer on 15/10/2016.
 */

public class ShopEvent {

    public final static int READ_EVENT = 0;

    private int type;
    private List<Shop> shops;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
