package com.ztt.stockinhome.shop.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.commons.ui.adapters.SelectedAdapter;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;
import com.ztt.stockinhome.shop.ui.OnShoppingListDetailListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 12/11/2016.
 */
public class ShoppingListDetailAdapter extends
        SelectedAdapter<ShoppingListDetailAdapter.ViewHolderShoppingListDetail,
                ShoppingListDetail> {

    private List<ShoppingListDetail> details = null;
    private OnShoppingListDetailListener shoppingListDetailListener;
    private Activity context;

    public ShoppingListDetailAdapter(OnShoppingListDetailListener listener, Activity context) {
        this.shoppingListDetailListener = listener;
        this.context = context;
        this.details = new ArrayList<ShoppingListDetail>();
    }

    @Override
    public ShoppingListDetail getItem(int position) {
        ShoppingListDetail item = null;
        if (this.details.size() >= position) {
            item = this.details.get(position);
        }
        return item;
    }

    /**
     * Recuperación de todos los items
     * @return
     */
    public List<ShoppingListDetail> getItems(){
        return this.details;
    }

    /**
     * Set listado de detalle
     *
     * @param details
     */
    public void setItems(final List<ShoppingListDetail> details) {
        this.details.clear();
        this.details.addAll(details);
        this.notifyDataSetChanged();
    }


    /**
     *
     * Añade un elementos en la posición indicada
     * @param position
     * @param item
     */
    public void add(int position, ShoppingListDetail item) {
        if (position > this.details.size()) {
            this.details.add(item);
        } else {
            this.details.add(position, item);
        }
    }

    /**
     * Añade un elemento a la lista
     * @param item
     */
    public void add(ShoppingListDetail item){
        this.details.add(item);
    }

    /**
     * Actualización de un item
     *
     * @param item
     */
    public void update(final ShoppingListDetail item) {
        int position = this.details.indexOf(item);
        this.details.set(position, item);
        this.notifyItemChanged(position);
    }

    /**
     * Actualización de un item
     *
     * @param item
     */
    public void updateWithoutNotifyItemChanged(final ShoppingListDetail item) {
        int position = this.details.indexOf(item);
        this.details.set(position, item);
    }


    /**
     * Eliminación listado de detalle
     *
     * @param details
     */
    public void delete(final List<ShoppingListDetail> details) {
        this.details.removeAll(details);
        this.notifyDataSetChanged();
    }

    /**
     * Devuelve el número de items chequeados
     * @return
     */
    public List<ShoppingListDetail> getCheckedItems(){
        List<ShoppingListDetail> checkedItems = new ArrayList<ShoppingListDetail>();
        for (ShoppingListDetail detail: this.details){
            if (detail.getChecked()){
                checkedItems.add(detail);
            }
        }

        return checkedItems;
    }


    @Override
    public ViewHolderShoppingListDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_detail_item, parent, false);
        return new ViewHolderShoppingListDetail(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderShoppingListDetail holder, int position) {
        ShoppingListDetail detail = this.getItem(position);
        holder.productName.setText(detail.getProduct().getName());
        holder.imgStock.setImageResource(detail.getProduct().getShop().getImgThumb());
        String units = "";
        if (detail.getUnits() == 1) {
            units = String.format(this.context.getString(R.string.shoppingList_detail_product_unit_one), detail.getUnits().toString());
        } else {
            units = String.format(this.context.getString(R.string.shoppingList_detail_product_unit), detail.getUnits().toString());
        }

        SpannableString content = new SpannableString(units);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        holder.units.setText(content);
        boolean isItemSelected = isSelected(position);
        holder.view.setSelected(isItemSelected);
        holder.chkItem.setChecked(detail.getChecked());


        holder.setOnShoppingListDetailListener(position, detail, this.shoppingListDetailListener, isItemSelected);

    }

    @Override
    public int getItemCount() {
        return this.details.size();
    }

    static class ViewHolderShoppingListDetail extends RecyclerView.ViewHolder {

        @Bind(R.id.imgStock)
        ImageView imgStock;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.units)
        TextView units;
        @Bind(R.id.containerDetail)
        RelativeLayout containerDetail;
        @Bind(R.id.chkItem)
        CheckBox chkItem;


        private View view;

        public ViewHolderShoppingListDetail(View itemView) {

            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnShoppingListDetailListener(final int position,
                                                    final ShoppingListDetail detail,
                                                    final OnShoppingListDetailListener listener,
                                                    final boolean isItemSelected) {

            chkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detail.getChecked()) {
                        listener.onUnCheck(detail);
                    } else {
                        listener.onCheck(detail);
                    }

                }
            });

/*
            chkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                   @Override
                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                           listener.onCheck(detail);
                       }else{
                           listener.onUnCheck(detail);
                       }
                   }
               }
            );
*/

            units.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (!isItemSelected) {
                    listener.onShowUpdaterUnit(detail);
                }
                }
            });
            containerDetail.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemSelected(position, detail);
                    return false;


                }
            });


        }
    }
}
