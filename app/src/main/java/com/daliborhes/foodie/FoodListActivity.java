package com.daliborhes.foodie;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.daliborhes.foodie.Adapter.RecyclerFoodAdapter;
import com.daliborhes.foodie.Adapter.RecyclerMenuAdapter;
import com.daliborhes.foodie.Model.Category;
import com.daliborhes.foodie.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    RecyclerView recyclerFood;
    List<Food> foodList = new ArrayList<>();
    RecyclerFoodAdapter adapter;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerFood = findViewById(R.id.recycler_food);
        recyclerFood.setHasFixedSize(true);
        recyclerFood.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerFoodAdapter(FoodListActivity.this, foodList);
        recyclerFood.setAdapter(adapter);

        // Get intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
            Log.d("CategoryID foodactivity", " " + categoryId);
            loadListFood(categoryId);
        }
    }

    private void loadListFood(String categoryIdFood) {

        databaseReference = database.getReference("Food");
        Query query = database.getReference("Food").orderByChild("menuId").equalTo(categoryIdFood);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Food food = snapshot.getValue(Food.class);
                        foodList.add(food);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FoodListActivity.this, "Something went wrong" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
