package com.ztt.stockinhome.category.impl;

import com.orm.SugarRecord;
import com.ztt.stockinhome.category.CategoryRepository;
import com.ztt.stockinhome.category.events.CategoryListEvent;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.category.events.CategoryEvent;
import com.ztt.stockinhome.libs.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtcmer on 02/09/2016.
 */
public class CategoryRepositoryImpl implements CategoryRepository {

    private EventBus eventBus;

    public CategoryRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void create(Category category) {
        Long id = SugarRecord.save(category);
        if (id != null){
            category.setId(id);
            postItem(CategoryEvent.CREATE_SUCCESS,category);
        } else {
            // TODO MARCAR EL PROBLEMA DE LA CREACIÓN
        }
    }

    @Override
    public void update(Category category) {

        try{
            SugarRecord.update(category);
        } catch(Throwable thw){
            thw.printStackTrace();
        }

        postItem(CategoryEvent.UPDATE_SUCCESS,category);


    }

    @Override
    public void delete(final Long id) {
        Category entity = this.findById(id);
        if (entity != null){
            SugarRecord.delete(entity);
            postItem(CategoryEvent.DELETE_SUCCESS,entity);
        }
    }

    @Override
    public void deleteItems(List<Category> categories) {
        int itemsDeleted = SugarRecord.deleteInTx(categories);
        if ((itemsDeleted > 0) && (itemsDeleted == categories.size())){
            postItems(CategoryListEvent.DELETE_ITEMS,categories);
        } else {
            // TODO  no se han eliminado todos los elementos
        }
    }

    @Override
    public void get(Long id) {
        Category entity = this.findById(id);
        postItem(CategoryEvent.EDIT_SUCCESS,entity);

    }



    @Override
    public void findAll() {
        List<Category> entities = null;

        try{
            entities = SugarRecord.listAll(Category.class);
        }catch (Throwable thw){
            thw.printStackTrace();
        }

        if (entities == null){
            entities = new ArrayList<Category>();
        }



        postItems(CategoryListEvent.READ_EVENT,entities);
    }

    @Override
    public boolean exist(final Category category) {

        boolean exist = true;

        List<Category> categories = SugarRecord.find(Category.class,"NAME = ? AND ID != ?",category.getName(), String.valueOf(category.getId()));
        if (categories.isEmpty()){
            exist = false;
        }

        return exist;
    }

    /**
     * Recuperación de un categoria por el identificador
     * @param id
     * @return
     */
    private Category findById(final Long id){
        Category category = SugarRecord.findById(Category.class, id);
        return category;
    }

    /**
     * Evento para un único producto
     * @param type
     * @param category
     */
    private void postItem(final int type, final Category category ){
        CategoryEvent event = new CategoryEvent();
        event.setType(type);
        event.setCategory(category);
        this.eventBus.post(event);
    }

    /**
     * Evento para una lista de productos
     * @param type
     * @param categories
     */
    private void postItems(final int type, final List<Category> categories){
        CategoryListEvent event = new CategoryListEvent();
        event.setType(type);
        event.setCategories(categories);
        eventBus.post(event);
    }
}
