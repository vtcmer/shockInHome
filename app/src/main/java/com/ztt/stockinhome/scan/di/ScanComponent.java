package com.ztt.stockinhome.scan.di;

import com.google.zxing.integration.android.IntentIntegrator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 27/08/2016.
 */
@Singleton
@Component(modules = {ScanModule.class})
public interface ScanComponent {

    IntentIntegrator getIntentIntegrator();
}
