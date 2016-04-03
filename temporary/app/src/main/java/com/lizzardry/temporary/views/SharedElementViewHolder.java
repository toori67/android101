package com.lizzardry.temporary.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizzardry.ril.RetroImageLoader;
import com.lizzardry.temporary.R;
import com.lizzardry.temporary.fragments.shared.SharedElement;

public class SharedElementViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView titleView;
    public SharedElementViewHolder(View itemView) {
        super(itemView);
        this.icon = (ImageView) itemView.findViewById(R.id.view_shared_element_fragment_card_item_icon);
        this.titleView = (TextView) itemView.findViewById(R.id.view_shared_element_fragment_card_item_title);
    }

    public void setData(SharedElement element) {
        RetroImageLoader.getInstance().displayImage(element.getImageUrl(), icon);
        titleView.setText(element.getTitle());
    }

    public View getItemView() {
        return itemView;
    }
}
