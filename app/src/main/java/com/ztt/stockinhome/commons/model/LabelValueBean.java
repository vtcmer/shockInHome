package com.ztt.stockinhome.commons.model;

/**
 * Created by vtcmer on 06/12/2016.
 */

public class LabelValueBean {

    private String label;

    private String value;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof LabelValueBean){
            LabelValueBean lb = (LabelValueBean) obj;
            if ((this.value != null) && (lb.getValue() != null)) {
                equal = this.value.equals(lb.getValue());
            }
        }

        return equal;
    }
}
