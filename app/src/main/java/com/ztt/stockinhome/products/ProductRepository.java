package com.ztt.stockinhome.products;

import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.stock.StockFilter;

import java.util.List;

/**
 * Created by vtcmer on 11/08/2016.
 */
public interface ProductRepository {

    /**
     * Recupera la lista de productos
     */
    void findProducts();

    /**
     * Recuperación de producto por código
     * @param product
     */
    void getProductByCode(final Product product);

    /**
     * Guardado del producto
     * @param product
     */
    void save(final Product product);

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
     * Actualización del producto
     * @param product
     */
    void update(final Product product);

    /**
     * Eliminación del producto
     * @param id
     */
    void delete(final Long id);

    /**
     * Eliminación del listado de productos
     * @param products
     */
    void delete(final List<Product> products);

    /**
     * Recuperación del producto
     * @param id
     */
    void get(final Long id);

    /**
     * Filtrado del producto
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
