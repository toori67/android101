package com.lizzardry.temporary.Adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lizzardry.temporary.R;
import com.lizzardry.temporary.fragments.shared.SharedElement;
import com.lizzardry.temporary.views.BaseRecyclerViewCardItemViewHolder;
import com.lizzardry.temporary.views.SharedElementViewHolder;

import java.util.List;

public class SharedElementRecyclerViewHolderAdapter extends RecyclerView.Adapter<SharedElementViewHolder>{
    public interface OnSharedElementViewHolderClickListener {
        void onClick(SharedElement sharedElement, SharedElementViewHolder viewHolder);
    }


    private List<SharedElement> items;
    private OnSharedElementViewHolderClickListener onSharedElementViewHolderClickListener;

    public SharedElementRecyclerViewHolderAdapter(List<SharedElement> items, OnSharedElementViewHolderClickListener listener) {
        this.items = items;
        this.onSharedElementViewHolderClickListener = listener;
    }

    @Override
    public SharedElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SharedElementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_shared_element_fragment_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final SharedElementViewHolder holder, int position) {
        final SharedElement sharedElement = items.get(position);
        holder.setData(sharedElement);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSharedElementViewHolderClickListener != null) {
                    onSharedElementViewHolderClickListener.onClick(sharedElement, holder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
