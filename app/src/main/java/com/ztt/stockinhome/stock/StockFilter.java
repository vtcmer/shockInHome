package com.ztt.stockinhome.stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 11/12/2016.
 */

public class StockFilter {

    private String name = "";
    private List<String> shops = new ArrayList<String>();
    private List<String> categories = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getShops() {
        return shops;
    }

    public void setShops(List<String> shops) {
        this.shops = shops;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
