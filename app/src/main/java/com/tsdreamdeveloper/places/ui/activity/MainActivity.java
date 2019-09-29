package com.tsdreamdeveloper.places.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tsdreamdeveloper.places.R;
import com.tsdreamdeveloper.places.base.BaseActivity;
import com.tsdreamdeveloper.places.mvp.model.Data;
import com.tsdreamdeveloper.places.mvp.presenter.MainPresenter;
import com.tsdreamdeveloper.places.mvp.view.MainView;
import com.tsdreamdeveloper.places.ui.adapter.PlaceAdapter;

import java.util.List;

import static com.tsdreamdeveloper.places.mvp.presenter.DetailPresenter.ITEM_EXTRA_KEY;

public class MainActivity extends BaseActivity implements MainView, PlaceAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter presenter;
    private RecyclerView rvPlace;
    private PlaceAdapter adapter;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        presenter.setCode(getIntent());
        presenter.getDataList();
        initRecyclerView();
    }

    @Override
    public void removeFooter() {
        adapter.removeLoadingFooter();
        loading = true;
    }

    @Override
    public void addList(List<Data> list) {
        adapter.setItems(list);
    }

    @Override
    public void onItemClick(Data item) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(ITEM_EXTRA_KEY, item);
        startActivity(intent);
    }

    /**
     * Implement RecyclerView and Adapter
     */
    private void initRecyclerView() {
        adapter = new PlaceAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        rvPlace = findViewById(R.id.rv_place);
        rvPlace.setLayoutManager(mLayoutManager);
        rvPlace.setAdapter(adapter);
        pagination();
    }

    private void pagination() {
        rvPlace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            adapter.addLoadingFooter();
                            presenter.getDataList();
                        }
                    }
                }
            }
        });
    }
}
