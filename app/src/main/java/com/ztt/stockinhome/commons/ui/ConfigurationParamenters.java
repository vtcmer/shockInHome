package com.ztt.stockinhome.commons.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.ztt.stockinhome.stock.ui.EnumFilterStock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vtcmer on 18/12/2016.
 */

public class ConfigurationParamenters {

    private Context context;


    public ConfigurationParamenters(Context context) {
        this.context = context;
    }

    /**
     * Recuperación del filtro de stock
     * @param filterStock
     * @return
     */
    public List<String> getFilterStock(final EnumFilterStock filterStock){
        List<String> result = new ArrayList<String>();
        SharedPreferences prefs = getPrivateSharedPreferences("FILTER_STOCK");
        result.addAll(prefs.getStringSet(filterStock.getValue(),new HashSet<String>()));
        return result;
    }

    /**
     * Guardado filtro de stock
     * @param items
     * @param filterStock
     */
    public void saveFilterStock(final List<String> items, final EnumFilterStock filterStock){

        SharedPreferences prefs = getPrivateSharedPreferences("FILTER_STOCK");
        SharedPreferences.Editor editor =prefs.edit();

        Set<String> setItems = new HashSet<String>();
        setItems.addAll(items);
        editor.putStringSet(filterStock.getValue(),setItems);
        editor.commit();

    }

    /**
     * Eliminación filtro de stock
     */
    public void cleanFilterStock(){
        SharedPreferences prefs = getPrivateSharedPreferences("FILTER_STOCK");
        SharedPreferences.Editor editor =prefs.edit();
        editor.clear();
        editor.commit();

    }


    public boolean isEmptyFilterStock(){
        boolean isOk = true;
        SharedPreferences prefs = getPrivateSharedPreferences("FILTER_STOCK");

        Map<String,Object> content = (Map<String, Object>) prefs.getAll();
        if (!content.isEmpty()){
            isOk = false;
        }

        return isOk;
    }

    private SharedPreferences getPrivateSharedPreferences(final String name){
        return this.context.getSharedPreferences(name,this.context.MODE_PRIVATE);
    }
}
