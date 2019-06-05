package com.daliborhes.foodie.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.daliborhes.foodie.Model.Food;
import com.daliborhes.foodie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.btn_cart)
    FloatingActionButton fab;
    @BindView(R.id.food_price)
    TextView foodPrice;
    @BindView(R.id.food_description)
    TextView foodDesc;
    @BindView(R.id.number_btn)
    ElegantNumberButton foodBtn;
    @BindView(R.id.img_food_detail)
    ImageView foodImage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        ButterKnife.bind(this);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        // Get intent here (FoodlistID)
        if (getIntent() != null) {
            String foodId = getIntent().getStringExtra("FoodName");
            Log.d("FoodName foodDetail ", " " + foodId);
            loadFoodDetail(foodId);
        }
    }

    private void loadFoodDetail(String foodId) {

        databaseReference = database.getReference("Food");
        Query query = databaseReference.orderByChild("name").equalTo(foodId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Food food = snapshot.getValue(Food.class);

                    // Set views
                    Picasso.get().load(food.getImage()).into(foodImage);
                    collapsingToolbarLayout.setTitle(food.getName());
                    foodPrice.setText(food.getPrice() + " RSD");
                    foodDesc.setText(food.getDescription());
                    Log.d("Name", "onDataChange: " + food.getName() + "Price: " + food.getPrice());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
