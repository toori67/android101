package com.lizzardry.temporary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lizzardry.temporary.Adapters.SQLiteNotiableRecyclerViewHolderAdapter;
import com.lizzardry.temporary.R;
import com.lizzardry.temporary.dao.SqlWrapper;

public class DbListFragment extends Fragment {
    private SqlWrapper sqlWrapper;
    private Button initDataBtn;
    private EditText searchText;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlWrapper = SqlWrapper.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_list, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        initDataBtn = (Button) view.findViewById(R.id.fragment_db_list_init_btn);
        searchText = (EditText) view.findViewById(R.id.fragment_db_list_search);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_db_list_recycler_view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initEvent();
    }


    private void initEvent() {
        final SQLiteNotiableRecyclerViewHolderAdapter adapter = new SQLiteNotiableRecyclerViewHolderAdapter(sqlWrapper);
        initDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlWrapper.drop();
                sqlWrapper.bulkInsert(getResources().openRawResource(R.raw.contact));
            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.notifyQueryUpdate(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }
}
