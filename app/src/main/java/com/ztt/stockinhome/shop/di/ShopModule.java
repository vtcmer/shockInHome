package com.ztt.stockinhome.shop.di;

import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.shop.ShopInteractor;
import com.ztt.stockinhome.shop.ShopRepository;
import com.ztt.stockinhome.shop.impl.ShopInteractorImpl;
import com.ztt.stockinhome.shop.impl.ShopRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 15/10/2016.
 */

@Module
public class ShopModule {


    @Provides
    @Singleton
    ShopInteractor providesShopInteractor(final ShopRepository repository){
        return new ShopInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ShopRepository providesShopRepository(final EventBus eventBus){
        return new ShopRepositoryImpl(eventBus);
    }

}
