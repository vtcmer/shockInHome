package com.ztt.stockinhome.shop.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;
import com.ztt.stockinhome.products.entities.SelectableObject;

/**
 * Created by vtcmer on 23/10/2016.
 */

@Table(name="SHOPPING_LIST")
public class ShoppingList implements SelectableObject {

    @Unique
    private Long id;

    private String name;

    private Boolean complete = Boolean.FALSE;

    private Integer items = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }


    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof ShoppingList){
            ShoppingList shoppingList = (ShoppingList)obj;
            if ((this.id != null) && (shoppingList.getId() != null)) {
                equal = this.id.equals(shoppingList.getId());
            }
        }

        return equal;
    }

}
