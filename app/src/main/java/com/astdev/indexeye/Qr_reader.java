package com.astdev.indexeye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Qr_reader {


    String qrResult;

    public static void qr_reader(Activity activity){
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scanner SVP");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            String contents = intentResult.getContents();
            if (contents != null){
                qrResult = intentResult.getContents();
            }
        }
        else super.onActivityResult(requestCode,resultCode,data);

    }*/
}
