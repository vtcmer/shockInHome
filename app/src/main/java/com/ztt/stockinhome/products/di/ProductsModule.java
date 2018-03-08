package com.ztt.stockinhome.products.di;

import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.products.ProductInteractor;
import com.ztt.stockinhome.products.ProductRepository;
import com.ztt.stockinhome.products.impl.ProductInteractorImpl;
import com.ztt.stockinhome.products.impl.ProductRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 11/08/2016.
 */
@Module
public class ProductsModule {


    @Provides
    @Singleton
    ProductRepository  providesProductRepository(final EventBus eventBus){
        return new ProductRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    ProductInteractor providesProductInteractor(final ProductRepository repository){
        return new ProductInteractorImpl(repository);
    }

}
