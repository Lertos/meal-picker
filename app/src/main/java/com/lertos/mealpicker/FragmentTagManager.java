package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lertos.mealpicker.adapters.AdapterTagList;
import com.lertos.mealpicker.model.DataManager;

import java.util.List;

public class FragmentTagManager extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;

    public FragmentTagManager() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tag_manager, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.rvTagsTimeToMake);

        addTabLayoutListener();
        setListToRecyclerView(DataManager.getInstance().getTags().getTagsTimeToMake());

        return view;
    }

    private void addTabLayoutListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setListToRecyclerView(DataManager.getInstance().getTags().getTagsTimeToMake());
                        break;
                    case 1:
                        setListToRecyclerView(DataManager.getInstance().getTags().getTagsDifficulty());
                        break;
                    case 2:
                        setListToRecyclerView(DataManager.getInstance().getTags().getTagsMealType());
                        break;
                    case 3:
                        setListToRecyclerView(DataManager.getInstance().getTags().getTagsOther());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setListToRecyclerView(List<String> list) {
        AdapterTagList adapterTagList = new AdapterTagList();
        adapterTagList.setDataList(list);

        recyclerView.setAdapter(adapterTagList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}