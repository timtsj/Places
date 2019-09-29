package com.tsdreamdeveloper.places.base;

/**
 * @author Timur Seisembayev
 * @since 29.09.2019
 */
public interface BaseView {

    void showMessage(String message);

    void nextStep();

    void onLoadingFinish();

    void onLoadingStart();

}
