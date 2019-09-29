package com.tsdreamdeveloper.places.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsdreamdeveloper.places.R;
import com.tsdreamdeveloper.places.base.BaseActivity;
import com.tsdreamdeveloper.places.mvp.model.Data;
import com.tsdreamdeveloper.places.mvp.presenter.DetailPresenter;
import com.tsdreamdeveloper.places.mvp.view.DetailView;

public class DetailActivity extends BaseActivity implements DetailView {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private DetailPresenter presenter;
    private TextView mTvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        presenter = new DetailPresenter(this);
        mTvDetail = findViewById(R.id.tv_detail);
        presenter.initData(getIntent());
        presenter.initMap(this);
    }

    @Override
    public void setData(String text) {
        mTvDetail.setText(text);
    }
}
