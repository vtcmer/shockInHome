package com.ztt.stockinhome.commons.ui;

/**
 * Created by vtcmer on 13/11/2016.
 */

public interface OnUpdaterUnitProductListener {

    /**
     * Actualización de las unidades para el id
     * @param id
     * @param units
     */
    public void onUpdated(final Long id, final Integer units);
}
