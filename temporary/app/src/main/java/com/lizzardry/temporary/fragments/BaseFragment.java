package com.lizzardry.temporary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizzardry.temporary.Adapters.BaseRecyclerViewHolderAdapter;
import com.lizzardry.temporary.R;

import java.util.Arrays;
import java.util.List;

public class BaseFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> titles;

    public BaseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = Arrays.asList(getResources().getStringArray(R.array.base_fragment_titles));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        initUi(view);

        return view;
    }

    private void initUi(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_base_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BaseRecyclerViewHolderAdapter(titles));
    }
}


