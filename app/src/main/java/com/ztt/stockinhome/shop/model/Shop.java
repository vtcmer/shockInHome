package com.ztt.stockinhome.shop.model;

import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by vtcmer on 15/10/2016.
 */
@Table(name = "SHOP")
public class Shop {

    /**Identificador de la tienda*/
    private Long id;
    /**Nombre de la tienda*/
    private String name;
    /**Image de la tienda*/
    @Column(name="IMG_THUMB")
    private Integer imgThumb;


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

    public Integer getImgThumb() {
        return imgThumb;
    }

    public void setImgThumb(Integer imgThumb) {
        this.imgThumb = imgThumb;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof Shop){
            Shop shop = (Shop)obj;
            if ((this.id != null) && (shop.getId() != null)) {
                equal = this.id.equals(shop.getId());
            }
        }

        return equal;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
