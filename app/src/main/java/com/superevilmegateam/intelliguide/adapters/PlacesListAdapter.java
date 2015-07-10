package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.Place;

import java.util.List;

/**
 * Created by Lukasz on 2015-04-08.
 */
public class PlacesListAdapter extends MyAdapter<Place> {

    public PlacesListAdapter(Activity cntx, List<Place> itemList) {
        super(cntx,itemList);
    }

    class ViewHolder {
        TextView text;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = cntx.getLayoutInflater().inflate(R.layout.place_list_item,parent,false);
            holder.text = (TextView)convertView.findViewById(R.id.place_item_name);
            holder.image = (ImageView)convertView.findViewById(R.id.place_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(itemList.get(position).getPreviewPhoto(),holder.image);
        holder.text.setText(itemList.get(position).getName());



        return convertView;
    }
}
