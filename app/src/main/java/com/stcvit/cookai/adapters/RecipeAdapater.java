package com.stcvit.cookai.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stcvit.cookai.R;
import com.stcvit.cookai.activities.CompleteRecipe;
import com.stcvit.cookai.activities.MainActivity;
import com.stcvit.cookai.model.IngredientsPost;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapater extends RecyclerView.Adapter<RecipeAdapater.RecipeClass>  {

    List<IngredientsPost> ingredientsPostList= new ArrayList<>();
    Context context;

    public RecipeAdapater(List<IngredientsPost> ingredientsPostList, Context context) {
        this.ingredientsPostList = ingredientsPostList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeClass holder, int position) {
        holder.recipe_title.setText(ingredientsPostList.get(position).getTitle());
        holder.recipe_cuisine.setText(ingredientsPostList.get(position).getCuisine());
        holder.recipe_content.setText(ingredientsPostList.get(position).getInstructions());

        try{
            Glide.with(context).load(ingredientsPostList.get(position).getImgurl())
                    .into(holder.recipe_image);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ingredientsPostList.get(position).getTitle();
                String ingredients = ingredientsPostList.get(position).getIngredients();
                int time =ingredientsPostList.get(position).getTime();
                String imgurl = ingredientsPostList.get(position).getImgurl();
                String cuisine = ingredientsPostList.get(position).getCuisine();
                String instruction = ingredientsPostList.get(position).getInstructions();
                IngredientsPost ingredientsPost_item = new IngredientsPost(title,ingredients,time,imgurl,cuisine,instruction);
                try{
                    String item_recipe = new Gson().toJson(ingredientsPost_item);
                    Intent i = new Intent( context, CompleteRecipe.class);
                    i.putExtra("Full_Recipe",item_recipe);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                }
                catch (Exception e){
                    //Toast.makeText(context,"Please Retry",Toast.LENGTH_LONG);
                    Intent i = new Intent( context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientsPostList.size();
    }

    public class RecipeClass extends RecyclerView.ViewHolder{

        TextView recipe_title,recipe_cuisine,recipe_content;
        ImageView recipe_image;
        public RecipeClass(@NonNull View itemView) {
            super(itemView);
            recipe_title=(TextView) itemView.findViewById(R.id.recipe_title);
            recipe_cuisine= itemView.findViewById(R.id.recipe_cuisine);
            recipe_content= itemView.findViewById(R.id.recipe_content);
            recipe_image = itemView.findViewById(R.id.recipes_image);
        }
    }
}
