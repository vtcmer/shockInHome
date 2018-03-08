package com.ztt.stockinhome.category.ui;

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
import com.ztt.stockinhome.category.model.Category;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class CategoryItemFragment extends DialogFragment implements DialogInterface.OnShowListener {

    public static final String ACTION = "action";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String CREATE_ACTION = "create";
    public static final String EDIT_ACTION = "edit";

    private CategoryActivity categoryActivity;


    private String action;
    private Category category;

    @Bind(R.id.editCategoryName)
    EditText editCategoryName;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.category)
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

        this.categoryActivity = (CategoryActivity) getActivity();
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_add_category_item, null);
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
                    category.setName(editCategoryName.getText().toString());
                    categoryActivity.onSaveCategory(category);
                    dismiss();

                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryActivity.onCancelCategory();
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
        this.category = new Category();
        this.action = bundle.getString(ACTION);

        if (this.action.equals(EDIT_ACTION)){
            category.setId(Long.valueOf(bundle.getString(FIELD_ID)));
            category.setName(bundle.getString(FIELD_NAME));
            this.editCategoryName.setText(category.getName());
        }
    }
}
