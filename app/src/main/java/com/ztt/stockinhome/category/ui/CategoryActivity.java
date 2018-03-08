package com.ztt.stockinhome.category.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.StockInHomeApp;
import com.ztt.stockinhome.category.CategoryPresenter;
import com.ztt.stockinhome.category.di.CategoryComponent;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.category.ui.adapters.CategoryAdapter;
import com.ztt.stockinhome.commons.ui.ApplicationActivity;
import com.ztt.stockinhome.commons.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends ApplicationActivity implements CategoryView, OnCategoryListener, ActionMode.Callback {

    /**
     *
     */
    private ActionMode actionMode;


    @Bind(R.id.btnAdd)
    FloatingActionButton btnAdd;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;


    CategoryPresenter presenter;
    CategoryAdapter adapter;
    CategoryItemFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        this.setupToolBar();
        this.setupInyection();
        this.setupRecyclerview();

        this.presenter.onCreate();
        this.presenter.findAll();
    }

    @Override
    protected void onDestroy() {
        this.presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Inicialización del recyclerview
     */
    private void setupRecyclerview() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setAdapter(this.adapter);
    }

    /**
     * Inicialización de la inyección de depedencias
     */
    private void setupInyection() {
        StockInHomeApp app = this.getApplicationStockInHome();
        CategoryComponent component = app.getCategoryComponent(this, this);
        this.presenter = component.getPresenter();
        this.adapter = component.getAdapter();
        this.fragment = component.getCategoryItemFragment();
    }

    /**
     * Inicialización del toolbar
     */
    private void setupToolBar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.toolbar)
    public void onToolBarClick() {
        this.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void setCategories(List<Category> categories) {
        this.adapter.setCategories(categories);
    }


    @OnClick(R.id.btnAdd)
    public void addCategoryClick() {

        if (!this.fragment.isResumed()) {
            Bundle bundle = new Bundle();
            bundle.putString(CategoryItemFragment.ACTION, CategoryItemFragment.CREATE_ACTION);
            this.fragment.setArguments(bundle);
            this.fragment.show(getSupportFragmentManager(), "");
            this.hideBtnAdd();
        }


    }


    @Override
    public void onEditCategory(Category category) {

        if (!this.fragment.isResumed()) {
            Bundle bundle = new Bundle();
            bundle.putString(CategoryItemFragment.ACTION, CategoryItemFragment.EDIT_ACTION);
            bundle.putString(CategoryItemFragment.FIELD_ID, String.valueOf(category.getId()));
            bundle.putString(CategoryItemFragment.FIELD_NAME, category.getName());
            this.fragment.setArguments(bundle);
            this.fragment.show(getSupportFragmentManager(), "");
            this.hideBtnAdd();
        }


    }

    @Override
    public void onSelectItem(final int position, final Category category) {
        //Snackbar.make(this.mainContent,"Long click",Snackbar.LENGTH_SHORT).show();
        if (this.actionMode == null) {
            // Start the CAB using the ActionMode.Callback defined above
            this.actionMode = this.startSupportActionMode(this);
        }


        this.adapter.toggleSelection(position, category);

        int size = this.adapter.getSelectedItemCount();
        if (size > 0) {
            String title = String.format(getString(R.string.title_model_msg), size);
            actionMode.setTitle(title);

        } else {
            actionMode.finish();
        }
    }


    @Override
    public void onSaveCategory(Category category) {
        this.presenter.save(category);
        this.showBtnAdd();
    }

    @Override
    public void onCancelCategory() {
        this.showBtnAdd();
    }

    @Override
    public void onCreateCategorySuccess(final Category category) {
        this.adapter.addCategory(category);
        Snackbar.make(this.mainContent, R.string.category_create_success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateCategoryError() {

    }

    @Override
    public void onUpdateCategorySuccess(Category category) {
        this.adapter.updateCategory(category);
        Snackbar.make(this.mainContent, R.string.category_update_success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateCategoryError() {

    }

    @Override
    public void onDeleteCategorySuccess(Category category) {
        this.adapter.deleteCategory(category);
        this.actionMode.finish();
        Snackbar.make(this.mainContent, R.string.category_delete_success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCategoryError() {

    }

    @Override
    public void onDeleteCategoriesSuccess(List<Category> categories) {
        String msg = "";
        if (categories.size() > 1) {
            msg = String.format(getString(R.string.categories_deleted_success), categories.size());
        } else {
            msg = String.format(getString(R.string.category_deleted_success), categories.size());
        }
        Snackbar.make(this.mainContent, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCategoriesError() {

    }


    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_stock_selected, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menu) {


        if (menu.getItemId() == R.id.menu_delete) {

            final List<Category> categoriesDelete = new ArrayList<Category>();
            final Map<Integer, Category> categoryMap = new HashMap<Integer, Category>();
            SparseArrayCompat<Category> selectedItems = adapter.getSelected();
            for (int i = 0; i < selectedItems.size(); i++) {
                int position = selectedItems.keyAt(i);
                Category item = selectedItems.get(position);
                categoryMap.put(position, item);
                categoriesDelete.add(item);
            }

            String deleteMsg = String.format(getString(R.string.msg_deleted), categoriesDelete.size());


            Snackbar snackbar = Snackbar
                    .make(mainContent, deleteMsg, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            categoriesDelete.clear();
                            for (Map.Entry<Integer, Category> entry : categoryMap.entrySet()) {
                                int position = entry.getKey();
                                Category item = entry.getValue();
                                adapter.addCategory(position, item);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }).setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            //see Snackbar.Callback docs for event details
                            System.out.println();
                            if (!categoriesDelete.isEmpty()) {
                                presenter.deleteItems(categoriesDelete);
                            }
                            //adapter.clearSelections();
                        }
                    });

            snackbar.show();
            this.adapter.deleteCategories(categoriesDelete);
            this.actionMode.finish();
            return true;

        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
        this.adapter.clearSelections();
    }

    /**
     * Muestra el icono de añadir
     */
    public void showBtnAdd(){
        this.btnAdd.setVisibility(View.VISIBLE);
    }

    /**
     *
     * Oculta el icono de añadir
     */
    public void hideBtnAdd(){
        this.btnAdd.setVisibility(View.GONE);
    }
}
