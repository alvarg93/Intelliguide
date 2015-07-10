package com.superevilmegateam.intelliguide.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.GalleryObject;
import com.superevilmegateam.intelliguide.views.SquareImageView;

import java.util.List;


/**
 * Created by Lukasz on 2015-02-24.
 */
public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.ViewHolder> {

    private List<GalleryObject> itemList;
    private Activity cntx;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SquareImageView cellImage;
        public ViewHolder(View v) {
            super(v);
            this.cellImage = (SquareImageView) v.findViewById(R.id.photo_grid_cell_image);
        }
    }

    public PhotoGridAdapter(Activity cntx, List<GalleryObject> itemList) {
        this.cntx = cntx;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_grid_cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = itemList.get(position).getUrl();
        ImageLoader.getInstance().displayImage(item, holder.cellImage);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}