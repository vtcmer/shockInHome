package com.ztt.stockinhome.products.impl;

import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.products.ProductRepository;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.stock.StockFilter;

import java.util.List;

/**
 * Created by vtcmer on 11/08/2016.
 */
public class ProductInteractorImpl implements ProductInteractor {

    private ProductRepository repository;

    public ProductInteractorImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getProducts() {
        this.repository.findProducts();
    }

    @Override
    public void getProductByCode(final Product product) {
        this.repository.getProductByCode(product);
    }

    @Override
    public void save(Product product) {

        if (product.getId() == null){
            this.repository.save(product);
        } else {
            this.repository.update(product);
        }

    }

    @Override
    public void getProduct(Long id) {
        this.repository.get(id);
    }

    @Override
    public void deleteProducts(List<Product> products) {
        this.repository.delete(products);
    }

    @Override
    public void update(Long id, Integer stock) {
        this.repository.update(id,stock);
    }

    @Override
    public void update(Long id, Integer units, boolean add) {
        this.repository.update(id,units,add);
    }

    @Override
    public void findByFilter(final StockFilter filter) {
        this.repository.findByFilter(filter);
    }

    @Override
    public void editScanProduct(Product product) {
        this.repository.editScanProduct(product);
    }

    @Override
    public void removeScanProduct(Product product) {
        this.repository.removeScanProduct(product);
    }

    @Override
    public void addScanProduct(Product product) {
        this.repository.addScanProduct(product);
    }


}
