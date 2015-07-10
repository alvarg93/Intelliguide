package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.Review;

import java.util.List;

/**
 * Created by Lukasz on 2015-04-08.
 */
public class ReviewsListAdapter extends MyAdapter<Review> {

    public ReviewsListAdapter(Activity cntx, List<Review> itemList) {
        super(cntx,itemList);
    }

    class ViewHolder {
        TextView text;
        RatingBar ratingBar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = cntx.getLayoutInflater().inflate(R.layout.review_list_item,parent,false);
            holder.text = (TextView)convertView.findViewById(R.id.review_item_desc);
            holder.ratingBar = (RatingBar)convertView.findViewById(R.id.review_item_stars);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(itemList.get(position).getContent());
        holder.ratingBar.setRating(itemList.get(position).getStars());
        holder.ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        return convertView;
    }
}
