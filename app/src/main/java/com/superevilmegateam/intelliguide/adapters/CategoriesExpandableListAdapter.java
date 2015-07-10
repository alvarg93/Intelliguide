package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukasz on 2015-04-21.
 */
public class CategoriesExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity cntx;
    private ArrayList<Category> itemList;

    private Category selectedCategory;

    public CategoriesExpandableListAdapter(Activity cntx, ArrayList<Category> itemList) {
        this.cntx = cntx;
        this.itemList = itemList;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return itemList.size();
    }

    @Override
    public Object getGroup(int i) {
        return itemList;
    }

    @Override
    public Object getChild(int i, int i2) {
        return itemList.get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int position, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = cntx.getLayoutInflater().inflate(R.layout.category_list_item, parent, false);
            holder = prepareViewHolder(position, convertView, parent, cntx);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(selectedCategory!=null) {
            holder.text.setText(selectedCategory.getName());
            holder.select.setVisibility(View.GONE);
            holder.image.setImageBitmap(selectedCategory.getIconBmp());
        } else {
            holder.text.setText("Kategoria");
            holder.select.setVisibility(View.GONE);
            holder.image.setVisibility(View.GONE);
        }
        if(convertView==null) Log.e("Dupa","NULL");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int position, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = cntx.getLayoutInflater().inflate(R.layout.category_list_item,parent,false);
            holder = prepareViewHolder(position,convertView, parent, cntx);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(itemList.get(position).getName());
        holder.select.setVisibility(View.GONE);
        holder.image.setImageBitmap(itemList.get(position).getIconBmp());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }


    class ViewHolder {
        ImageView image;
        TextView text;
        Switch select;
    }
    private ViewHolder prepareViewHolder(int position, View convertView, ViewGroup parent, Activity cntx){
        ViewHolder holder;

        holder = new ViewHolder();
        holder.text = (TextView)convertView.findViewById(R.id.category_item_name);
        holder.select = (Switch)convertView.findViewById(R.id.category_item_switch);
        holder.image = (ImageView) convertView.findViewById(R.id.category_item_icon);

        Log.e("DUPA", "DUPA" + position);
        return holder;
    }



}
