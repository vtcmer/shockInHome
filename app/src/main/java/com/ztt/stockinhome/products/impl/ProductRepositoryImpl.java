package com.ztt.stockinhome.products.impl;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.ztt.stockinhome.events.ProductEvent;
import com.ztt.stockinhome.events.ProductListEvent;
import com.ztt.stockinhome.events.ProductSingleEvent;
import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductRepository;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.stock.StockFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.filter;

/**
 * Created by vtcmer on 11/08/2016.
 */
public class ProductRepositoryImpl implements ProductRepository {

    private EventBus eventBus;


    public ProductRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void findProducts() {
        List<Product> products = SugarRecord.listAll(Product.class);
        post(ProductListEvent.READ_PRODUCTS,products);
    }

    @Override
    public void getProductByCode(final Product product) {

        this.post(ProductSingleEvent.READ_PRODUCT,product);
    }

    @Override
    public void save(Product product) {

        try {
            long id = SugarRecord.save(product);
            product.setId(id);
        } catch(Throwable thw){
            thw.printStackTrace();
        }
        this.post(ProductSingleEvent.CREATE_PRODUCT,product);
    }

    @Override
    public void update(Long id, Integer stock) {
        Product product = this.getItem(id);
        product.setStock(stock);
        this.update(product);
    }

    @Override
    public void update(Long id, Integer units, boolean add) {
        Product product = this.getItem(id);
        int value = product.getStock();
        if (add){
            value += units;
        }else{
            value -= units;
            if (value < 0){
                value = 0;
            }
        }
        product.setStock(value);
        this.update(product);
    }

    @Override
    public void update(Product product) {
        try{
            SugarRecord.update(product);
        } catch(Throwable thw){
            thw.printStackTrace();
        }
        this.post(ProductSingleEvent.UPDATE_PRODUCT, product);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(List<Product> products) {
        int itemDeleted = SugarRecord.deleteInTx(products);
        this.post(ProductListEvent.DELETE_PRODUCTS, products);
    }

    @Override
    public void get(Long id) {

        Product product = this.getItem(id);
        this.post(ProductSingleEvent.READ_PRODUCT,product);
    }

    @Override
    public void findByFilter(StockFilter filter) {

        StringBuffer sql = new StringBuffer("");
        List<String> params = new ArrayList<String>();

        boolean setAnd = false;

        if ((filter.getName() != null) && (!filter.getName().isEmpty())){
            final StringBuffer filterName = new StringBuffer("%");
            filterName.append(filter.getName());
            filterName.append("%");
            sql.append(" NAME like ? COLLATE NOCASE ");
            params.add(filterName.toString());

            setAnd = true;
        }

        if ((filter.getShops() != null) && (filter.getShops().size() > 0)){

            if (setAnd){
              sql.append(" AND ");
            } else {
                setAnd = true;
            }

            sql.append(" SHOP IN (");

            for (String st: filter.getShops()) {
                sql.append("?,");
            }
            sql.delete(sql.length()-1,sql.length());

            sql.append(")");

            params.addAll(filter.getShops());
        }


        if ((filter.getCategories() != null) && (filter.getCategories().size() > 0)){

            if (setAnd){
                sql.append(" AND ");
            }

            sql.append(" CATEGORY IN (");

            for (String st: filter.getCategories()) {
                sql.append("?,");
            }
            sql.delete(sql.length()-1,sql.length());

            sql.append(")");


            params.addAll(filter.getCategories());
        }

        List<Product> products = SugarRecord.find(Product.class,sql.toString(),params.toArray(new String[]{}));
        post(ProductListEvent.READ_PRODUCTS,products);

    }

    private List<Product> findProductWithCode(final Product product) {

        List<Product> products = null;

        if (product != null){

            StringBuffer sql = new StringBuffer("");
            List<String> params = new ArrayList<String>();
            sql.append(" CODE LIKE ? ");
            params.add(product.getCode());

            String sortCode = product.getCode().substring(0,9);
            sql.append(" OR CODE LIKE ? ");
            params.add(sortCode);

            products = SugarRecord.find(Product.class,sql.toString(),params.toArray(new String[]{}));
        }

        return products;

    }

    @Override
    public void editScanProduct(Product product) {

        List<Product> products = this.findProductWithCode(product);

        if ((products != null) && !products.isEmpty()){

            if (products.size() == 1){
                this.post(ProductSingleEvent.READ_PRODUCT,products.get(0));
            } else {
                // TODO MÁS DE UN PRODUCTO, ALGO NO VA BIEN
                this.post(ProductListEvent.MORE_SCAN_PRODUCTS,products);
            }

        } else {
            // TODO EL PRODUCTO NO ESTÁ SE DEVUELVE PARA AÑADIR NUEVO PRODUCTO
            this.post(ProductSingleEvent.PRODUCT_NOT_FOUND,product);
        }

    }

    @Override
    public void removeScanProduct(Product product) {

        List<Product> products = this.findProductWithCode(product);

        if ((products != null) && !products.isEmpty()){

            if (products.size() == 1){
                this.post(ProductSingleEvent.REMOVE_PRODUCT_SCAN,products.get(0));
            } else {
                // TODO MÁS DE UN PRODUCTO, ALGO NO VA BIEN
                this.post(ProductListEvent.MORE_SCAN_PRODUCTS,products);
            }

        } else {
            // TODO EL PRODUCTO NO ESTÁ SE DEVUELVE PARA AÑADIR NUEVO PRODUCTO
            this.post(ProductSingleEvent.PRODUCT_NOT_FOUND,product);
        }

    }

    @Override
    public void addScanProduct(Product product) {

        List<Product> products = this.findProductWithCode(product);

        if ((products != null) && !products.isEmpty()){

            if (products.size() == 1){
                // TODO AÑADIR EL PRODUCTO Y CONFIRMAR QUE SE AÑADIO
                Product productTmp = products.get(0);
                productTmp.setStock(1);
                this.post(ProductSingleEvent.ADD_PRODUCT_SCAN,products.get(0));
            } else {
                // TODO MÁS DE UN PRODUCTO, ALGO NO VA BIEN
                this.post(ProductListEvent.MORE_SCAN_PRODUCTS,products);
            }

        } else {
            // TODO EL PRODUCTO NO ESTÁ SE DEVUELVE PARA AÑADIR NUEVO PRODUCTO
            this.post(ProductSingleEvent.PRODUCT_ADD_NOT_FOUND,product);
        }

    }

    /**
     * Recuperación del producto
     * @param id
     * @return
     */
    private Product getItem(final Long id){
        Product product = SugarRecord.findById(Product.class,id);
        return product;
    }

    /**
     * Lanza hacia el presentador el evento
     * @param type
     * @param products
     */
    private void post(final int type, final List<Product> products){
        ProductListEvent event = new ProductListEvent();
        event.setType(type);
        event.setProducts(products);
        eventBus.post(event);
    }

    private void post(final int type, final Product product){
        ProductSingleEvent event = new ProductSingleEvent();
        event.setType(type);
        event.setProduct(product);
        eventBus.post(event);
    }


    private List<Product> getProductDummy() {
        List<Product> products = new ArrayList<Product>();


        Product product = new Product();
        product.setName("Chorizo");
        product.setCode("1235458");
        products.add(product);

        product = new Product();
        product.setName("Leche");
        product.setCode("9996525");
        products.add(product);


        product = new Product();
        product.setName("ColaCao");
        product.setCode("88888");
        products.add(product);

        return products;
    }

}
