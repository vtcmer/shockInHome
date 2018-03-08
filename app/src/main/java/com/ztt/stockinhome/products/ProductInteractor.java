package com.ztt.stockinhome.products;

import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.stock.StockFilter;

import java.util.List;

/**
 * Created by vtcmer on 11/08/2016.
 */
public interface ProductInteractor {


    /**
     * Recuperación de la lista de productos
     */
    void getProducts();

    /**
     * Recuperación de producto por código
     * @param product
     */
    void getProductByCode(final Product product);

    /**
     * Guardardo de un producto
     * @param product
     */
    void save(final Product product);

    /**
     * Recuperación de un producto
     * @param id
     */
    void getProduct(final Long id);

    /**
     * Eliminación de un listado de productos
     * @param products
     */
    void deleteProducts(final List<Product> products);

    /**
     * Actualización del stock de productos
     * @param id
     * @param stock
     */
    void update(final Long id, final Integer stock);

    /**
     * Actualiza el producto
     * @param id identificador del producto
     * @param units Número de unidades
     * @param add Indica si se suma o resta
     */
    void update(final Long id, final Integer units, boolean add);

    /**
     * Filtrado de productos
     * @param filter
     */
    void findByFilter(final StockFilter filter);

    /**
     * Editar producto por escaner
     * @param product
     */
    void editScanProduct(final Product product);

    /**
     * Eliminación de un producto por escaner
     * @param product
     */
    void removeScanProduct(final Product product);

    /**
     * Añadir producto por escaner
     * @param product
     */
    void addScanProduct(final Product product);
}
