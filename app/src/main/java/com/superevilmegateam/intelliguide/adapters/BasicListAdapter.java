package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;

import java.util.List;

/**
 * Created by Lukasz on 2015-04-08.
 */
public class BasicListAdapter extends MyAdapter<String> {

    public BasicListAdapter(Activity cntx, List<String> itemList) {
        super(cntx,itemList);
    }

    class ViewHolder {
        TextView text;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = cntx.getLayoutInflater().inflate(R.layout.list_item,viewGroup,false);
            holder.text = (TextView)view.findViewById(R.id.list_item_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(itemList.get(i));

        return view;
    }
}
