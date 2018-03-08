package com.google.zxing.integration.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import java.util.Collection;

/**
 * Created by vtcmer on 07/01/2017.
 */

public class IntentZttIntegrator extends IntentIntegrator {

    private int scanProductAction = REQUEST_CODE;

    public IntentZttIntegrator(Activity activity) {
        super(activity);
    }


    @Override
    public AlertDialog initiateScan(Collection<String> desiredBarcodeFormats) {
        Intent intentScan = new Intent(BS_PACKAGE + ".SCAN");
        intentScan.addCategory(Intent.CATEGORY_DEFAULT);

        // check which types of codes to scan for
        if (desiredBarcodeFormats != null) {
            // setItems the desired barcode types
            StringBuilder joinedByComma = new StringBuilder();
            for (String format : desiredBarcodeFormats) {
                if (joinedByComma.length() > 0) {
                    joinedByComma.append(',');
                }
                joinedByComma.append(format);
            }
            intentScan.putExtra("SCAN_FORMATS", joinedByComma.toString());
        }

        String targetAppPackage = findTargetAppPackage(intentScan);
        if (targetAppPackage == null) {
            return showDownloadDialog();
        }
        intentScan.setPackage(targetAppPackage);
        intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        attachMoreExtras(intentScan);
        startActivityForResult(intentScan, this.scanProductAction);
        return null;
    }



    public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            String contents = intent.getStringExtra("SCAN_RESULT");
            String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
            byte[] rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");
            int intentOrientation = intent.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
            Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
            String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
            return new IntentResult(contents,
                    formatName,
                    rawBytes,
                    orientation,
                    errorCorrectionLevel);
        }
        return new IntentResult();

    }

    /**
     * Setea la acción
     * @param scanProductAction
     */
    public void setScanProductAction(int scanProductAction){
        this.scanProductAction = scanProductAction;
    }

}
