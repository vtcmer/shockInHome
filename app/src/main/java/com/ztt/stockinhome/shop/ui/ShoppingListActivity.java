package com.ztt.stockinhome.shop.ui;

import android.content.Intent;
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
import com.ztt.stockinhome.commons.ui.ApplicationActivity;
import com.ztt.stockinhome.commons.ui.DividerItemDecoration;
import com.ztt.stockinhome.shop.ShoppingListPresenter;
import com.ztt.stockinhome.shop.di.ShoppingListComponent;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.ui.adapters.ShoppingListAdapter;
import com.ztt.stockinhome.stock.ui.EditProductActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListActivity extends ApplicationActivity
            implements ShoppingListView, OnShoppingListListener,
                        ActionMode.Callback {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.btnAdd)
    FloatingActionButton btnAdd;
    @Bind(R.id.container)
    CoordinatorLayout container;

    /**
     *
     */
    private ActionMode actionMode;


    ShoppingListPresenter presenter;
    ShoppingListAdapter adapter;
    ShoppingListItemFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ButterKnife.bind(this);


        this.setupToolbar();
        this.setupInyection();
        this.setupRecyclerView();

        this.presenter.subscribe();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.findAll();
    }

    @Override
    protected void onDestroy() {
        this.presenter.unsubscribe();
        super.onDestroy();
    }

    /**
     * Inicialización del recycler view
     */
    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setAdapter(this.adapter);
    }


    /**
     * Inicialización de la inyección de dependencia
     */
    private void setupInyection() {
        StockInHomeApp app = this.getApplicationStockInHome();
        ShoppingListComponent component = app.getShoppingListComponent(this,this);
        this.presenter = component.getPresenter();
        this.adapter = component.getAdapter();
        this.fragment = component.getShoppingListItemFragment();
    }

    /**
     * Inicialización del toolbar
     */
    private void setupToolbar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.toolbar)
    public void onToolBarClick() {
        this.recyclerView.smoothScrollToPosition(0);
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

            final List<ShoppingList> deleteItems = new ArrayList<ShoppingList>();

            final Map<Integer, ShoppingList> itemMap = new HashMap<Integer, ShoppingList>();
            SparseArrayCompat<ShoppingList> selectedItems = adapter.getSelected();
            for (int i = 0; i < selectedItems.size(); i++) {
                int position = selectedItems.keyAt(i);
                ShoppingList item = selectedItems.get(position);
                itemMap.put(position, item);
                deleteItems.add(item);
            }

            String deleteMsg = String.format(getString(R.string.msg_deleted), deleteItems.size());

            Snackbar snackbar = Snackbar
                    .make(container, deleteMsg, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deleteItems.clear();
                            for (Map.Entry<Integer, ShoppingList> entry : itemMap.entrySet()) {
                                int position = entry.getKey();
                                ShoppingList item = entry.getValue();
                                adapter.add(position, item);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }).setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            //see Snackbar.Callback docs for event details

                            if (!deleteItems.isEmpty()) {
                                presenter.delete(deleteItems);
                            }
                            //adapter.clearSelections();
                        }
                    });


            snackbar.show();
            this.adapter.delete(deleteItems);
            this.actionMode.finish();

        }



        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
        this.adapter.clearSelections();
    }

    @Override
    public void onSave(ShoppingList shoppingList) {
        this.presenter.save(shoppingList);
        this.showBtnAdd();
    }

    @Override
    public void onCancel() {
        this.showBtnAdd();
    }

    @OnClick(R.id.btnAdd)
    public void addClick() {

        if (!this.fragment.isResumed()) {
            Bundle bundle = new Bundle();
            bundle.putString(ShoppingListItemFragment.ACTION, ShoppingListItemFragment.CREATE_ACTION);
            this.fragment.setArguments(bundle);
            this.fragment.show(getSupportFragmentManager(), "");
            this.hideBtnAdd();
        }


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


    @Override
    public void onEdit(ShoppingList shoppingList) {

        if (!this.fragment.isResumed()) {
            Bundle bundle = new Bundle();
            bundle.putString(ShoppingListItemFragment.ACTION, ShoppingListItemFragment.EDIT_ACTION);
            bundle.putLong(ShoppingListItemFragment.FIELD_ID, shoppingList.getId());
            bundle.putString(ShoppingListItemFragment.FIELD_NAME, shoppingList.getName());
            bundle.putInt(ShoppingListItemFragment.FIELD_ITEMS,shoppingList.getItems());
            bundle.putBoolean(ShoppingListItemFragment.FIELD_COMPLETE,shoppingList.getComplete());
            this.fragment.setArguments(bundle);
            this.fragment.show(getSupportFragmentManager(), "");
            this.hideBtnAdd();
        }

    }

    @Override
    public void onItemSelected(int position, ShoppingList shoppingList) {
        if (this.actionMode == null) {
            // Start the CAB using the ActionMode.Callback defined above
            this.actionMode = this.startSupportActionMode(this);
        }


        this.adapter.toggleSelection(position, shoppingList);

        int size = this.adapter.getSelectedItemCount();
        if (size > 0) {
            String title = String.format(getString(R.string.title_model_msg), size);
            actionMode.setTitle(title);

        } else {
            actionMode.finish();
        }
    }

    @Override
    public void showDetail(ShoppingList shoppingList) {
        Intent intent = new Intent(this, ShoppingListDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(ShoppingListDetailActivity.FIELD_ID, shoppingList.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onCreatedSuccess(ShoppingList shoppingList) {

        this.adapter.add(shoppingList);
        Snackbar.make(this.container, R.string.shoppinglist_create_success, Snackbar.LENGTH_SHORT).show();



    }

    @Override
    public void onReadItems(List<ShoppingList> shoppingList) {
        this.adapter.set(shoppingList);
    }

    @Override
    public void onUpdatedSuccess(ShoppingList shoppingList) {
        this.adapter.update(shoppingList);
        Snackbar.make(this.container, R.string.shoppinglist_update_success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItems(List<ShoppingList> shoppingList) {
        this.adapter.delete(shoppingList);
        Snackbar.make(this.container, R.string.shoppinglist_delete_success, Snackbar.LENGTH_SHORT).show();
    }
}
