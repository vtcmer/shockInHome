package com.ztt.stockinhome.commons.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.ztt.stockinhome.R;
import com.ztt.stockinhome.StockInHomeApp;

import java.util.Arrays;

/**
 * Created by vtcmer on 27/08/2016.
 */
public abstract class ApplicationActivity extends AppCompatActivity {


    /**Objeto aplicación*/
    private StockInHomeApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.app = (StockInHomeApp) getApplication();

    }


    /**
     * Recuperación del objeto aplicación
     * @return
     */
    protected StockInHomeApp getApplicationStockInHome(){
        return this.app;
    }


}
