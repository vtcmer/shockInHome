package com.ztt.stockinhome.products.entities;

import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.shop.model.Shop;

/**
 * Created by vtcmer on 09/08/2016.
 */
@Table(name = "PRODUCT")
public class Product implements SelectableObject{

    @Unique
    private Long id;
    private String name;
    private String code;
    private String format;
    private Shop shop;
    private Category category;
    private Integer stock;

    public Product() {
    }

    public Product( String code, String format) {
        this.code = code;
        this.format = format;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof Product){
            Product product = (Product)obj;
            if ((this.id != null) && (product.getId() != null)) {
                equal = this.id.equals(product.getId());
            }
        }

        return equal;
    }
}
