package com.lertos.mealpicker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.R;
import com.lertos.mealpicker.model.DataManager;

import java.util.ArrayList;
import java.util.List;

public class AdapterTagList extends RecyclerView.Adapter<AdapterTagList.ViewHolder> {

    private int currentActivePos = -1;
    private List<String> tagList = new ArrayList<>();
    private String previousTagValue;

    public void setDataList(List<String> list) {
        tagList = list;

        //Gets rid of the initial empty record
        if (tagList.get(0).isEmpty())
            tagList.remove(0);

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
        holder.etTagName.getEditableText().append(tagList.get(holder.getAdapterPosition()));

        //Disable all text fields other than the currently desired row
        if (currentActivePos != holder.getAdapterPosition())
            disableTextField(holder);
        else
            enableTextField(holder);

        holder.ibBtnEdit.setOnClickListener(view -> {
            //Add the previous value in the map so we can restore it if needed
            previousTagValue = holder.etTagName.getEditableText().toString();
            currentActivePos = holder.getAdapterPosition();
            notifyDataSetChanged();
        });

        holder.ibBtnConfirm.setOnClickListener(view -> {
            //Set the tag to the new text
            tagList.set(holder.getAdapterPosition(), holder.etTagName.getEditableText().toString());
            disableTextField(holder);
            saveFile();
        });

        holder.ibBtnCancel.setOnClickListener(view -> {
            //Set the tag back to the previous text
            holder.etTagName.getEditableText().clear();
            holder.etTagName.getEditableText().append(previousTagValue);
            disableTextField(holder);
        });

        holder.ibBtnDelete.setOnClickListener(view -> {
            tagList.remove(holder.getAdapterPosition());
            saveFile();

            currentActivePos = -1;
            notifyDataSetChanged();
        });
    }

    private void saveFile() {
        DataManager.getInstance().getFile().getTagFile().saveToFile(DataManager.getInstance().getTags());
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
