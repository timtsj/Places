package com.tsdreamdeveloper.places.mvp.presenter;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsdreamdeveloper.places.R;
import com.tsdreamdeveloper.places.mvp.model.Data;
import com.tsdreamdeveloper.places.mvp.view.DetailView;

/**
 * @author Timur Seisembayev
 * @since 29.09.2019
 */
public class DetailPresenter implements OnMapReadyCallback {

    private static final String TAG = DetailPresenter.class.getSimpleName();
    public static final String ITEM_EXTRA_KEY = "item";

    private DetailView view;
    private long mLat;
    private long mLon;
    private String mName;

    public DetailPresenter(DetailView view) {
        this.view = view;
        view.onLoadingStart();
    }

    public void initData(Intent intent) {
        Data item = (Data) intent.getSerializableExtra(ITEM_EXTRA_KEY);
        mLat = item.getLat();
        mLon = item.getLon();
        mName = item.getName();

        String sb = "id: " + item.getId() + "\n" +
                "name: " + item.getName() + "\n" +
                "country: " + item.getCountry() + "\n" +
                "lat: " + item.getLat() + "\n" +
                "lon: " + item.getLon();
        view.setData(sb);
    }

    public void initMap(AppCompatActivity activity) {
        SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        LatLng place = new LatLng(mLat, mLon);
        googleMap.addMarker(new MarkerOptions().position(place).title("Marker in " + mName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));

        view.onLoadingFinish();
    }
}
