package com.ztt.stockinhome.shop.impl;

import com.orm.SugarRecord;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.shop.ShoppingListRepository;
import com.ztt.stockinhome.shop.events.ShoppingListEvent;
import com.ztt.stockinhome.shop.events.ShoppingListSingleEvent;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.List;

/**
 * Created by vtcmer on 26/10/2016.
 */

public class ShoppingListRepositoryImpl implements ShoppingListRepository {

    private EventBus eventBus;

    public ShoppingListRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void create(ShoppingList shoppingList) {

        Long id = SugarRecord.save(shoppingList);
        shoppingList.setId(id);

        this.post(ShoppingListSingleEvent.CREATED_SUCCESS, shoppingList);

    }


    @Override
    public void update(ShoppingList shoppingList) {

        SugarRecord.update(shoppingList);

        this.post(ShoppingListSingleEvent.UPDATED_SUCCESS, shoppingList);

    }

    @Override
    public void delete(ShoppingList shoppingList) {

        SugarRecord.delete(shoppingList);

        this.post(ShoppingListSingleEvent.DELETED_SUCCESS,shoppingList);

    }

    @Override
    public void get(Long id) {
        ShoppingList shoppingList = SugarRecord.findById(ShoppingList.class,id);
        this.post(ShoppingListSingleEvent.READ_SUCCESS,shoppingList);
    }

    @Override
    public void findAll(){
        List<ShoppingList> list = SugarRecord.listAll(ShoppingList.class);
        this.post(ShoppingListEvent.READ_EVENT,list);
    }

    @Override
    public void delete(List<ShoppingList> items) {
        int itemsDeleted = SugarRecord.deleteInTx(items);
        if ((itemsDeleted > 0) && (itemsDeleted == items.size())){
            this.post(ShoppingListEvent.DELETE_ITEMS,items);
        } else {
            // TODO  no se han eliminado todos los elementos
        }
    }


    /**
     * Envia el evento al presentador
     * @param type
     * @param shoppingList
     */
    private void post(int type, ShoppingList shoppingList) {
        ShoppingListSingleEvent event = new ShoppingListSingleEvent();
        event.setType(type);
        event.setShoppingList(shoppingList);

        eventBus.post(event);
    }

    private void post(int type, List<ShoppingList> shoppingList) {
        ShoppingListEvent event = new ShoppingListEvent();
        event.setType(type);
        event.setShoppingList(shoppingList);

        eventBus.post(event);
    }
}
