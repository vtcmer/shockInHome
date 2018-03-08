package com.ztt.stockinhome.shop.impl;

import com.orm.SugarRecord;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.shop.ShoppingListDetailRepository;
import com.ztt.stockinhome.shop.events.ShoppingListDetailEvent;
import com.ztt.stockinhome.shop.model.ShoppingList;
import com.ztt.stockinhome.shop.model.ShoppingListDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 10/11/2016.
 */

public class ShoppingListDetailRepositoryImpl implements ShoppingListDetailRepository {

    private EventBus eventBus;

    public ShoppingListDetailRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void findByShoppinList(Long shoppingListId) {
        List<ShoppingListDetail> details = SugarRecord.find(ShoppingListDetail.class,"SHOPPING_LIST_ID = ?",shoppingListId.toString());
        this.post(ShoppingListDetailEvent.READ_EVENT,details);
    }



    @Override
    public boolean existsProduct(Long shoppigListId, Long productId) {

        boolean exists = false;

        long total = SugarRecord.count(ShoppingListDetail.class,"SHOPPING_LIST_ID = ? AND PRODUCT_ID = ?",new String[]{shoppigListId.toString(), productId.toString()});
        if (total > 0){
            exists = true;
        }
        return exists;
    }


    @Override
    public void add(List<ShoppingListDetail> details) {

        SugarRecord.saveInTx(details);

    }

    @Override
    public void add(ShoppingListDetail detail) {

        ShoppingListDetail item = this.getShoppingListDetailByShoppingList(detail.getShoppingList().getId(), detail.getProduct().getId());

        if (item == null){
            SugarRecord.save(detail);
        } else {
            int units = detail.getUnits() + item.getUnits();
            item.setUnits(units);
            this.updateItem(item);
        }


    }


    @Override
    public void updateUnit(Long id, int units) {

        ShoppingListDetail detail = this.getItem(id);
        if (detail != null){
            detail.setUnits(units);
            this.updateItem(detail);

            this.post(ShoppingListDetailEvent.UPDATED_UNITS_SUCCESS,detail);
        }

    }

    @Override
    public void delete(List<ShoppingListDetail> details) {
        SugarRecord.deleteInTx(details);
        this.post(ShoppingListDetailEvent.DELETED_SUCCESS, details);
    }

    @Override
    public void removeAllProductInShoppingList(Long shoppingListId) {
        SugarRecord.deleteAll(ShoppingListDetail.class,"SHOPPING_LIST_ID = ?",shoppingListId.toString());
        this.post(ShoppingListDetailEvent.CLEAN_SUCCESS);
    }

    @Override
    public void check(Long id, boolean check) {

        ShoppingListDetail detail = this.getItem(id);
        if (detail != null){
            detail.setChecked(check);
            this.updateItem(detail);
            this.post(ShoppingListDetailEvent.CHECKED_SUCCESS,detail);
        }
    }

    @Override
    public void checkAll(Long shoppingListId, boolean check) {
        StringBuffer query = new StringBuffer("UPDATE SHOPPING_LIST_DETAIL SET CHECKED = ? ");
        String[] params = null;

        int checked = 0;
        if (check){
            checked = 1;
            params = new String[]{String.valueOf(checked),String.valueOf(shoppingListId)};
        }else{
            query.append(", UNITS = ? ");
            params = new String[]{String.valueOf(checked),"0",String.valueOf(shoppingListId)};
        }

        query.append(" WHERE SHOPPING_LIST_ID = ?");
        SugarRecord.executeQuery(query.toString(),params);

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(shoppingListId);
        ShoppingListDetail detail = new ShoppingListDetail();
        detail.setShoppingList(shoppingList);

        this.post(ShoppingListDetailEvent.UPDATED_STOCK_SUCCESS,detail);
    }

    /**
     * Actualización de un detalle
     * @param detail
     */
    private void updateItem(final ShoppingListDetail detail){
        SugarRecord.update(detail);
    }

    /**
     * Recuperación de un detalle
     * @param id
     * @return
     */
    private ShoppingListDetail getItem(final Long id){
        ShoppingListDetail detail = SugarRecord.findById(ShoppingListDetail.class, id);
        return detail;
    }


    /**
     * Recuperación de un item del detalle de un listado
     * @param shoppigListId
     * @param productId
     * @return
     */
    private ShoppingListDetail getShoppingListDetailByShoppingList(Long shoppigListId, Long productId){
        ShoppingListDetail detail = null;
        List<ShoppingListDetail> details = SugarRecord.find(ShoppingListDetail.class,"SHOPPING_LIST_ID = ? AND PRODUCT_ID = ?",new String[]{shoppigListId.toString(), productId.toString()});
        if ((details != null)&& (details.size() > 0)){
            detail = details.get(0);
        }
        return detail;
    }


    /**
     * Envía el evento al presentador
     * @param type
     */
    private void post(int type){
        ShoppingListDetailEvent event = new ShoppingListDetailEvent();
        event.setType(type);
        this.eventBus.post(event);
    }


    /**
     * Envío de eventos
     * @param type
     * @param detail
     */
    private void post(int type, ShoppingListDetail detail){
        List<ShoppingListDetail> details = new ArrayList<ShoppingListDetail>();
        details.add(detail);
        this.post(type,details);
    }

    /**
     * Envío del evento
     * @param type
     * @param details
     */
    private void post(int type, List<ShoppingListDetail> details) {
        ShoppingListDetailEvent event = new ShoppingListDetailEvent();
        event.setType(type);
        event.setDetails(details);

        this.eventBus.post(event);
    }
}
