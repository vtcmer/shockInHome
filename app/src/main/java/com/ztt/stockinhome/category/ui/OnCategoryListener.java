package com.ztt.stockinhome.category.ui;

import com.ztt.stockinhome.category.model.Category;

/**
 * Created by vtcmer on 04/09/2016.
 */
public interface OnCategoryListener {

    /**
     * Guardado de la categoría
     * @param category
     */
    void onSaveCategory(final Category category);

    /**
     * Operación de cancel
     */
    void onCancelCategory();

    /**
     * Editar un categoría
     * @param category
     */
    void onEditCategory(final Category category);

    /**
     * Evento al seleccionar un elementos mediante pulsación larga
     * @param position
     * @param category
     */
    void onSelectItem(final int position, final Category category);

}
