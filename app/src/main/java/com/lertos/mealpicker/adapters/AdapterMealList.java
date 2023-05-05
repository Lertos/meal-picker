package com.lertos.mealpicker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.FragmentExistingMeal;
import com.lertos.mealpicker.Helper;
import com.lertos.mealpicker.R;
import com.lertos.mealpicker.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class AdapterMealList extends RecyclerView.Adapter<AdapterMealList.ViewHolder> {

    private FragmentActivity fragmentActivity;
    private List<Meal> mealList = new ArrayList<>();

    public void setFragmentActivity(FragmentActivity activity) {
        fragmentActivity = activity;
    }

    public void setDataList(List<Meal> list) {
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
        holder.etMealName.setText(mealList.get(position).getTitle());

        holder.ibBtnDelete.setOnClickListener(view -> {
            mealList.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
        });

        holder.ibBtnChoose.setOnClickListener(view -> {
            //TODO: Switch to the existing meal page
            Helper.replaceFragment(fragmentActivity, new FragmentExistingMeal());
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton ibBtnDelete;
        private final ImageButton ibBtnChoose;
        private final TextView etMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ibBtnDelete = itemView.findViewById(R.id.ibBtnDelete);
            ibBtnChoose = itemView.findViewById(R.id.ibBtnChoose);
            etMealName = itemView.findViewById(R.id.etMealName);
        }
    }

}
