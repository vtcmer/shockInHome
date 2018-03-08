package com.ztt.stockinhome.products.di;

import com.ztt.stockinhome.libs.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 11/08/2016.
 */

@Singleton
@Component(modules={ProductsModule.class, LibsModule.class})
public interface ProductsComponent {
}
