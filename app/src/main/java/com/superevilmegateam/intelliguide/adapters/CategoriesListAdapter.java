package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.Category;

import java.util.List;

/**
 * Created by Lukasz on 2015-04-08.
 */
public class CategoriesListAdapter extends MyAdapter<Category> {

    public CategoriesListAdapter(Activity cntx, List<Category> itemList) {
        super(cntx,itemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = cntx.getLayoutInflater().inflate(R.layout.category_list_item,parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.category_item_icon);
            holder.select = (Switch)convertView.findViewById(R.id.category_item_switch);
            holder.text = (TextView)convertView.findViewById(R.id.category_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(itemList.get(position).getName());
        holder.select.setTextOff("Nie");
        holder.select.setTextOn("Tak");
        holder.select.setChecked(itemList.get(position).isSelected());
        holder.image.setImageBitmap(itemList.get(position).getIconBmp());

        return convertView;
    }
    class ViewHolder {
        ImageView image;
        TextView text;
        Switch select;
    }
}
