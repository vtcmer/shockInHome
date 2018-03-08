package com.ztt.stockinhome.category.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;
import com.ztt.stockinhome.db.StockInHomeDatabase;
import com.ztt.stockinhome.products.entities.SelectableObject;

/**
 * Created by vtcmer on 30/08/2016.
 */

@Table(name = "CATEGORY")
public class Category  implements SelectableObject {


    @Unique
    private Long id;

    private String name;

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


    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof Category){
            Category category = (Category)obj;
            if ((this.id != null) && (category.getId() != null)) {
                equal = this.id.equals(category.getId());
            }
        }

        return equal;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
