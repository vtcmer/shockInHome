package com.ztt.stockinhome.shop.model;

import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.products.entities.SelectableObject;

/**
 * Created by vtcmer on 23/10/2016.
 */

@Table(name="SHOPPING_LIST_DETAIL")
public class ShoppingListDetail implements SelectableObject {

    @Unique
    private Long id;

    private Integer units = 1;

    private Boolean checked = Boolean.FALSE;

    @Column(name="PRODUCT_ID")
    private Product product;

    @Column(name="SHOPPING_LIST_ID")
    private ShoppingList shoppingList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof ShoppingListDetail){
            ShoppingListDetail detail = (ShoppingListDetail)obj;
            if ((this.id != null) && (detail.getId() != null)) {
                equal = this.id.equals(detail.getId());
            }
        }

        return equal;
    }
}
