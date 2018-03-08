package com.ztt.stockinhome.products.enumerations;

/**
 * Created by vtcmer on 20/10/2016.
 */

public enum EnumProductFormat {

    MANUAL("MANUAL"),
    EAN_13("EAN_13");

    private String value;

    EnumProductFormat(final String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    /**
     * Recuperación del tipo enumerado a partir de un valor
     * @param formatValue
     * @return
     */
    public static EnumProductFormat find(final String formatValue){

        EnumProductFormat format = null;
        for(EnumProductFormat formatEnumProductFormat : EnumProductFormat.values()){
            if (formatEnumProductFormat.value.equalsIgnoreCase(formatValue)){
                format = formatEnumProductFormat;
                break;
            }
        }
        return format;
    }
}
