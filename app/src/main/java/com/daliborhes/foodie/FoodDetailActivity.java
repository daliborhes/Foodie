package com.daliborhes.foodie;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.daliborhes.foodie.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    TextView foodPrice;
    TextView foodDesc;
    ElegantNumberButton foodBtn;
    ImageView foodImage;

    DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("Food");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Init views
        foodImage = findViewById(R.id.img_food_detail);
        foodDesc = findViewById(R.id.food_description);
        foodPrice = findViewById(R.id.food_price);
        fab = findViewById(R.id.btn_cart);
        foodBtn = findViewById(R.id.number_btn);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        // Get intent here
        if (getIntent() != null) {
            String foodIdPass = getIntent().getStringExtra("FoodId");
            Log.d("FoodID foodactivity", " " + foodIdPass);
            loadFoodDetail(foodIdPass);
        }
    }

    private void loadFoodDetail(String foodId) {
        foodRef.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);

                // Set views
                Picasso.get().load(food.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(food.getName());
                foodPrice.setText(food.getPrice());
                foodDesc.setText(food.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
