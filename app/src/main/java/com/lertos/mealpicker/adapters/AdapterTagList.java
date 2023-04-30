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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterTagList extends RecyclerView.Adapter<AdapterTagList.ViewHolder> {

    private List<String> tagList = new ArrayList<>();
    private Map<Integer, String> previousValueMap;

    public void setDataList(List<String> list) {
        tagList = list;
        previousValueMap = new HashMap<>();
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

        holder.ibBtnEdit.setOnClickListener(view -> {
            enableTextField(holder);

            //Add the previous value in the map so we can restore it if needed
            previousValueMap.put(position, holder.etTagName.getEditableText().toString());
        });

        holder.ibBtnConfirm.setOnClickListener(view -> {
            disableTextField(holder);

            //Set the tag to the new text and remove it from the previous value list
            tagList.set(position, holder.etTagName.getEditableText().toString());
            previousValueMap.remove(position);
        });

        holder.ibBtnCancel.setOnClickListener(view -> {
            disableTextField(holder);

            //Set the tag back to the previous text and remove it from the previous value list
            holder.etTagName.getEditableText().clear();
            holder.etTagName.getEditableText().append(previousValueMap.get(position));
        });

        //TODO: When a deletion occurs, check if it's in the list; if so - delete it and shift other positions above it
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
