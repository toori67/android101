package com.lizzardry.temporary.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lizzardry.temporary.R;
import com.lizzardry.temporary.dao.SqlWrapper;
import com.lizzardry.temporary.dao.beans.Contact;
import com.lizzardry.temporary.views.DbListRecyclerViewCardItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteNotiableRecyclerViewHolderAdapter extends RecyclerView.Adapter<DbListRecyclerViewCardItemViewHolder>{
    private List<Contact> items;
    private SqlWrapper sqlWrapper;
    private String criterion;

    public SQLiteNotiableRecyclerViewHolderAdapter(SqlWrapper sqlWrapper) {
        items = new ArrayList<>();
        this.sqlWrapper = sqlWrapper;
        this.criterion = "";
    }
    @Override
    public DbListRecyclerViewCardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DbListRecyclerViewCardItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_db_list_fragment_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DbListRecyclerViewCardItemViewHolder holder, int position) {
        holder.setContact(items.get(position), criterion);
    }

    public void notifyQueryUpdate(String criterion) {
        this.criterion = criterion;
        items = sqlWrapper.selectFromDocuments(criterion);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
