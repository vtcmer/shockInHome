package com.ztt.stockinhome.shop.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.commons.ui.adapters.SelectedAdapter;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.ui.OnShoppingListListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 26/10/2016.
 */

public class ShoppingListAdapter extends
        SelectedAdapter<ShoppingListAdapter.ViewHolderShoppingList,
                ShoppingList> {


    private OnShoppingListListener listener;
    private List<ShoppingList> shoppingList = null;

    public ShoppingListAdapter(OnShoppingListListener listener) {
        this.listener = listener;
        this.shoppingList = new ArrayList<ShoppingList>();
    }

    /**
     * Setea el listado de tiendas
     *
     * @param shoppingList
     */
    public void set(final List<ShoppingList> shoppingList) {
        this.shoppingList.clear();
        this.shoppingList.addAll(shoppingList);
        this.notifyDataSetChanged();
    }

    @Override
    public ShoppingList getItem(int position) {
        ShoppingList item = null;
        if (this.shoppingList.size() >= position) {
            item = this.shoppingList.get(position);
        }
        return item;
    }

    /**
     * Añade un nuevo item
     *
     * @param item
     */
    public void add(final ShoppingList item) {
        this.shoppingList.add(item);
        notifyDataSetChanged();
    }


    /**
     * Añadir un nuevo item a la posición indicada
     *
     * @param position
     * @param item
     */
    public void add(int position, ShoppingList item) {

        if (position > this.shoppingList.size()) {
            this.shoppingList.add(item);
        } else {
            this.shoppingList.add(position, item);
        }

    }

    /**
     * Actualización de un item
     *
     * @param item
     */
    public void update(final ShoppingList item) {
        int position = this.shoppingList.indexOf(item);
        this.shoppingList.set(position, item);
        this.notifyItemChanged(position);
    }


    /**
     * Eliminación de un conjunto de items
     *
     * @param deleteItems
     */
    public void delete(List<ShoppingList> deleteItems) {
        this.shoppingList.removeAll(deleteItems);
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolderShoppingList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolderShoppingList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderShoppingList holder, int position) {
        ShoppingList shoppingList = this.getItem(position);
        boolean isItemSelected = isSelected(position);

        holder.shoppingListName.setText(shoppingList.getName());
        holder.setOnShoppingListListener(position, shoppingList, this.listener, isItemSelected);
        holder.view.setSelected(isItemSelected);
        if (isItemSelected) {
            holder.btnEditShoppingList.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.btnEditShoppingList.setImageResource(R.drawable.ic_edit_black_24dp);
        }


    }

    @Override
    public int getItemCount() {
        return this.shoppingList.size();
    }


    static class ViewHolderShoppingList extends RecyclerView.ViewHolder {

        @Bind(R.id.shoppingListName)
        TextView shoppingListName;
        @Bind(R.id.btnEditShoppingList)
        ImageView btnEditShoppingList;
        @Bind(R.id.containerShoppingItem)
        RelativeLayout containerShoppingItem;


        private View view;


        public ViewHolderShoppingList(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnShoppingListListener(final int position, final ShoppingList shoppingList, final OnShoppingListListener onShoppingListListener, final boolean isItemSelected) {


            containerShoppingItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isItemSelected) {
                        onShoppingListListener.showDetail(shoppingList);
                    }
                }
            });

            btnEditShoppingList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isItemSelected) {
                        onShoppingListListener.onEdit(shoppingList);
                    }
                }
            });

            containerShoppingItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onShoppingListListener.onItemSelected(position, shoppingList);
                    return true;
                }
            });


        }
    }

}
