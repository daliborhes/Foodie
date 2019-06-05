package com.daliborhes.foodie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daliborhes.foodie.UI.FoodDetailActivity;
import com.daliborhes.foodie.Model.Food;
import com.daliborhes.foodie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dalibor J. Stanković on 22.05.2019.
 */

public class RecyclerFoodAdapter extends RecyclerView.Adapter<RecyclerFoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> foodList;

    public RecyclerFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, final int position) {

        Picasso.get().load(foodList.get(position).getImage()).into(holder.foodImage);
        holder.foodName.setText(foodList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.menu_name)
        TextView foodName;
        @BindView(R.id.menu_image)
        ImageView foodImage;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String foodName = foodList.get(getAdapterPosition()).getName();
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("FoodName", foodName);
            Log.d("FoodID", "onClick: " + foodName);
            context.startActivity(intent);
            Toast.makeText(context, "You clicked: " + foodName, Toast.LENGTH_SHORT).show();
        }
    }
}
