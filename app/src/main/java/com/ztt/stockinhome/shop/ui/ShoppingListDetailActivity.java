package com.ztt.stockinhome.shop.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.Toast;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.StockInHomeApp;
import com.ztt.stockinhome.commons.ui.ApplicationActivity;
import com.ztt.stockinhome.commons.ui.DividerItemDecoration;
import com.ztt.stockinhome.commons.ui.OnUpdaterUnitProductListener;
import com.ztt.stockinhome.commons.ui.UpdaterUnitProductFragment;
import com.ztt.stockinhome.shop.ShoppingListDetailPresenter;
import com.ztt.stockinhome.shop.di.ShoppingListDetailComponent;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;
import com.ztt.stockinhome.shop.ui.adapters.ShoppingListDetailAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingListDetailActivity extends ApplicationActivity
        implements ShoppingListDetailView,
        OnUpdaterUnitProductListener,
        OnShoppingListDetailListener, ActionMode.Callback {

    public static final String FIELD_ID = "ID";

    /**
     * Identificador de la lista de la compra
     */
    Long shoppingListId = null;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.container)
    CoordinatorLayout container;
    private ActionMode mode;


    ShoppingListDetailPresenter presenter;
    ShoppingListDetailAdapter adapter;
    UpdaterUnitProductFragment updaterUnitProductFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        this.shoppingListId = bundle.getLong(FIELD_ID);

        this.setupToolBar();
        this.setupInyection();
        this.setupRecyclerView();

        this.presenter.subscribe();
    }

    @OnClick(R.id.toolbar)
    public void onToolBarClick() {
        this.recyclerView.smoothScrollToPosition(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean result = false;

        switch (item.getItemId()){
            case R.id.action_save:

                int totalItems = adapter.getItemCount();
                List<ShoppingListDetail> checkeItems = adapter.getCheckedItems();

                if (totalItems == checkeItems.size()){
                    presenter.updateStock(this.shoppingListId, checkeItems);
                } else{
                    Toast.makeText(this, R.string.shoppinglist_detail_not_all_selected_item, Toast.LENGTH_SHORT).show();
                }

                result = true;
                break;

            case R.id.action_clean:
                this.cleanAllItems();
                break;

            default:
                result = super.onOptionsItemSelected(item);
                break;
        }

        return result;
    }


    /**
     * Inicialización del toolbar
     */
    private void setupToolBar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Inicialización de la inyección de dependecias
     */
    private void setupInyection(){
        StockInHomeApp app = this.getApplicationStockInHome();
        ShoppingListDetailComponent component = app.getShoppingListDetailComponent(this,this,this);
        this.presenter = component.getPresenter();
        this.adapter = component.getAdapter();
        this.updaterUnitProductFragment = new UpdaterUnitProductFragment();
    }

    /**
     * Inicialización del recyclerview
     */
    private void setupRecyclerView(){

        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setAdapter(this.adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.findAllDetail(this.shoppingListId);
    }

    @Override
    protected void onDestroy() {
        this.presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_selected, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menu) {

        if (menu.getItemId() == R.id.menu_delete) {

            final List<ShoppingListDetail> deleteItems = new ArrayList<ShoppingListDetail>();

            final Map<Integer, ShoppingListDetail> itemMap = new HashMap<Integer, ShoppingListDetail>();
            SparseArrayCompat<ShoppingListDetail> selectedItems = adapter.getSelected();
            for (int i = 0; i < selectedItems.size(); i++) {
                int position = selectedItems.keyAt(i);
                ShoppingListDetail item = selectedItems.get(position);
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
                            for (Map.Entry<Integer, ShoppingListDetail> entry : itemMap.entrySet()) {
                                int position = entry.getKey();
                                ShoppingListDetail item = entry.getValue();
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

                        }
                    });


            snackbar.show();
            this.adapter.delete(deleteItems);
            this.mode.finish();

        }
        return true;
    }


    /**
     * Borrado de todos los productos asociados a una lista de la compra
     */
    private void cleanAllItems(){

        final List<ShoppingListDetail> removeItems = new ArrayList<ShoppingListDetail>();
        removeItems.addAll(this.adapter.getItems());

        if (!removeItems.isEmpty()) {

            String deleteMsg = String.format(getString(R.string.msg_deleted), removeItems.size());

            Snackbar snackbar = Snackbar
                    .make(container, deleteMsg, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (ShoppingListDetail detail : removeItems) {
                                adapter.add(detail);
                            }
                            removeItems.clear();
                            adapter.notifyDataSetChanged();

                        }
                    }).setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            if (!removeItems.isEmpty()) {
                                presenter.cleanShoppingList(shoppingListId);
                            }

                        }
                    });

            snackbar.show();
            this.adapter.setItems(new ArrayList<ShoppingListDetail>());
        } else {
            Snackbar.make(this.container, R.string.shoppinglists_empty, Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.mode = null;
        this.adapter.clearSelections();
    }

    @Override
    public void onReadDetails(List<ShoppingListDetail> details) {
        this.adapter.setItems(details);
    }

    @Override
    public void onCheckDetail(ShoppingListDetail detail) {
        this.adapter.update(detail);
    }

    @Override
    public void onDeletedSuccess(List<ShoppingListDetail> details) {
        this.adapter.delete(details);
        Snackbar.make(this.container, R.string.shoppinglist_details_delete_success, Snackbar.LENGTH_SHORT).show();

    }


    @Override
    public void onItemSelected(int position, ShoppingListDetail detail) {
        if (this.mode == null){
            this.mode = this.startSupportActionMode(this);
        }
        this.adapter.toggleSelection(position,detail);

        int size = this.adapter.getSelectedItemCount();
        if (size > 0) {
            String title = String.format(getString(R.string.title_model_msg), size);
            mode.setTitle(title);

        } else {
            mode.finish();
        }
    }

    @Override
    public void onCheck(ShoppingListDetail detail) {
        this.presenter.checked(detail);
    }

    @Override
    public void onUnCheck(ShoppingListDetail detail) {
        this.presenter.unchecked(detail);
    }

    @Override
    public void onShowUpdaterUnit(ShoppingListDetail detail) {

        if (!this.updaterUnitProductFragment.isResumed()) {
            this.updaterUnitProductFragment.setListener(this);
            Bundle bundle = new Bundle();
            bundle.putLong(UpdaterUnitProductFragment.FIELD_ID, detail.getId());
            bundle.putString(UpdaterUnitProductFragment.FIELD_NAME, detail.getProduct().getName());
            bundle.putInt(UpdaterUnitProductFragment.FIELD_UNITS, detail.getUnits());
            this.updaterUnitProductFragment.setArguments(bundle);
            this.updaterUnitProductFragment.show(getSupportFragmentManager(), "");
        }

    }

    @Override
    public void onUpdated(Long id, Integer units) {
        this.presenter.updatedUnits(id,units);

    }


    @Override
    public void onUpdatedUnitsSuccess(ShoppingListDetail detail) {
        this.adapter.update(detail);
    }

    @Override
    public void onUpdatedStockSuccess(final Long shoppingListId) {
        Toast.makeText(this, R.string.product_stock_updated, Toast.LENGTH_SHORT).show();
        this.presenter.findAllDetail(shoppingListId);
    }

    @Override
    public void onCleanSuccess() {
        Toast.makeText(this, R.string.shoppinglist_detail_clean, Toast.LENGTH_SHORT).show();
        this.adapter.setItems(new ArrayList<ShoppingListDetail>());
    }

}
