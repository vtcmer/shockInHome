package com.ztt.stockinhome.stock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.StockInHomeApp;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.commons.ui.ApplicationActivity;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.products.enumerations.EnumProductFormat;
import com.ztt.stockinhome.shop.model.Shop;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;
import com.ztt.stockinhome.shop.ui.OnShoppingListSelectedListener;
import com.ztt.stockinhome.stock.EditProductPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProductActivity extends ApplicationActivity implements EditProductView ,OnShoppingListSelectedListener {

    /**
     * Acciones a ejecutar Editar/Añadir/Eliminar
     */
    public static final String ACTION = "action";
    public static final String ID = "id";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_REMOVE = "remove";
    public static final String FORMAT = "format";
    public static final String CODE = "code";

    @Bind(R.id.productId)
    TextView productId;
    @Bind(R.id.productName)
    EditText productName;
    @Bind(R.id.productCode)
    EditText productCode;
    @Bind(R.id.productFormat)
    EditText productFormat;
    @Bind(R.id.productShop)
    Spinner productShop;
    @Bind(R.id.productCategory)
    Spinner productCategory;
    @Bind(R.id.productStock)
    EditText productStock;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.mainContentEdit)
    RelativeLayout mainContent;

    ArrayAdapter<Shop> adapterShop = null;
    ArrayAdapter<Category> adapterCategory = null;

    ShoppingListSelectedFragment shoppingListSelectedFragment = null;

    private EditProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ButterKnife.bind(this);

        this.setupInyection();
        this.setupSpinner();

        this.presenter.subscribe();
        this.presenter.getAllCategories();
        this.presenter.getAllShops();

        this.populateProductFromBundle();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Boolean bool = false;

        switch (item.getItemId()) {
            case R.id.menu_send:
                this.presenter.findAllShoppingList();
                bool = true;
                break;
            default:
                bool  = super.onOptionsItemSelected(item);
        }

        return bool;
    }

    /**
     * Settea el titulo a nuevo producto
     */
    private void setTitleNewProduct(){
        this.setTitle(this.getResources().getString(R.string.product_create_new_title));
    }


    /**
     * Setea el titulo a modificar el producto
     */
    private void setTitleEditProduct(){
        this.setTitle(this.getResources().getString(R.string.product_edit_title));
    }

    /**
     * Se rellena la vista con la información del bundle
     */
    private void populateProductFromBundle() {

        Bundle bundle = getIntent().getExtras();
        String operation = bundle.getString(ACTION);
        String formatValue = bundle.getString(FORMAT);

        Product product = new Product();

        if (operation.equalsIgnoreCase(ACTION_ADD)) {

            // -- Añadir un producto
            setTitleNewProduct();
            EnumProductFormat format = EnumProductFormat.find(formatValue);

            product.setFormat(formatValue);
            product.setStock(1);
            if (format.equals(EnumProductFormat.MANUAL)) {
                long code = (-1) * System.currentTimeMillis();
                product.setCode(String.valueOf(code));

            } else {

                String code = bundle.getString(CODE);
                product.setCode(code);
            }

            this.objectToView(product);

        } else if (operation.equalsIgnoreCase(ACTION_EDIT)) {
            // -- Editar producto
            this.setTitleEditProduct();
            Long id = bundle.getLong(ID);
            if (id != null){
                this.presenter.getProduct(id);
            }


        } else if (operation.equalsIgnoreCase(ACTION_REMOVE)) {

        }

    }

    private void setupSpinner() {

        this.adapterShop =
                new ArrayAdapter<Shop>(getApplicationContext(), R.layout.stock_simple_spinner);
        this.adapterShop.setDropDownViewResource(R.layout.stock_simple_spinner_dropdown);
        this.productShop.setAdapter(this.adapterShop);


        this.adapterCategory =
                new ArrayAdapter<Category>(getApplicationContext(), R.layout.stock_simple_spinner);
        this.adapterCategory.setDropDownViewResource(R.layout.stock_simple_spinner_dropdown);
        this.productCategory.setAdapter(this.adapterCategory);


    }

    /**
     * Inyección de dependencias
     */
    private void setupInyection() {

        StockInHomeApp app = this.getApplicationStockInHome();
        this.presenter = app.getEditProductComponent(this).getPresenter();
        this.shoppingListSelectedFragment = new ShoppingListSelectedFragment();
    }

    @Override
    protected void onDestroy() {
        this.presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setCategories(List<Category> categories) {

        this.adapterCategory.clear();
        this.adapterCategory.addAll(categories);
        this.adapterCategory.notifyDataSetChanged();

    }

    @Override
    public void setShops(List<Shop> shops) {

        this.adapterShop.clear();
        this.adapterShop.addAll(shops);
        this.adapterShop.notifyDataSetChanged();
    }

    @OnClick(R.id.btnSave)
    public void onSave() {

        Product product = this.viewToObject();
        this.presenter.save(product);

    }

    @OnClick(R.id.btnCancel)
    public void onCancel(){
        Intent intent = new Intent(this, StockActivity.class);
        this.startActivity(intent);
        // TODO MIRAR PARA AÑADIR LOS FLAG
    }


    @Override
    public void showProgressBar() {

        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {

       this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateSuccess(Product product) {

        final String msg =  String.format(getString(R.string.product_created_success), product.getName());
        Snackbar.make(this.mainContent, msg, Snackbar.LENGTH_SHORT).show();
        this.setTitleEditProduct();
        this.objectToView(product);
    }

    @Override
    public void onUpdateSuccess(Product product) {
        final String msg =  String.format(getString(R.string.product_updated_success), product.getName());
        Snackbar.make(this.mainContent, msg, Snackbar.LENGTH_SHORT).show();
        this.objectToView(product);
    }

    @Override
    public void onEditSuccess(Product product) {
        this.objectToView(product);
    }

    @Override
    public void onFindShoppingList(List<ShoppingList> shoppingList) {

        if (!this.shoppingListSelectedFragment.isResumed()) {
            //Bundle bundle = new Bundle();
            //this.shoppingListSelectedFragment.setArguments(bundle);
            this.shoppingListSelectedFragment.setItems(shoppingList);
            this.shoppingListSelectedFragment.setListener(this);
            this.shoppingListSelectedFragment.show(getSupportFragmentManager(), "");
        }

    }

    /**
     * Setea los valores de la vista al objeto
     */
    private Product viewToObject() {


        Shop shop = (Shop) this.productShop.getSelectedItem();
        Category category = (Category) this.productCategory.getSelectedItem();

        Product product = new Product();
        if (!TextUtils.isEmpty(this.productId.getText().toString())) {
            product.setId(Long.valueOf(this.productId.getText().toString()));
        }
        product.setName(this.productName.getText().toString());
        product.setCode(this.productCode.getText().toString());
        product.setFormat(this.productFormat.getText().toString());
        product.setShop(shop);
        product.setCategory(category);
        product.setStock(Integer.parseInt(this.productStock.getText().toString()));


        return product;

    }

    /**
     * Setea los valores del objeto a la vista
     *
     * @param product
     */
    private void objectToView(final Product product) {

        if (product != null) {

            if (product.getId() != null) {
                this.productId.setText(product.getId().toString());
            }
            this.productName.setText(product.getName());
            this.productCode.setText(product.getCode());
            this.productFormat.setText(product.getFormat());
            this.productStock.setText(product.getStock().toString());


            int shopPosition = 0;
            if (product.getShop() != null){
                shopPosition = this.adapterShop.getPosition(product.getShop());
            }
            this.productShop.setSelection(shopPosition);

            int categoryPosition = 0;
            if (product.getCategory() != null){
                categoryPosition = this.adapterCategory.getPosition(product.getCategory());
            }
            this.productCategory.setSelection(categoryPosition);



        }

    }

    @Override
    public void onShoppingListItemSelected(ShoppingList shoppingList) {

        Product product = this.viewToObject();
        ShoppingListDetail detail = new ShoppingListDetail();
        detail.setShoppingList(shoppingList);
        detail.setProduct(product);
        this.presenter.addProductsToShoppingList(detail);


    }
}
