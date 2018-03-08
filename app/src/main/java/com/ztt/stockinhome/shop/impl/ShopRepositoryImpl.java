package com.ztt.stockinhome.shop.impl;

import com.orm.SugarRecord;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.shop.ShopRepository;
import com.ztt.stockinhome.shop.events.ShopEvent;
import com.ztt.stockinhome.shop.model.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 15/10/2016.
 */

public class ShopRepositoryImpl implements ShopRepository {

    private EventBus eventBus;

    public ShopRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getAll() {
        List<Shop> entities = null;

        entities = SugarRecord.listAll(Shop.class);
        if (entities == null){
            entities = new ArrayList<Shop>();
        }

        this.postItems(ShopEvent.READ_EVENT,entities);

    }

    /**
     * Búsqueda de tienda por identificador
     * @param id
     * @return
     */
    private Shop findById(final Long id){
        Shop entity = SugarRecord.findById(Shop.class,id);
        return entity;
    }

    /**
     * Evento para la lista de tiendas
     * @param type Tipado de la tienda
     * @param shops Listado de tiendas
     */
    private void postItems(final int type, final List<Shop> shops){
        ShopEvent event = new ShopEvent();
        event.setType(type);
        event.setShops(shops);

        eventBus.post(event);
    }




}
