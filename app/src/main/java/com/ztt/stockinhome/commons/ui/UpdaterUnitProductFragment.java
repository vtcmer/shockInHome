package com.ztt.stockinhome.commons.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.commons.ui.OnUpdaterUnitProductListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class UpdaterUnitProductFragment extends DialogFragment implements DialogInterface.OnShowListener {

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_UNITS = "UNITS";

    @Bind(R.id.productDescription)
    TextView productDescription;
    @Bind(R.id.btnMinus)
    ImageView btnMinus;
    @Bind(R.id.productUnits)
    EditText productUnits;
    @Bind(R.id.btnPlus)
    ImageView btnPlus;

    private Long id;
    private String name;
    private Integer units;


    private OnUpdaterUnitProductListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.update_unit_title)
                .setPositiveButton(R.string.btn_save,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .setNegativeButton(R.string.btn_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.product_inc, null);
        ButterKnife.bind(this, view);
        this.populateView();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }


    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {

            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer unitsUpdated = Integer.parseInt(productUnits.getText().toString());
                    listener.onUpdated(id,unitsUpdated);
                    dismiss();

                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @OnClick({R.id.btnMinus, R.id.btnPlus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlus:
                Integer valueInc  = Integer.valueOf(productUnits.getText().toString());
                valueInc++;
                productUnits.setText(valueInc.toString());
                break;
            case R.id.btnMinus:
                Integer valueDec  = Integer.valueOf(productUnits.getText().toString());
                valueDec--;
                if (valueDec < 0){
                    valueDec = 0;
                }
                productUnits.setText(valueDec.toString());
                break;
        }
    }


    /**
     * Establece el listener
     * @param listener
     */
    public void setListener(OnUpdaterUnitProductListener listener) {
        this.listener = listener;
    }

    /**
     * Recupera la información desde el bundle
     */
    private void populateView(){
        Bundle bundle = this.getArguments();
        this.id = bundle.getLong(FIELD_ID);
        this.name = bundle.getString(FIELD_NAME);
        this.units = bundle.getInt(FIELD_UNITS);

        this.productDescription.setText(this.name);
        this.productUnits.setText(this.units.toString());

    }
}
