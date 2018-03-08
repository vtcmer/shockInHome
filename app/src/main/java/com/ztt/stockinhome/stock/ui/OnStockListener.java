package com.ztt.stockinhome.stock.ui;

import com.ztt.stockinhome.commons.model.LabelValueBean;
import com.ztt.stockinhome.products.entities.Product;

import java.util.List;

/**
 * Created by vtcmer on 23/10/2016.
 */

public interface OnStockListener {

    /**
     * Al editar un producto
     * @param product
     */
    void onEdit(final Product product);

    /**
     * Al seleccionar producto
     * @param position
     * @param product
     */
    void onSelectItem(final int position, final Product product);

    /**
     * Actualiza las unidades del stock
     * @param product
     */
    void onShowUpdaterStock(Product product);

    /**
     * Filtro de categorias y tiendas
     * @param filterItem
     * @param filterType
     */
    void onFilterStock(final List<String> filterItem, final EnumFilterStock filterType);
}
