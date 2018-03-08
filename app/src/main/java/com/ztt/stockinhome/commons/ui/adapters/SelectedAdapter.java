package com.ztt.stockinhome.commons.ui.adapters;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;

import com.ztt.stockinhome.products.entities.SelectableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 10/08/2016.
 */
public abstract class SelectedAdapter<VH extends RecyclerView.ViewHolder, E extends SelectableObject> extends RecyclerView.Adapter<VH> {

    /**Elementos seleccionados*/
   // protected SparseBooleanArray selectedItems = new SparseBooleanArray();
    protected SparseArrayCompat<E> selectedItems = new SparseArrayCompat<E>();


    /**
     * Añade y elimina los elementos seleccinados
     * @param pos
     */
    public void toggleSelection(int pos, E object) {
        if (selectedItems.get(pos) != null ) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, object);
        }
        notifyItemChanged(pos);
    }

    /**
     * Limpiar la lista de elementos seleccionados
     */
    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * Devuelve el número de elementos seleccionados
     * @return
     */
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    /**
     * Devuelve la lista de posiciones seleccionadas
     * @return
     */
    public List<E> getSelectedItems() {
        List<E> items = new ArrayList<E>();
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.get(selectedItems.keyAt(i)));
        }
        return items;
    }

    public SparseArrayCompat<E> getSelected(){
        return this.selectedItems;
    }




    /**
     * Indica si un elementos está seleccionado o no
     * @param position
     * @return
     */
    protected boolean isSelected(int position){
        boolean selected = false;
        if (selectedItems.get(position) != null){
            selected = true;
        }
        return selected;
    }

    public abstract E getItem(int position);
}
