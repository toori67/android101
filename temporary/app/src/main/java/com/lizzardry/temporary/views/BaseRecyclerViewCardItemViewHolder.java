package com.lizzardry.temporary.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lizzardry.temporary.R;

public class BaseRecyclerViewCardItemViewHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    public BaseRecyclerViewCardItemViewHolder(View itemView) {
        super(itemView);
        this.titleView = (TextView) itemView.findViewById(R.id.view_base_fragment_card_item_title);
    }

    public void setTitle(String title) {
        this.titleView.setText(title);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
