package com.ztt.stockinhome.category.events;

import com.ztt.stockinhome.category.model.Category;

import java.util.List;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class CategoryListEvent {

    public final static int READ_EVENT = 0;
    public final static int DELETE_ITEMS = 1;

    /**Tipado del evento*/
    private int type;

    /**Listado de categorias*/
    private List<Category> categories;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
