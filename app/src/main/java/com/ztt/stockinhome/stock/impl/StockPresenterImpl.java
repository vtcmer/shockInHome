package com.ztt.stockinhome.stock.impl;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.events.ProductListEvent;
import com.ztt.stockinhome.events.ProductSingleEvent;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.ShopInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailInteractor;
import com.ztt.stockinhome.shop.ShoppingListInteractor;
import com.ztt.stockinhome.shop.events.ShopEvent;
import com.ztt.stockinhome.shop.events.ShoppingListEvent;
import com.ztt.stockinhome.shop.model.Shop;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;
import com.ztt.stockinhome.stock.StockFilter;
import com.ztt.stockinhome.stock.StockPresenter;
import com.ztt.stockinhome.stock.ui.StockView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 11/08/2016.
 */
public class StockPresenterImpl implements StockPresenter {


    private EventBus eventBus;
    private StockView view;
    private ProductInteractor productInteractor;
    private ShoppingListInteractor shoppingListInteractor;
    private ShoppingListDetailInteractor shoppingListDetailInteractor;
    private ShopInteractor shopInteractor;
    private CategoryInteractor categoryInteractor;



    public StockPresenterImpl(EventBus eventBus, StockView view,
                              ProductInteractor productInteractor,
                              ShoppingListInteractor shoppingListInteractor,
                              ShoppingListDetailInteractor shoppingListDetailInteractor,
                              ShopInteractor shopInteractor,
                              CategoryInteractor categoryInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.productInteractor = productInteractor;
        this.shoppingListInteractor = shoppingListInteractor;
        this.shoppingListDetailInteractor = shoppingListDetailInteractor;
        this.shopInteractor = shopInteractor;
        this.categoryInteractor = categoryInteractor;
    }

    @Override
    public void onCreate() {
        this.eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view = null;
        this.eventBus.unregister(this);
    }

    @Override
    public void addProductsToShoppingList(final List<ShoppingListDetail> details) {
        this.shoppingListDetailInteractor.add(details);
    }

    @Override
    public void updatedStock(Long id, Integer units) {
        this.productInteractor.update(id,units);
    }

    @Override
    public void getAllShops() {
        this.shopInteractor.findAll();
    }

    @Override
    public void getAllCategories() {
        this.categoryInteractor.findAll();
    }

    @Override
    public void addScanProduct(Product product) {
        this.productInteractor.addScanProduct(product);
    }

    @Override
    public void editScanProduct(Product product) {
        this.productInteractor.editScanProduct(product);
    }

    @Override
    public void removeScanProduct(Product product) {
        this.productInteractor.removeScanProduct(product);
    }

    @Override
    @Subscribe
    public void onEventMainThread(CategoryListEvent events) {
        if (events.getType() == CategoryListEvent.READ_EVENT){
            if (this.view != null){
                List<Category> categories = events.getCategories();
                this.view.setCategories(categories);
            }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(ShopEvent events) {
        if (events.getType() == ShopEvent.READ_EVENT){
            if (this.view != null){
                List<Shop> shops = events.getShops();
                this.view.setShops(shops);
            }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(ProductListEvent event) {

        if (event.getType() == ProductListEvent.READ_PRODUCTS){
            if (this.view != null){
                this.view.renderProducts(event.getProducts());
                this.view.hideProgressBar();
            }
        } else if (event.getType() == ProductListEvent.DELETE_PRODUCTS){
            if (this.view != null){
                this.view.hideProgressBar();
            }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(ProductSingleEvent event) {

        if (event.getType() == ProductSingleEvent.READ_PRODUCT){
            if (this.view != null){
                this.view.renderProduct(event.getProduct());
                this.view.hideProgressBar();
            }
        } else if (event.getType() == ProductSingleEvent.UPDATE_PRODUCT){
            if (this.view != null){
                this.view.updatedProduct(event.getProduct());
                this.view.hideProgressBar();
            }
        } else if (event.getType() == ProductSingleEvent.ADD_PRODUCT_SCAN){

            if (view != null){
                Product product = event.getProduct();
                this.productInteractor.update(product.getId(),product.getStock(),true);
            }

        } else if (event.getType() == ProductSingleEvent.REMOVE_PRODUCT_SCAN){

            if (view != null){
                Product product = event.getProduct();
                List<Product> products = new ArrayList<Product>();
                products.add(product);
                this.productInteractor.deleteProducts(products);
            }

        }  else if (event.getType() == ProductSingleEvent.PRODUCT_ADD_NOT_FOUND){

            if (view != null){
                Product product = event.getProduct();
                this.view.onAddScanProductNotFound(product);

            }

        } else if (event.getType() == ProductSingleEvent.PRODUCT_NOT_FOUND){

            if (view != null){
                Product product = event.getProduct();
                this.view.onScanProductNotFound(product);

            }

        }
    }



    @Override
    @Subscribe
    public void onEventMainThread(ShoppingListEvent event) {
        if (this.view != null){

            switch (event.getType()){
                case ShoppingListEvent.READ_EVENT:
                    this.view.onFindShoppingList(event.getShoppingList());
                    break;

            }

        }
    }


    @Override
    public void getProductByCode(final Product product) {
        if (this.view != null){
            this.view.showProgressBar();
        }
        this.productInteractor.getProductByCode(product);
    }

    @Override
    public void loadProducts() {
        if (this.view != null){
            this.view.showProgressBar();
        }
        this.productInteractor.getProducts();

    }

    @Override
    public void findProductByFilter(final StockFilter filter) {
        this.productInteractor.findByFilter(filter);
    }

    @Override
    public void getProduct(Long id) {
        if (view != null){
            this.view.showProgressBar();
        }
        this.productInteractor.getProduct(id);
    }


    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void deleteProducts(List<Product> products) {
        if (view != null){
            this.view.showProgressBar();
        }
        this.productInteractor.deleteProducts(products);
    }

    @Override
    public void findAllShoppingList() {
        this.shoppingListInteractor.findlAll();
    }
}
