package com.tsdreamdeveloper.places.mvp.view;

import com.tsdreamdeveloper.places.base.BaseView;
import com.tsdreamdeveloper.places.mvp.model.Data;

import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 29.09.2019
 */
public interface MainView extends BaseView {
    void removeFooter();

    void addList(List<Data> list);
}
