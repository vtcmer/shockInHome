package com.ztt.stockinhome.shop.impl;

import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailInteractor;
import com.ztt.stockinhome.shop.ShoppingListDetailPresenter;
import com.ztt.stockinhome.shop.events.ShoppingListDetailEvent;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;
import com.ztt.stockinhome.shop.ui.ShoppingListDetailView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by vtcmer on 12/11/2016.
 */
public class ShoppingListDetailPresenterImpl implements ShoppingListDetailPresenter{

    private EventBus eventBus;
    private ProductInteractor productInteractor;
    private ShoppingListDetailInteractor interactor;
    private ShoppingListDetailView view;

    public ShoppingListDetailPresenterImpl(final EventBus eventBus,
                                           final ProductInteractor productInteractor,
                                           final ShoppingListDetailInteractor interactor,
                                           final ShoppingListDetailView view) {
        this.eventBus = eventBus;
        this.productInteractor = productInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void subscribe() {
      this.eventBus.register(this);
    }

    @Override
    public void unsubscribe() {
        this.eventBus.unregister(this);
        this.view = null;
    }

    @Override
    public void findAllDetail(final Long shoppingListId) {
        this.interactor.findByShoppinList(shoppingListId);
    }

    @Override
    public void delete(List<ShoppingListDetail> deleteItems) {
        this.interactor.delete(deleteItems);
    }


    @Override
    public void checked(ShoppingListDetail detail) {
        this.interactor.check(detail.getId(),true);
    }

    @Override
    public void unchecked(ShoppingListDetail detail) {
        this.interactor.check(detail.getId(),false);
    }

    @Override
    public void updatedUnits(Long id, Integer units) {
        this.interactor.updateUnit(id,units);
    }

    @Override
    public void updateStock(final Long shoppingListId, final List<ShoppingListDetail> checkedItems) {
        for(ShoppingListDetail detail: checkedItems){
            this.productInteractor.update(detail.getProduct().getId(), detail.getUnits(), true);
        }
        this.interactor.checkAll(shoppingListId,false);

    }

    @Override
    public void cleanShoppingList(Long shoppingListId) {
        this.interactor.removeAllProductInShoppingList(shoppingListId);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ShoppingListDetailEvent event) {

        if (this.view != null){

            switch (event.getType()){
                case ShoppingListDetailEvent.READ_EVENT:
                    this.view.onReadDetails(event.getDetails());
                     break;

                case ShoppingListDetailEvent.CHECKED_SUCCESS:
                    this.view.onCheckDetail(event.getDetails().get(0));
                    break;

                case ShoppingListDetailEvent.DELETED_SUCCESS:
                    this.view.onDeletedSuccess(event.getDetails());
                    break;

                case ShoppingListDetailEvent.UPDATED_UNITS_SUCCESS:
                    this.view.onUpdatedUnitsSuccess(event.getDetails().get(0));
                    break;

                case ShoppingListDetailEvent.UPDATED_STOCK_SUCCESS:
                    this.view.onUpdatedStockSuccess(event.getDetails().get(0).getShoppingList().getId());
                    break;

                case ShoppingListDetailEvent.CLEAN_SUCCESS:
                    this.view.onCleanSuccess();
                    break;


            }

        }

    }

}
