package com.ztt.stockinhome.stock.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.commons.model.LabelValueBean;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.ui.OnShoppingListSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 30/10/2016.
 */

public class FilterStockFragment extends DialogFragment  {


    /**Items para seleccionar*/
    private List<LabelValueBean> values = new ArrayList<LabelValueBean>();
    /**Elementos seleccionados**/
    private List<String> seletedItems= null;
    /**Item que está seleccionados por defecto*/
    private boolean[] checkItems = null;
    /**Titulo a mostrar*/
    private String title = "" ;


    /**Identifica el tipo de filtro*/
    EnumFilterStock filterStockType ;

    /**Listener de resultados*/
    private OnStockListener listener;

    /**
     * Setea el listener
     * @param listener
     */
    public void setListener(OnStockListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        if (seletedItems == null) {
            seletedItems = new ArrayList<String>();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(this.getTitle());
        builder.setPositiveButton(R.string.btn_apply,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onFilterStock(seletedItems,filterStockType);
                        close();
                    }
                });
        builder.setNegativeButton(R.string.btn_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        close();
                    }
                });

        String[] items =  this.getArrayItems();
        //builder.setItems(items,  this);
        builder.setMultiChoiceItems(items, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {

                LabelValueBean lb = getItem(indexSelected);
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    seletedItems.add(lb.getValue());
                } else  {
                    // Else, if the item is already in the array, remove it
                    seletedItems.remove(lb.getValue());
                }
            }
        });


        return builder.create();
    }





    /**
     * Set del listado de elementos
     * @param items
     */
    public void setItems(final List<LabelValueBean> items){
        this.values.clear();
        this.values = items;
    }

    /**
     * Establece los elementos seleccionadoso
     * @param items
     */
    public void setSeletedItems(final List<String> items){
        this.seletedItems = items;
    }

    /**
     * Establece el titulo
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Recuperación del titulo
     * @return
     */
    private String getTitle() {
        if (title.isEmpty()){
            title = getActivity().getResources().getString(R.string.select_info);
        }
        return title;
    }

    public void setFilterStockType(final EnumFilterStock filterStockType){
        this.filterStockType = filterStockType;
    }

    /**
     * Recuperación del objeto por la posición
     * @param position
     * @return
     */
    private LabelValueBean getItem(int position){
        return this.values.get(position);
    }

    private String[] getArrayItems(){
        checkItems = new boolean[this.values.size()];
        final String[] items = new String[this.values.size()];
        int pos = 0;
        for (LabelValueBean lb: this.values){
            int index = pos++;
            items[index] = lb.getLabel();
            checkItems[index] =  seletedItems.contains(lb.getValue());
        }
        return items;
    }

    /**
     * Cierre de la ventana
     */
    private void close(){
        this.values.clear();
        this.dismiss();
    }

}
