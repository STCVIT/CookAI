package com.stcvit.cookai.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stcvit.cookai.R;
import com.stcvit.cookai.model.IngredientsPost;

public class CompleteRecipe extends AppCompatActivity {

    TextView recipe_title,recipe_cuisine,recipe_instruction,recipe_ingredient,recipe_preptime;
    ImageView recipe_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_recipe);

        Bundle bundle = getIntent().getExtras();
        String json_item;
        json_item = bundle.getString("Full_Recipe");
        IngredientsPost ingredientsPost = (IngredientsPost)new Gson().fromJson(json_item,IngredientsPost.class);

        recipe_title = findViewById(R.id.comp_recipe_title);
        recipe_cuisine = findViewById(R.id.comp_recipe_cuisine);
        recipe_ingredient = findViewById(R.id.comp_recipe_ingredients);
        recipe_image = findViewById(R.id.comp_recipe_image);
        recipe_instruction = findViewById(R.id.comp_recipe_steps);
        recipe_preptime = findViewById(R.id.comp_recipe_prep);

        loader(ingredientsPost);
    }
    private void loader(IngredientsPost item){
        recipe_title.setText(item.getTitle());
        recipe_cuisine.setText(item.getCuisine());
        String str=item.getIngredients();
        String[] str_parts = str.split(",");
        Log.i("DATA",str_parts.toString());
        String ingredeints="";
        int last= str_parts.length;
        for(String i : str_parts) {
            if (i.equals(str_parts[last-1])) {
                ingredeints = ingredeints + i;
            } else {
                ingredeints = ingredeints + i + "\n";
            }
        }
        Log.i("ING",ingredeints);
        recipe_ingredient.setText(ingredeints);

        String str_ins=item.getInstructions();
        String[] str_parts_ins = str_ins.split("\n");
        Log.i("Data",str_parts_ins[0]);
        String instructions="";
        int last_ins= str_parts_ins.length;
        int ins_count = 1;
        for (int i = 0 ; i<last_ins; i++){
            String s = i+1+") " + str_parts_ins[i]+"\n";
            str_parts_ins[i]=s;
        }
        for(String i : str_parts_ins) {
            if (i.equals(str_parts_ins[last_ins-1])) {
                instructions = instructions + i;
            } else {
                instructions = instructions + i + "\n";
            }
        }
        Log.i("INS",instructions);
        recipe_instruction.setText(instructions);
        String time = item.getTime().toString() + " mins";
        recipe_preptime.setText(time);
        try{
            Glide.with(getApplicationContext()).load(item.getImgurl())
                    .into(recipe_image);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}