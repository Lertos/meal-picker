package com.lertos.mealpicker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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
        //Set the tag text using the list passed to the adapter
        holder.etTagName.getEditableText().clear();
        holder.etTagName.getEditableText().append(tagList.get(position));

        //TODO: Check if this is needed when you have the tab switching working
        //Disable any open fields; useful when switching tabs
        if (holder.etTagName.isEnabled())
            disableTextField(holder);

        holder.ibBtnEdit.setOnClickListener(view -> {
            enableTextField(holder);
        });

        holder.ibBtnConfirm.setOnClickListener(view -> {
            disableTextField(holder);
        });

        holder.ibBtnCancel.setOnClickListener(view -> {
            disableTextField(holder);
        });
    }

    private void enableTextField(ViewHolder holder) {
        holder.etTagName.setEnabled(true);

        holder.ibBtnEdit.setVisibility(View.GONE);
        holder.ibBtnConfirm.setVisibility(View.VISIBLE);
        holder.ibBtnCancel.setVisibility(View.VISIBLE);
    }

    private void disableTextField(ViewHolder holder) {
        holder.etTagName.setEnabled(false);

        holder.ibBtnEdit.setVisibility(View.VISIBLE);
        holder.ibBtnConfirm.setVisibility(View.GONE);
        holder.ibBtnCancel.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton ibBtnEdit;
        private final ImageButton ibBtnConfirm;
        private final ImageButton ibBtnCancel;
        private final ImageButton ibBtnDelete;
        private final EditText etTagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ibBtnEdit = itemView.findViewById(R.id.ibBtnEdit);
            ibBtnConfirm = itemView.findViewById(R.id.ibBtnConfirm);
            ibBtnCancel = itemView.findViewById(R.id.ibBtnCancel);
            ibBtnDelete = itemView.findViewById(R.id.ibBtnDelete);
            etTagName = itemView.findViewById(R.id.etTagName);
        }
    }

}
