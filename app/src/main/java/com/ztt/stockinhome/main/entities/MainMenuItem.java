package com.ztt.stockinhome.main.entities;

import com.ztt.stockinhome.main.EnumMainMenuActions;

/**
 * Created by vtcmer on 01/08/2016.
 */
public class MainMenuItem {

    /*Titulo del botón*/
    private String title;
    /*Icono a mostrar*/
    private Integer icon;
    /*Establece la acción a ejecutar*/
    private EnumMainMenuActions action;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public EnumMainMenuActions getAction() {
        return action;
    }

    public void setAction(EnumMainMenuActions action) {
        this.action = action;
    }
}
