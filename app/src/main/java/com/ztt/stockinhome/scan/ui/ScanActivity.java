package com.ztt.stockinhome.scan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentZttIntegrator;
import com.ztt.stockinhome.R;
import com.ztt.stockinhome.StockInHomeApp;
import com.ztt.stockinhome.commons.ui.ApplicationActivity;
import com.ztt.stockinhome.products.entities.Product;
import com.ztt.stockinhome.products.ui.EnumScanProductAction;
import com.ztt.stockinhome.scan.di.ScanComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vtcmer on 24/08/2016.
 */
public abstract   class ScanActivity extends ApplicationActivity {

    public static final String ADD = "add";
    public static final String REMOVE ="remove";
    public static final String EDIT = "edit";

    /** Listado de formatos disponibles para los escaneos*/
    private List<String> availableProductFormats = null;

    /**Creación del intent que lanza el escaner*/
    protected IntentIntegrator scanIntegrator = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        availableProductFormats = Arrays.asList(getResources().getStringArray(R.array.availableProductFormats));
        //this.setupInjection();

    }

    /**
     * Inyección de dependecias
     */
    protected void setupInjection() {

        ScanComponent scanComponent = this.getApplicationStockInHome().getScanComponent(this);
        this.scanIntegrator = scanComponent.getIntentIntegrator();
    }


    /**
     * Método que se ejecuta cuando el escaneo fue correcto
     * @param product
     */
    protected abstract void onScanSuccess(final Product product, final EnumScanProductAction action);

    /**
     * Método que se ejecuta cuando el escaneo fue con error
     */
    protected abstract void onScanError(final String msg);

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (intent != null){

            EnumScanProductAction enumScanProductAction = EnumScanProductAction.find(requestCode);

            if (enumScanProductAction != null){
                //Se obtiene el resultado del proceso de scaneo y se parsea
                IntentResult scanningResult = IntentZttIntegrator.parseActivityResult(requestCode, resultCode, intent);
                if (scanningResult != null) {
                    // -- recuperación del código escaneado
                    String code = scanningResult.getContents();
                    String format = scanningResult.getFormatName();
                    final Product product = new Product(code, format);
                    boolean isFormatValid = this.isFormatValid(format);
                    if (isFormatValid) {
                        this.onScanSuccess(product, enumScanProductAction);
                    }else {
                        this.onScanError("Format is not valid: "+format);
                    }

                }
            } else{
                this.onScanError("Scan Action not available: "+requestCode);
            }



        }
    }

    /**
     * Método para validar si el formato que se escane es correcto
     * @param format
     * @return
     */
    private boolean isFormatValid(final String format){
        boolean isOk = true;

        if (availableProductFormats.isEmpty() || !availableProductFormats.contains(format)){
            isOk = false;
        }
        return isOk;
    }

    /**
     * Comprobar si la acción indicada es correcta
     * @param action
     * @return
     */
    protected boolean isValidAction (final Integer action){

        boolean isValid = false;

        EnumScanProductAction enumScanProductAction = EnumScanProductAction.find(action);

        if (enumScanProductAction != null){
            isValid = true;
        }

        return isValid;

    }

    /**
     * Lanza el proceso de escaner
     */
    protected void scan(final EnumScanProductAction action){

        //Se procede con el proceso de scaneo
        ((IntentZttIntegrator)scanIntegrator).setScanProductAction(action.getValue());
        scanIntegrator.initiateScan();


    }


}
