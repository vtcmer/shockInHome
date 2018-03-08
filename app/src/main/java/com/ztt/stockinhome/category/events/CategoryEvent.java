package com.ztt.stockinhome.category.events;

import com.ztt.stockinhome.category.model.Category;

/**
 * Created by vtcmer on 02/09/2016.
 */
public class CategoryEvent {

    public final static int CREATE_SUCCESS = 1;
    public final static int CREATE_ERROR = -1;
    public final static int UPDATE_SUCCESS = 2;
    public final static int UPDATE_ERROR = -2;
    public final static int EDIT_SUCCESS = 3;
    public final static int EDIT_ERROR = -3;
    public final static int DELETE_SUCCESS = 4;
    public final static int DELETE_ERROR = -4;

    /**Tipado del evento*/
    private int type;

    /**
     * Objeto de tipo categoria
     */
    private Category category;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
