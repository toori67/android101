package com.lizzardry.temporary.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lizzardry.temporary.R;
import com.lizzardry.temporary.views.BaseRecyclerViewCardItemViewHolder;

import java.util.List;

public class BaseRecyclerViewHolderAdapter extends RecyclerView.Adapter<BaseRecyclerViewCardItemViewHolder>{
    private List<String> items;

    public BaseRecyclerViewHolderAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public BaseRecyclerViewCardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewCardItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_base_fragment_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewCardItemViewHolder holder, int position) {
        holder.setTitle(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
