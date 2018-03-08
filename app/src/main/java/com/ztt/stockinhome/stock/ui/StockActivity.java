package com.ztt.stockinhome.stock.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.commons.model.LabelValueBean;
import com.ztt.stockinhome.commons.ui.ConfigurationParamenters;
import com.ztt.stockinhome.commons.ui.DividerItemDecoration;
import com.ztt.stockinhome.commons.ui.OnUpdaterUnitProductListener;
import com.ztt.stockinhome.commons.ui.UpdaterUnitProductFragment;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.products.enumerations.EnumProductFormat;
import com.ztt.stockinhome.products.ui.EnumScanProductAction;
import com.ztt.stockinhome.scan.ui.ScanActivity;
import com.ztt.stockinhome.shop.model.Shop;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;
import com.ztt.stockinhome.shop.ui.OnShoppingListSelectedListener;
import com.ztt.stockinhome.stock.StockFilter;
import com.ztt.stockinhome.stock.StockPresenter;
import com.ztt.stockinhome.stock.di.StockComponent;
import com.ztt.stockinhome.stock.ui.adapters.StockAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StockActivity
        extends ScanActivity
        implements StockView, OnStockListener,
        OnUpdaterUnitProductListener,
        SearchView.OnQueryTextListener,
        OnShoppingListSelectedListener, ActionMode.Callback {


    /**
     * Acciones en la selección de elementos
     */
    private ActionMode actionMode;


    @Bind(R.id.recyclerViewStock)
    RecyclerView recyclerViewStock;
    @Bind(R.id.stock_content)
    CoordinatorLayout stockContent;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnScanMore)
    FloatingActionButton btnScanMore;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    StockPresenter presenter;
    StockAdapter adapter;
    ShoppingListSelectedFragment shoppingListSelectedFragment;
    FilterStockFragment filterFragment;
    UpdaterUnitProductFragment updaterUnitProductFragment;
    ConfigurationParamenters configParameter;

    @Bind(R.id.btnEdit)
    FloatingActionButton btnEdit;
    @Bind(R.id.txtBtnEdit)
    TextView txtBtnEdit;

    @Bind(R.id.btnAdd)
    FloatingActionButton btnAdd;
    @Bind(R.id.txtBtnAdd)
    TextView txtBtnAdd;


    @Bind(R.id.btnScanRemove)
    FloatingActionButton btnScanRemove;
    @Bind(R.id.txtBtnScanRemove)
    TextView txtBtnScanRemove;


    @Bind(R.id.btnScanAdd)
    FloatingActionButton btnScanAdd;
    @Bind(R.id.txtBtnScanAdd)
    TextView txtBtnScanAdd;



    private MenuItem filterActive;

    private Animation fab_open;
    private Animation fab_close;
    private Animation rotate_forward;
    private Animation rotate_backward;
    boolean show = false;

    private final StockFilter filter = new StockFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ButterKnife.bind(this);

        this.setupToolBar();
        this.setupInjection();
        this.setupRecyclerView();

        this.setupAnimation();

        this.presenter.onCreate();

    }

    private void setupAnimation() {
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

    }

    @Override
    protected void onScanSuccess(final Product product, final EnumScanProductAction action) {
        System.out.println();

        switch (action) {
            case ADD:
                this.presenter.addScanProduct(product);
                break;
            case EDIT:
                this.presenter.editScanProduct(product);
                break;
            case REMOVE:
                this.presenter.removeScanProduct(product);
                break;

            default:
                System.out.println("onScanSuccess Error. Action  not found : "+action.getValue());

        }


    }

    @Override
    protected void onScanError(final String msg) {
        // TODO ERROR EN  EL ESCANEO
        System.out.println("onScanError Error: "+msg);
    }

    @Override
    protected void onDestroy() {
        this.presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        filterActive = menu.findItem(R.id.action_filter_active);
        filterActive.setVisible(!this.configParameter.isEmptyFilterStock());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean result = false;
        switch (item.getItemId()) {
            case R.id.action_filter_shop:
                this.presenter.getAllShops();
                break;

            case R.id.action_filter_category:
                this.presenter.getAllCategories();
                break;


            case R.id.action_filter_active:
                this.resetFilter();
                item.setVisible(false);
                this.invalidateOptionsMenu();
                break;

            default:
                result = super.onOptionsItemSelected(item);
                break;
        }

        return result;
    }

    /**
     * Limpiado del filtro y realiza una nueva búsqueda
     */
    private void resetFilter() {
        this.cleanFilter();
        this.presenter.loadProducts();
    }

    /**
     * Limpia el contenido del filtro
     */
    private void cleanFilter(){
        this.configParameter.cleanFilterStock();
        this.filter.setName(null);
        this.filter.getCategories().clear();
        this.filter.getShops().clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!this.configParameter.isEmptyFilterStock()){
            StockFilter filter = new StockFilter();
            filter.setShops(this.getFilterPreferences(EnumFilterStock.FILTER_SHOPS));
            filter.setCategories(this.getFilterPreferences(EnumFilterStock.FILTER_CATEGORY));
            this.presenter.findProductByFilter(filter);
        } else {
            this.presenter.loadProducts();
        }

    }


    @Override
    public void renderProducts(List<Product> products) {
        this.adapter.setProducts(products);
    }

    @Override
    public void renderProduct(Product product) {
        // redirección a la pantalla de edición
        if (product.getId() == null) {
            // -- El producto no existe
        } else {
            this.presenter.onDestroy();
            Intent intent = new Intent(this, EditProductActivity.class);
            intent.putExtra(EditProductActivity.ACTION, EditProductActivity.ACTION_EDIT);
            intent.putExtra(EditProductActivity.ID, product.getId());
            startActivity(intent);
        }
    }

    @Override
    public void showProgressBar() {
        this.recyclerViewStock.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.recyclerViewStock.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFindShoppingList(List<ShoppingList> shoppingList) {


        if (!this.shoppingListSelectedFragment.isResumed()) {
            this.shoppingListSelectedFragment.setItems(shoppingList);
            this.shoppingListSelectedFragment.setListener(this);
            this.shoppingListSelectedFragment.show(getSupportFragmentManager(), "");
        }


    }

    @Override
    public void updatedProduct(Product product) {
        this.adapter.update(product);
        final String msg =  String.format(getString(R.string.product_updated_success), product.getName());
        Snackbar.make(this.stockContent, msg, Snackbar.LENGTH_SHORT).show();


    }

    @Override
    public void onScanProductNotFound(Product product) {
        final String msg =  String.format(getString(R.string.product_not_found), product.getCode());
        Snackbar.make(this.stockContent, msg, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void setCategories(List<Category> categories) {

        if (!this.filterFragment.isResumed()){

            List<LabelValueBean> lbs = new ArrayList<LabelValueBean>();
            for (Category cat: categories){
                LabelValueBean lb = new LabelValueBean();
                lb.setLabel(cat.getName());
                lb.setValue(cat.getId().toString());
                lbs.add(lb);
            }

            this.filterFragment.setItems(lbs);
            this.filterFragment.setSeletedItems(this.getFilterPreferences(EnumFilterStock.FILTER_CATEGORY));
            this.filterFragment.setFilterStockType(EnumFilterStock.FILTER_CATEGORY);
            this.filterFragment.setListener(this);
            this.filterFragment.setTitle(this.getResources().getString(R.string.category_filter_title));
            this.filterFragment.show(getSupportFragmentManager(),"");
        }

    }

    @Override
    public void setShops(List<Shop> shops) {

        if (!this.filterFragment.isResumed()){

            List<LabelValueBean> lbs = new ArrayList<LabelValueBean>();
            for (Shop sh: shops){
                LabelValueBean lb = new LabelValueBean();
                lb.setLabel(sh.getName());
                lb.setValue(sh.getId().toString());
                lbs.add(lb);
            }

            this.filterFragment.setItems(lbs);
            this.filterFragment.setSeletedItems(this.getFilterPreferences(EnumFilterStock.FILTER_SHOPS));
            this.filterFragment.setFilterStockType(EnumFilterStock.FILTER_SHOPS);
            this.filterFragment.setListener(this);
            this.filterFragment.setTitle(this.getResources().getString(R.string.shop_filter_title));
            this.filterFragment.show(getSupportFragmentManager(),"");
        }

    }

    /**
     * Inicialia el recyclerview
     */
    private void setupRecyclerView() {
        this.recyclerViewStock.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerViewStock.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewStock.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerViewStock.setAdapter(this.adapter);
    }

    /**
     * Inicialización de la inyección de dependecias
     */
    @Override
    protected void setupInjection() {
        super.setupInjection();
        StockComponent component = this.getApplicationStockInHome().getStockComponent(this, this, this);
        this.adapter = component.getAdapter();
        this.presenter = component.getPresenter();
        this.shoppingListSelectedFragment = new ShoppingListSelectedFragment();
        this.updaterUnitProductFragment = new UpdaterUnitProductFragment();
        this.filterFragment = new FilterStockFragment();
        this.configParameter = new ConfigurationParamenters(this);
    }

    private void setupToolBar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * Añadir un producto
     *
     * @param product
     */
    private void addProduct(final Product product) {
        this.presenter.addProduct(product);
    }

    /**
     * Eliminación de un producto
     *
     * @param product
     */
    private void deleteProduct(final Product product) {
        this.presenter.deleteProduct(product);
    }


    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_stock_selected, menu);
        this.btnScanMore.setVisibility(View.GONE);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }


    /**
     * Recuperación de la lista de producto seleccionados
     *
     * @return
     */
    private List<ShoppingListDetail> getProductSelectedToShoppingList(final ShoppingList shoppingList) {

        final List<ShoppingListDetail> productsSelected = new ArrayList<ShoppingListDetail>();

        SparseArrayCompat<Product> selectedItems = adapter.getSelected();
        for (int i = 0; i < selectedItems.size(); i++) {
            int position = selectedItems.keyAt(i);
            Product item = selectedItems.get(position);

            ShoppingListDetail detail = new ShoppingListDetail();
            detail.setProduct(item);
            detail.setShoppingList(shoppingList);

            productsSelected.add(detail);
        }

        return productsSelected;

    }

    private void showConfirmView() {

    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menu) {
        //List<Product> products = adapter.getSelectedItems();

        if (menu.getItemId() == R.id.menu_delete) {

            final List<Product> productsDelete = new ArrayList<Product>();
            final Map<Integer, Product> productMap = new HashMap<Integer, Product>();

            SparseArrayCompat<Product> selectedItems = adapter.getSelected();
            for (int i = 0; i < selectedItems.size(); i++) {
                int position = selectedItems.keyAt(i);
                Product item = selectedItems.get(position);
                productMap.put(position, item);
                productsDelete.add(item);
            }

            String deleteMsg = String.format(getString(R.string.msg_deleted), productsDelete.size());


            Snackbar snackbar = Snackbar
                    .make(stockContent, deleteMsg, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            productsDelete.clear();
                            for (Map.Entry<Integer, Product> entry : productMap.entrySet()) {
                                int position = entry.getKey();
                                Product item = entry.getValue();
                                adapter.add(position, item);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }).setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            //see Snackbar.Callback docs for event details
                            if (!productsDelete.isEmpty()) {
                                presenter.deleteProducts(productsDelete);
                            }
                        }
                    });

            snackbar.show();
            this.adapter.delete(productsDelete);
            this.actionMode.finish();
            return true;

        } else if (menu.getItemId() == R.id.menu_send) {
            this.presenter.findAllShoppingList();
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
        this.adapter.clearSelections();
        this.btnScanMore.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btnScanMore)
    public void showMoreAction() {
        // this.scan();
        //Intent intent = new Intent(this, EditProductActivity.class);
        //startActivity(intent);
//https://www.youtube.com/watch?v=orcpzMo7igQ
        if (this.show) {
            this.show = false;
            this.hideFloattingButtons();
        } else {
            this.show = true;
            this.showFloattingButtons();
        }

    }

    /**
     * Editar mediante scaner
     */
    @OnClick(R.id.btnEdit)
    public void edit() {
        this.scan(EnumScanProductAction.EDIT);
        System.out.println("Editar scan");
    }


    /**
     * Añadir de forma manual
     */
    @OnClick(R.id.btnAdd)
    public void addManual() {
        System.out.println("Add Product manual");
        this.presenter.onDestroy();
        Intent intent = new Intent(this, EditProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EditProductActivity.ACTION, EditProductActivity.ACTION_ADD);
        bundle.putString(EditProductActivity.FORMAT, EnumProductFormat.MANUAL.getValue());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Eliminar producto mediante scaner
     */
    @OnClick(R.id.btnScanRemove)
    public void scanRemove() {
        System.out.println("btnScanRemove");
        this.scan(EnumScanProductAction.REMOVE);
    }

    /**
     * Añadir producto mediante scanner
     */
    @OnClick(R.id.btnScanAdd)
    public void scanAdd() {
        System.out.println("btnScanAdd");
        this.scan(EnumScanProductAction.ADD);

    }

    /**
     * Evento creado producto al scanear
     * @param product
     */
    @Override
    public void onAddScanProductNotFound(final Product product){

        this.presenter.onDestroy();
        Intent intent = new Intent(this, EditProductActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(EditProductActivity.ACTION, EditProductActivity.ACTION_ADD);
        bundle.putString(EditProductActivity.FORMAT, EnumProductFormat.find(product.getFormat()).getValue());
        bundle.putString(EditProductActivity.CODE, product.getCode());

        intent.putExtras(bundle);
        startActivity(intent);

    }



    /**
     * Muestra los botones
     */
    private void showFloattingButtons() {

        this.btnScanMore.startAnimation(rotate_forward);

        this.btnEdit.setVisibility(View.VISIBLE);
        this.btnEdit.startAnimation(this.fab_open);
        this.btnEdit.setClickable(true);
        this.txtBtnEdit.setVisibility(View.VISIBLE);
        this.txtBtnEdit.startAnimation(this.fab_open);

        this.btnAdd.setVisibility(View.VISIBLE);
        this.btnAdd.startAnimation(this.fab_open);
        this.btnAdd.setClickable(true);
        this.txtBtnAdd.setVisibility(View.VISIBLE);
        this.txtBtnAdd.startAnimation(this.fab_open);


        this.btnScanAdd.setVisibility(View.VISIBLE);
        this.btnScanAdd.startAnimation(this.fab_open);
        this.btnScanAdd.setClickable(true);
        this.txtBtnScanAdd.setVisibility(View.VISIBLE);
        this.txtBtnScanAdd.startAnimation(this.fab_open);


        this.btnScanRemove.setVisibility(View.VISIBLE);
        this.btnScanRemove.startAnimation(this.fab_open);
        this.btnScanRemove.setClickable(true);
        this.txtBtnScanRemove.setVisibility(View.VISIBLE);
        this.txtBtnScanRemove.startAnimation(this.fab_open);

    }

    /**
     * Oculta los botones
     */
    private void hideFloattingButtons() {

        this.btnScanMore.startAnimation(rotate_backward);

        this.btnEdit.setVisibility(View.GONE);
        this.btnEdit.startAnimation(this.fab_close);
        this.btnEdit.setClickable(false);
        this.txtBtnEdit.setVisibility(View.GONE);
        this.txtBtnEdit.startAnimation(this.fab_close);


        this.btnAdd.setVisibility(View.GONE);
        this.btnAdd.startAnimation(this.fab_close);
        this.btnAdd.setClickable(false);
        this.txtBtnAdd.setVisibility(View.GONE);
        this.txtBtnAdd.startAnimation(this.fab_close);


        this.btnScanAdd.setVisibility(View.GONE);
        this.btnScanAdd.startAnimation(this.fab_close);
        this.btnScanAdd.setClickable(false);
        this.txtBtnScanAdd.setVisibility(View.GONE);
        this.txtBtnScanAdd.startAnimation(this.fab_close);


        this.btnScanRemove.setVisibility(View.GONE);
        this.btnScanRemove.startAnimation(this.fab_close);
        this.btnScanRemove.setClickable(false);
        this.txtBtnScanRemove.setVisibility(View.GONE);
        this.txtBtnScanRemove.startAnimation(this.fab_close);

    }


    @Override
    public void onEdit(Product product) {
        this.renderProduct(product);

    }

    @Override
    public void onSelectItem(int position, Product product) {

        if (this.actionMode == null) {
            // Start the CAB using the ActionMode.Callback defined above
            this.actionMode = this.startSupportActionMode(this);
        }


        this.adapter.toggleSelection(position, product);

        int size = this.adapter.getSelectedItemCount();
        if (size > 0) {
            String title = String.format(getString(R.string.title_model_msg), size);
            actionMode.setTitle(title);

        } else {
            actionMode.finish();
        }

    }


    /**
     * Evento para captura la lista de la compra seleccionada
     *
     * @param shoppingList
     */
    @Override
    public void onShoppingListItemSelected(final ShoppingList shoppingList) {

        List<ShoppingListDetail> details = this.getProductSelectedToShoppingList(shoppingList);
        this.actionMode.finish();
        this.presenter.addProductsToShoppingList(details);

    }

    @Override
    public void onShowUpdaterStock(Product product) {

        if (!this.updaterUnitProductFragment.isResumed()) {
            this.updaterUnitProductFragment.setListener(this);
            Bundle bundle = new Bundle();
            bundle.putLong(UpdaterUnitProductFragment.FIELD_ID, product.getId());
            bundle.putString(UpdaterUnitProductFragment.FIELD_NAME, product.getName());
            bundle.putInt(UpdaterUnitProductFragment.FIELD_UNITS, product.getStock());
            this.updaterUnitProductFragment.setArguments(bundle);
            this.updaterUnitProductFragment.show(getSupportFragmentManager(), "");
        }

    }

    @Override
    public void onFilterStock(final List<String> filterItem, final EnumFilterStock filterType) {
        this.saveFilterPreferences(filterItem,filterType);
    }




    @Override
    public void onUpdated(Long id, Integer units) {
        this.presenter.updatedStock(id, units);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filter.setName(query);
        this.presenter.findProductByFilter(filter);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        filter.setName(newText);
        this.presenter.findProductByFilter(filter);
        return true;
    }


    private List<String> getFilterPreferences(final EnumFilterStock filterStock ){
        return this.configParameter.getFilterStock(filterStock);
    }

    private void saveFilterPreferences(final List<String> items, final EnumFilterStock filterStock ){

        this.configParameter.saveFilterStock(items, filterStock );

        if (filterStock.equals(EnumFilterStock.FILTER_SHOPS)){
            filter.setShops(items);
        } else if (filterStock.equals(EnumFilterStock.FILTER_CATEGORY)){
            filter.setCategories(items);
        }

        this.presenter.findProductByFilter(filter);

        this.filterActive.setVisible(!this.configParameter.isEmptyFilterStock());
        this.invalidateOptionsMenu();

    }


}