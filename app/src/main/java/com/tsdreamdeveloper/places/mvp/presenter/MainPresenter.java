package com.tsdreamdeveloper.places.mvp.presenter;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.tsdreamdeveloper.places.mvp.model.Data;
import com.tsdreamdeveloper.places.mvp.view.MainView;
import com.tsdreamdeveloper.places.utils.JsonUtils;
import com.tsdreamdeveloper.places.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 29.09.2019
 */
public class MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    public static final String CODE_EXTRA_KEY = "code";

    private MainView view;
    private String code;
    private int page = 1;
    private boolean isFirst = true;

    public MainPresenter(MainView view) {
        this.view = view;
        view.onLoadingStart();
    }

    public void setCode(Intent intent) {
        code = intent.getStringExtra(CODE_EXTRA_KEY);
    }

    public void getDataList() {
        AsyncTask<Void, Void, List<Data>> mTask = new AsyncTask<Void, Void, List<Data>>() {

            @Override
            protected List<Data> doInBackground(Void... params) {
                if (isCancelled()) {
                    return null;
                }

                URL authRequestUrl = NetworkUtils.getDataUrl(code, String.valueOf(page));

                /* Use the URL to retrieve the JSON */
                try {
                    String response = NetworkUtils.getResponseFromHttpUrl(authRequestUrl);
                    List<Data> list = JsonUtils.getDataFromJson(response);

                    return list;
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "auth: ", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Data> result) {
                super.onPostExecute(result);
                result(result);
            }
        };

        mTask.execute();
    }

    private void result(List<Data> list) {
        view.onLoadingFinish();
        view.removeFooter();

        if (list == null) {
            view.showMessage("No data");
        } else {
            view.addList(list);
            page++;
        }
        if (isFirst) {
            isFirst = false;
            getDataList();
        }
    }
}
