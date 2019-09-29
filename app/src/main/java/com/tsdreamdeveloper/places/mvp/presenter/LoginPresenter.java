package com.tsdreamdeveloper.places.mvp.presenter;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.tsdreamdeveloper.places.mvp.view.LoginView;
import com.tsdreamdeveloper.places.utils.JsonUtils;
import com.tsdreamdeveloper.places.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * @author Timur Seisembayev
 * @since 29.09.2019
 */
public class LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void auth(final String username, final String password) {
        view.onLoadingStart();
        AsyncTask<Void, Void, String> mFetchAuthTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                if (isCancelled()) {
                    return null;
                }

                URL authRequestUrl = NetworkUtils.getAuthUrl(username, password);

                /* Use the URL to retrieve the JSON */
                try {
                    String response = NetworkUtils.getResponseFromHttpUrl(authRequestUrl);
                    String code = JsonUtils.getAuthDataFromJson(response);

                    return code;
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "auth: ", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                result(result);
            }
        };

        mFetchAuthTask.execute();
    }

    private void result(String code) {
        view.onLoadingFinish();
        if (TextUtils.isEmpty(code)) {
            view.showMessage("Login or password incorrect");
        } else {
            view.setCode(code);
            view.nextStep();
        }
    }
}
