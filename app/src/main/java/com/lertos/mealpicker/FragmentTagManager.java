package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lertos.mealpicker.adapters.AdapterTagList;
import com.lertos.mealpicker.model.DataManager;
import com.lertos.mealpicker.model.EnumListType;

import java.util.ArrayList;

public class FragmentTagManager extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private AdapterTagList adapterTagList;
    private EditText etNewTag;
    private ImageButton ibBtnAdd;
    private ArrayList<String> currentTagList;
    private EnumListType listType;

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
        etNewTag = view.findViewById(R.id.etNewTag);
        ibBtnAdd = view.findViewById(R.id.ibBtnAdd);

        currentTagList = DataManager.getInstance().getTags().getTagsTimeToMake();

        addTabLayoutListener();
        setListToRecyclerView();
        addNewTagButtonListener();

        return view;
    }

    private void addTabLayoutListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        currentTagList = DataManager.getInstance().getTags().getTagsTimeToMake();
                        listType = EnumListType.TIME_TO_MAKE;
                        break;
                    case 1:
                        currentTagList = DataManager.getInstance().getTags().getTagsDifficulty();
                        listType = EnumListType.DIFFICULTY;
                        break;
                    case 2:
                        currentTagList = DataManager.getInstance().getTags().getTagsMealType();
                        listType = EnumListType.MEAL_TYPE;
                        break;
                    case 3:
                        currentTagList = DataManager.getInstance().getTags().getTagsOther();
                        listType = EnumListType.OTHER;
                        break;
                }
                setListToRecyclerView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setListToRecyclerView() {
        adapterTagList = new AdapterTagList();
        adapterTagList.setDataList(currentTagList, listType);

        recyclerView.setAdapter(adapterTagList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void addNewTagButtonListener() {
        ibBtnAdd.setOnClickListener(btn -> {
            String newTag = etNewTag.getEditableText().toString();

            if (newTag.isEmpty()) {
                Toast.makeText(view.getContext(), "Your new tag cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!DataManager.getInstance().getTags().addTagToList(currentTagList, newTag)) {
                Toast.makeText(view.getContext(), "That tag already exists in the current list", Toast.LENGTH_SHORT).show();
                return;
            }

            //Update the adapter list so the new tag will show up
            adapterTagList.notifyDataSetChanged();

            //Make sure the new tag field is empty after adding a tag
            etNewTag.getEditableText().clear();

            //Depending on the setting, close the virtual keyboard after creating a tag
            if (DataManager.getInstance().getSettings().useCloseKeyboardAfterTagCreation())
                Helper.hideKeyboard(getActivity());
        });
    }
}