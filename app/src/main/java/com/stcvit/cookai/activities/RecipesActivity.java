package com.stcvit.cookai.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.stcvit.cookai.R;
import com.stcvit.cookai.adapters.RecipeAdapater;
import com.stcvit.cookai.model.IngredientsPost;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    RecyclerView recipes_recyclerview;
    RecipeAdapater recipe_adapater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        Bundle extras = getIntent().getExtras();
        ArrayList<IngredientsPost> recipes_result = (ArrayList<IngredientsPost>) extras.getSerializable("Recipes");
        recipes_recyclerview=findViewById(R.id.recycler_recipes);
        recipes_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recipe_adapater=new RecipeAdapater(recipes_result,getApplicationContext());
        recipes_recyclerview.setAdapter(recipe_adapater);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(RecipesActivity.this, MainActivity.class);
        myIntent.putExtra("Clear","Clear");
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
        return;
    }
}