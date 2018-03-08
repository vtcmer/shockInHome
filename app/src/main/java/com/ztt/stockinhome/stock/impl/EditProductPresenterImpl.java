package com.ztt.stockinhome.stock.impl;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.category.model.Category;
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
import com.ztt.stockinhome.stock.EditProductPresenter;
import com.ztt.stockinhome.stock.ui.EditProductView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by vtcmer on 16/10/2016.
 */

public class EditProductPresenterImpl implements EditProductPresenter {

    /**Event Bus para registrar los eventos*/
    private EventBus eventBus;
    /**Referencia a la vista*/
    private EditProductView view;

    /**Interactor para los productos*/
    private ProductInteractor productInteractor;
    /**Interactor para las categorias*/
    private CategoryInteractor categoryInteractor;
    /**Interactor para las tiendas*/
    private ShopInteractor shopInteractor;
    /**Listado de tiendas*/
    private ShoppingListInteractor shoppingListInteractor;
    /**Detalles de la lista de la compra*/
    private ShoppingListDetailInteractor shoppingListDetailInteractor;



    public EditProductPresenterImpl(EventBus eventBus, EditProductView view,
                                    ProductInteractor productInteractor,
                                    CategoryInteractor categoryInteractor,
                                    ShopInteractor shopInteractor,
                                    ShoppingListInteractor shoppingListInteractor,
                                    ShoppingListDetailInteractor shoppingListDetailInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.productInteractor = productInteractor;
        this.categoryInteractor = categoryInteractor;
        this.shopInteractor = shopInteractor;
        this.shoppingListInteractor = shoppingListInteractor;
        this.shoppingListDetailInteractor = shoppingListDetailInteractor;
    }

    @Override
    public void subscribe() {
        this.eventBus.register(this);
    }

    @Override
    public void unsubscribe() {
        this.view = null;
        this.eventBus.unregister(this);
    }

    @Override
    public void getAllCategories() {
        this.categoryInteractor.findAll();
    }

    @Override
    public void getAllShops() {
        this.shopInteractor.findAll();
    }

    @Override
    public void findAllShoppingList() {
        this.shoppingListInteractor.findlAll();
    }

    @Override
    public void save(Product product) {
        if (view != null){
            this.view.showProgressBar();
        }
        this.productInteractor.save(product);
    }


    @Override
    public void getProduct(Long id) {
        if (view != null){
            this.view.showProgressBar();
        }
        this.productInteractor.getProduct(id);
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
    public void onEventMainThread(ProductSingleEvent event) {

        if (event.getType() == ProductSingleEvent.CREATE_PRODUCT){
            if (this.view != null){
                this.view.hideProgressBar();
                this.view.onCreateSuccess(event.getProduct());
            }
        } else if (event.getType() == ProductSingleEvent.UPDATE_PRODUCT){
            if (this.view != null){
                this.view.hideProgressBar();
                this.view.onUpdateSuccess(event.getProduct());
            }
        } else if (event.getType() == ProductSingleEvent.READ_PRODUCT){
            if (this.view != null){
                this.view.hideProgressBar();
                this.view.onEditSuccess(event.getProduct());
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
    public void addProductsToShoppingList(ShoppingListDetail detail) {
        this.shoppingListDetailInteractor.add(detail);
    }
}
