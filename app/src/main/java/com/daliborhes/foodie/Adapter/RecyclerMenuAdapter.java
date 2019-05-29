package com.daliborhes.foodie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daliborhes.foodie.FoodListActivity;
import com.daliborhes.foodie.Model.Category;
import com.daliborhes.foodie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Dalibor J. Stanković on 21.05.2019.
 */

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private String categoryId;

    public RecyclerMenuAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categoryList = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.get().load(categoryList.get(position).getImage()).into(holder.categoryImage);
        holder.categoryName.setText(categoryList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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
//            DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("Category");
//            categoryRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Category category = dataSnapshot.getValue(Category.class);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

            Intent intent = new Intent(context, FoodListActivity.class);
            categoryId = categoryList.get(getAdapterPosition()).getCategoryId();
            intent.putExtra("CategoryId", categoryId);
            context.startActivity(intent);
        }
    }
}
