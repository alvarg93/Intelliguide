package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * Created by Jan Badura on 2015-06-16.
 */
public abstract class MyAdapter<T> extends BaseAdapter {

    protected Activity cntx;
    protected List<T> itemList;

    public MyAdapter(Activity cntx, List<T> itemList) {
        this.cntx = cntx;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
