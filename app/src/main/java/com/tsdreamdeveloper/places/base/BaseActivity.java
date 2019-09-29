package com.tsdreamdeveloper.places.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Timur Seisembayev
 * @since 28.09.2019
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showMessage(String message) {
        new AlertDialog
                .Builder(this)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(
                        getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .create()
                .show();
    }

    @Override
    public void nextStep() {

    }

    @Override
    public void onLoadingFinish() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onLoadingStart() {
        mDialog = ProgressDialog.show(this, "", "Loading. Please wait…", true);
    }
}
