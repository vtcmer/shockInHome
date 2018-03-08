package com.ztt.stockinhome;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 27/08/2016.
 */
@Module
public class StockInHomeModule {

    private StockInHomeApp stockInHomeApp;

    public StockInHomeModule(StockInHomeApp stockInHomeApp) {
        this.stockInHomeApp = stockInHomeApp;
    }

    @Provides
    @Singleton
    public StockInHomeApp providesStockInHomeApplication(){
        return this.stockInHomeApp;
    }

    @Provides
    @Singleton
    Context providesApplicationContext(){
        return this.stockInHomeApp.getApplicationContext();
    }

}
