package com.lertos.mealpicker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTagList extends RecyclerView.Adapter<AdapterTagList.ViewHolder> {

    private List<String> tagList = new ArrayList<>();

    public void setDataList(List<String> list) {
        this.tagList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set onClick listeners
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivBtnEdit;
        private final ImageView ivBtnDelete;
        private final EditText etTagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBtnEdit = itemView.findViewById(R.id.ivBtnEdit);
            ivBtnDelete = itemView.findViewById(R.id.ivBtnDelete);
            etTagName = itemView.findViewById(R.id.etTagName);
        }
    }

}
