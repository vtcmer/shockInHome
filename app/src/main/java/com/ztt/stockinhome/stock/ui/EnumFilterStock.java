package com.ztt.stockinhome.stock.ui;

/**
 * Created by vtcmer on 01/08/2016.
 */
public enum EnumFilterStock {

    FILTER_SHOPS("filterShops"),

    FILTER_CATEGORY("filterCategories");

    private String value;

    EnumFilterStock (final String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }



}
