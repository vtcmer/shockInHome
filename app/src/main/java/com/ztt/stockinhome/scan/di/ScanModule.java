package com.ztt.stockinhome.scan.di;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentZttIntegrator;
import com.ztt.stockinhome.scan.ui.ScanActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 27/08/2016.
 */
@Module
public class ScanModule {


    public ScanActivity activity;

    public ScanModule(ScanActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    ScanActivity providesScanActivity(){
        return this.activity;
    }

    @Provides
    @Singleton
    public IntentIntegrator providesIntentIntegrator(final ScanActivity activity){
        return  new IntentZttIntegrator(activity);
    }
}
