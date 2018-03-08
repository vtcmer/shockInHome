package com.ztt.stockinhome.stock.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.ui.OnShoppingListSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 30/10/2016.
 */

public class ShoppingListSelectedFragment extends DialogFragment implements  DialogInterface.OnClickListener {

    private List<ShoppingList> shoppingList = new ArrayList<ShoppingList>();
    private OnShoppingListSelectedListener listener;

    /**
     * Setea el listener
     * @param listener
     */
    public void setListener(OnShoppingListSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.select_info);
        builder.setPositiveButton(R.string.btn_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        String[] items =  this.getArrayItems();
        builder.setItems(items,  this);


        return builder.create();
    }




    @Override
    public void onClick(DialogInterface dialog, int position) {
        ShoppingList shoppingList = this.getItem(position);

        this.listener.onShoppingListItemSelected(shoppingList);

        dialog.dismiss();
    }

    /**
     * Set del listado de elementos
     * @param items
     */
    public void setItems(final List<ShoppingList> items){
        this.shoppingList.clear();
        this.shoppingList.addAll(items);
    }

    /**
     * Recuperación del objeto por la posición
     * @param position
     * @return
     */
    private ShoppingList getItem(int position){
        return this.shoppingList.get(position);
    }

    private String[] getArrayItems(){
        final String[] items = new String[this.shoppingList.size()];
        int pos = 0;
        for (ShoppingList sh: this.shoppingList){
            items[pos++] = sh.getName();
        }
        return items;
    }


}
