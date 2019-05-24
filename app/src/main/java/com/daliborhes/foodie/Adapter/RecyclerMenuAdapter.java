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

import com.daliborhes.foodie.EnteringActivity;
import com.daliborhes.foodie.FoodListActivity;
import com.daliborhes.foodie.HomeActivity;
import com.daliborhes.foodie.LoginActivity;
import com.daliborhes.foodie.Model.Category;
import com.daliborhes.foodie.R;
import com.daliborhes.foodie.SignupActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dalibor J. Stanković on 21.05.2019.
 */

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public RecyclerMenuAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.get().load(categories.get(position).getImage()).into(holder.categoryImage);
        holder.categoryName.setText(categories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.menu_name)
        TextView categoryName;
        @BindView(R.id.menu_image)
        ImageView categoryImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, FoodListActivity.class);
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//            String categoryId = databaseReference.push().getKey();
//            Log.d("CategoryId from adapter", " " + categoryId);
//            intent.putExtra("CategoryId", categoryId);
            context.startActivity(intent);
            Toast.makeText(context, "You Clicked: " + categories.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
