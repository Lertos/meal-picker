package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.adapters.AdapterTagList;
import com.lertos.mealpicker.model.DataManager;

public class FragmentTagManager extends Fragment {

    public FragmentTagManager() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag_manager, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvTagsTimeToMake);

        AdapterTagList adapterTagList = new AdapterTagList();
        adapterTagList.setDataList(DataManager.getInstance().getTags().getTagsTimeToMake());

        recyclerView.setAdapter(adapterTagList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}