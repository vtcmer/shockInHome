package com.ztt.stockinhome.stock.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.commons.ui.adapters.SelectedAdapter;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.stock.ui.OnStockListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 09/08/2016.
 */
public class StockAdapter extends SelectedAdapter<StockAdapter.ViewHolderStock, Product> {

    private List<Product> products = new ArrayList<Product>();
    private OnStockListener onStockListener;
    private Activity context;

    public StockAdapter(final OnStockListener onStockListener, final Activity context) {
        this.context = context;
        this.onStockListener = onStockListener;
    }

    /**
     * Devuelve un elementos
     *
     * @param position
     * @return
     */
    @Override
    public Product getItem(int position) {
        return this.products.get(position);
    }

    /**
     * Actualiza la lista completa de productos
     *
     * @param products
     */
    public void setProducts(final List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        this.notifyDataSetChanged();
    }

    /**
     * Añade un producto al listado
     *
     * @param product
     */
    public void add(final Product product) {
        this.products.add(product);
        this.notifyDataSetChanged();
    }

    /**
     * Añadir un producto para posición indicada
     *
     * @param position
     * @param product
     */
    public void add(final int position, final Product product) {
        if (position > products.size()) {
            this.products.add(product);
        } else {
            this.products.add(position, product);
        }
    }

    /**
     * Eliminación de un producto de la lista de productos
     *
     * @param product
     */
    public void delete(final Product product) {
        this.products.remove(product);
        this.notifyDataSetChanged();
    }

    /**
     * Eliminado un listado de productos
     *
     * @param products
     */
    public void delete(final List<Product> products) {
        this.products.removeAll(products);
        this.notifyDataSetChanged();
    }

    /**
     * Actialización del producto
     *
     * @param product
     */
    public void update(final Product product) {
        int position = this.products.indexOf(product);
        this.products.set(position, product);
        this.notifyItemChanged(position);
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolderStock onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
        return new ViewHolderStock(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderStock holder, int position) {

        Product currentProduct = this.products.get(position);
        boolean isItemSelected = isSelected(position);
        holder.productName.setText(currentProduct.getName());
        holder.productCode.setText(currentProduct.getCode());
        holder.imgStock.setImageResource(currentProduct.getShop().getImgThumb());

        String categoryTitle = ": "+currentProduct.getCategory().getName();
        holder.productCategory.setText(categoryTitle);


        String stockTitle = "";
        String stock = currentProduct.getStock().toString();
        if (currentProduct.getStock() == 1) {
            stockTitle = String.format(this.context.getString(R.string.product_adapter_unit), stock);
        } else {
            stockTitle = String.format(this.context.getString(R.string.product_adapter_units), stock);
        }

        SpannableString contentStock = new SpannableString(stockTitle);
        contentStock.setSpan(new UnderlineSpan(), 0, contentStock.length(), 0);
        holder.productStock.setText(contentStock);


        holder.setOnStockListener(position, currentProduct, this.onStockListener, isItemSelected);
        holder.view.setSelected(isSelected(position));


    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }


    /**
     * Clase Holder específica
     */
    static class ViewHolderStock extends RecyclerView.ViewHolder {

        private View view;

        @Bind(R.id.imgStock)
        ImageView imgStock;
        @Bind(R.id.productName)
        TextView productName;
        @Bind(R.id.productCode)
        TextView productCode;
        @Bind(R.id.productCategory)
        TextView productCategory;
        @Bind(R.id.productStock)
        TextView productStock;


        @Bind(R.id.containerStockItem)
        RelativeLayout containerStockItem;

        public ViewHolderStock(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, this.view);
        }


        public void setOnStockListener(final int position, final Product product, final OnStockListener onStockListener, final boolean isItemsSelected) {

            containerStockItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isItemsSelected) {
                        onStockListener.onEdit(product);
                    }
                }
            });

            containerStockItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onStockListener.onSelectItem(position, product);
                    return true;
                }
            });



            productStock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isItemsSelected) {
                        onStockListener.onShowUpdaterStock(product);
                    }
                }
            });

        }
    }
}
