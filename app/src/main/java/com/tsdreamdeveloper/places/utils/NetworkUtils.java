package com.tsdreamdeveloper.places.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Timur Seisembayev
 * @since 28.09.2019
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String AUTH_BASE_URL = "https://www.alarstudios.com/test/auth.cgi?";
    private static final String DATA_BASE_URL = "https://www.alarstudios.com/test/data.cgi?";

    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";

    private static final String CODE_PARAM = "code";
    private static final String PAGE_PARAM = "p";

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        try {
            int code = urlConnection.getResponseCode();
            if (code !=  200) {
                throw new IOException("Invalid response from server: " + code);
            }

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();

            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL getAuthUrl(String username, String password) {
        Uri authQueryUri = Uri.parse(AUTH_BASE_URL).buildUpon()
                .appendQueryParameter(USERNAME_PARAM, username)
                .appendQueryParameter(PASSWORD_PARAM, password)
                .build();

        try {
            return new URL(authQueryUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "getAuthUrl: ", e);
            return null;
        }
    }

    public static URL getDataUrl(String code, String page) {
        Uri dataQueryUri = Uri.parse(DATA_BASE_URL).buildUpon()
                .appendQueryParameter(CODE_PARAM, code)
                .appendQueryParameter(PAGE_PARAM, page)
                .build();

        try {
            return new URL(dataQueryUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "getDataUrl: ", e);
            return null;
        }
    }
}
