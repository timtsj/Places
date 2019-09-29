package com.tsdreamdeveloper.places.utils;

import com.tsdreamdeveloper.places.mvp.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 28.09.2019
 */
public class JsonUtils {

    private static final String STATUS = "status";
    private static final String OK = "ok";
    private static final String CODE = "code";

    public static String getAuthDataFromJson(String authJsonStr) throws JSONException {
        JSONObject authJson = new JSONObject(authJsonStr);

        if (authJson.has(STATUS)) {
            String status = authJson.getString(STATUS);
            if (!status.equals(OK)) {
                return null;
            }
        }

        return authJson.getString(CODE);
    }

    public static List<Data> getDataFromJson(String dataJsonStr) throws JSONException {
        JSONObject authJson = new JSONObject(dataJsonStr);

        if (authJson.has(STATUS)) {
            String status = authJson.getString(STATUS);
            if (!status.equals(OK)) {
                return null;
            }
        }
        JSONArray jsonArray = authJson.getJSONArray("data");
        List<Data> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Data data = new Data();
            JSONObject dataObject = jsonArray.getJSONObject(i);
            String id = dataObject.getString("id");
            String name = dataObject.getString("name");
            String country = dataObject.getString("country");
            long lat = dataObject.getLong("lat");
            long lon = dataObject.getLong("lon");

            data.setId(id);
            data.setName(name);
            data.setCountry(country);
            data.setLat(lat);
            data.setLon(lon);

            list.add(data);
        }

        return list;
    }
}
