package com.lizzardry.temporary.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lizzardry.temporary.R;
import com.lizzardry.temporary.dao.beans.Contact;

import java.util.Collections;
import java.util.List;

public class DbListRecyclerViewCardItemViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView companyTextView;
    private TextView matchResultTextView;
    public DbListRecyclerViewCardItemViewHolder(View itemView) {
        super(itemView);
        this.nameTextView = (TextView) itemView.findViewById(R.id.view_db_list_fragment_card_item_name);
        this.companyTextView = (TextView) itemView.findViewById(R.id.view_db_list_fragment_card_item_company);
        this.matchResultTextView = (TextView) itemView.findViewById(R.id.view_db_list_fragment_card_item_match_result);
    }

    public void setContact(Contact contact, String query) {
        this.nameTextView.setText(contact.getName());
        this.companyTextView.setText(contact.getCompany() + " / " + contact.getPosition());
        this.matchResultTextView.setText(contact.getMatchedString(query));
    }
}
