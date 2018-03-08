package com.ztt.stockinhome.products.ui;

import com.ztt.stockinhome.products.enumerations.EnumProductFormat;

/**
 * Created by vtcmer on 07/01/2017.
 */

public enum EnumScanProductAction {

    ADD(1),
    EDIT(2),
    REMOVE(3);

    private Integer value;

    EnumScanProductAction(final Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }


    /**
     * Recuperación del tipo enumerado a partir de un valor
     * @param val
     * @return
     */
    public static EnumScanProductAction find(final Integer val){

        EnumScanProductAction action = null;
        for(EnumScanProductAction enumScanProductAction : EnumScanProductAction.values()){
            if (enumScanProductAction.value.equals(val)){
                action = enumScanProductAction;
                break;
            }
        }
        return action;
    }

}
