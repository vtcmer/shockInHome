package com.ztt.stockinhome.shop.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.shop.model.ShoppingList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class ShoppingListItemFragment extends DialogFragment implements DialogInterface.OnShowListener {

    public static final String ACTION = "action";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ITEMS = "items";
    public static final String FIELD_COMPLETE = "complete";
    public static final String CREATE_ACTION = "create";
    public static final String EDIT_ACTION = "edit";

    private ShoppingListActivity activity;


    private String action;
    private ShoppingList item;

    @Bind(R.id.editShoppingListName)
    EditText editShoppingListName;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.shoppinglist_list)
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

        this.activity = (ShoppingListActivity) getActivity();
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_add_shoppinglist_item, null);
        ButterKnife.bind(this, view);
        this.populateData();
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
                    item.setName(editShoppingListName.getText().toString());
                    activity.onSave(item);
                    dismiss();

                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onCancel();
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

    /**
     * Recupera la información desde el bundle
     */
    private void populateData(){
        Bundle bundle = this.getArguments();
        this.item = new ShoppingList();
        this.action = bundle.getString(ACTION);

        if (this.action.equals(EDIT_ACTION)){
            this.item.setId(bundle.getLong(FIELD_ID));
            this.item.setName(bundle.getString(FIELD_NAME));
            this.item.setItems(bundle.getInt(FIELD_ITEMS));
            this.item.setComplete(bundle.getBoolean(FIELD_COMPLETE));
            this.editShoppingListName.setText(this.item.getName());
        }
    }
}
