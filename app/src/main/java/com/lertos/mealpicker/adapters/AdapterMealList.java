package com.lertos.mealpicker.adapters;

import android.util.Log;
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

public class AdapterMealList extends RecyclerView.Adapter<AdapterMealList.ViewHolder> {

    private List<String> mealList = new ArrayList<>();

    public void setDataList(List<String> list) {
        mealList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ibBtnDelete.setOnClickListener(view -> {
            mealList.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
        });

        holder.ibBtnChoose.setOnClickListener(view -> {
            //TODO: Switch to the existing meal page
            Log.d("DEBUG", "Switched to meal page");
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton ibBtnDelete;
        private final ImageButton ibBtnChoose;
        private final EditText etMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ibBtnDelete = itemView.findViewById(R.id.ibBtnDelete);
            ibBtnChoose = itemView.findViewById(R.id.ibBtnChoose);
            etMealName = itemView.findViewById(R.id.etMealName);
        }
    }

}
