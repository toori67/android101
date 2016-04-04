package com.lizzardry.temporary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizzardry.temporary.R;
import com.lizzardry.temporary.dao.SqlWrapper;
import com.lizzardry.temporary.dao.beans.Contact;

public class DbListFragment extends Fragment {

    private SqlWrapper sqlWrapper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlWrapper = new SqlWrapper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_list, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        view.findViewById(R.id.fragment_db_list_init_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlWrapper.drop();
                sqlWrapper.bulkInsert(getResources().openRawResource(R.raw.contact));
            }
        });
        view.findViewById(R.id.fragment_db_list_insert_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = new Contact();
                contact.setName("name");
                contact.setCompany("company");
                contact.setDepartment("dept");
                contact.setPosition("position");
                contact.setPhone("010-21323123");
                contact.setAddress("addre addre adder");
                sqlWrapper.insert(contact);
            }
        });
    }
}
