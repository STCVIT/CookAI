package com.stcvit.cookai.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.stcvit.cookai.JsonPlaceholderApi;
import com.stcvit.cookai.R;
import com.stcvit.cookai.model.IngredientsPost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView_ingredients;
    ChipGroup chipGroup_ingredients;
    ArrayList<String> ingredients_list;
    ImageView image_emptyvessel,image_filledvessel;
    Button button_findrecipes;
    TextView textView_pantryStatus,privacy_policy;
    Chip chip;
    private Retrofit retrofit;
    String URL_req = "https://cookai.azurewebsites.net/api/";
    String ing_temp = "";
    ArrayList<IngredientsPost> ingredientsPosts_list = new ArrayList<>();
    int count = 0;
    NestedScrollView nestedScrollView_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit=new Retrofit.Builder()
                .baseUrl(URL_req)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ingredients_list = new ArrayList<>();
        nestedScrollView_main=findViewById(R.id.main_activity_layout);
        privacy_policy= findViewById(R.id.tv_privacy);
        autoCompleteTextView_ingredients=findViewById(R.id.textfield_input);
        chipGroup_ingredients=findViewById(R.id.ingredient_chips);
        textView_pantryStatus=findViewById(R.id.text_pantrystatus);
        button_findrecipes=findViewById(R.id.button_findrecipes);
        image_emptyvessel=findViewById(R.id.imageview_emptyvessel);
        image_emptyvessel.setVisibility(View.VISIBLE);
        image_filledvessel=findViewById(R.id.imageview_filledvessel);
        try{
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("Clear").equals("Clear"));{
                ingredients_list.clear();
                chipGroup_ingredients.removeAllViews();
                count = 0;
                ingredientsPosts_list.clear();
                check_vesselImage();
            }
        }
        catch (Exception e){

        }
        String[] ingredients=getResources().getStringArray(R.array.ingredients_array);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ingredients);
        autoCompleteTextView_ingredients.setAdapter(adapter);

        button_findrecipes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(sizeCheck(ingredients_list)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View dialogview = getLayoutInflater().inflate(R.layout.loading_layout,null);
                    builder.setView(dialogview);
                    AlertDialog dialog
                            = builder.create();
                    dialog.show();
                    /*ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                    progressDialog.setMessage("Fetching your Recipes");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();*/
                    ingredients_list.clear();
                    String request = "";
                    for (int i = 0; i < chipGroup_ingredients.getChildCount(); i++) {
                        String text_from_chip = ((Chip) chipGroup_ingredients.getChildAt(i)).getText().toString();
                        ingredients_list.add(text_from_chip);
                        ing_temp = ing_temp + ", " + text_from_chip;
                        request = request + "," + text_from_chip;
                    }
                    request = request.substring(1, request.length());
                    Log.i("REQUEST", request);
                    JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
                    IngredientsPost ingredientsPost = new IngredientsPost(request);
                    try {

                        String json = new Gson().toJson(ingredientsPost);
                        Log.i("JSON req", json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Call<List<IngredientsPost>> call = jsonPlaceholderApi.postIngredients(ingredientsPost);
                    call.enqueue(new Callback<List<IngredientsPost>>() {
                        @Override
                        public void onResponse(Call<List<IngredientsPost>> call, Response<List<IngredientsPost>> response) {
                            if (!response.isSuccessful()) {
                                Log.i("FAILED in ", response.toString());
                                dialog.cancel();
                            } else {
                                Log.i("Success :: ", response.body().get(0).getTitle());
                                int size = response.body().size();
                                ingredientsPosts_list.clear();
                                for (int i = 0; i < size; i++) {
                                    String title = response.body().get(i).getTitle();
                                    String ingredients = response.body().get(i).getIngredients();
                                    int time = response.body().get(i).getTime();
                                    String imgurl = response.body().get(i).getImgurl();
                                    String cuisine = response.body().get(i).getCuisine();
                                    String instruction = response.body().get(i).getInstructions();
                                    ingredientsPosts_list.add(new IngredientsPost(title, ingredients, time, imgurl, cuisine, instruction));
                                }
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Recipes", (Serializable) ingredientsPosts_list);
                                Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                dialog.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<IngredientsPost>> call, Throwable t) {
                            Log.i("FAILURE out ", t.toString());
                            dialog.cancel();
                        }
                    });
                }
            }
        });


        autoCompleteTextView_ingredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(inputCheck(ingredients_list,parent.getItemAtPosition(position).toString())) {
                    ingredients_list.add(parent.getItemAtPosition(position).toString());
                    Log.i("DATA",ingredients_list.toString());
                    check_vesselImage();
                    LayoutInflater layoutInflater_chips=LayoutInflater.from(MainActivity.this);
                    chip=(Chip)layoutInflater_chips.inflate(R.layout.chip_ingredient,null,false);
                    chip.setClickable(false);
                    chip.setTag(count);
                    count++;
                    chip.setText(parent.getItemAtPosition(position).toString());
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int i  = (int) v.getTag();
                            Chip chipt=v.findViewWithTag(i);
                            ingredients_list.remove(chipt.getText().toString());
                            Log.i("ID",ingredients_list.toString());
                            check_vesselImage();
                            chipGroup_ingredients.removeView(v);
                        }
                    });
                    chipGroup_ingredients.addView(chip);
                    autoCompleteTextView_ingredients.setText("");

                }

            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri
                        .parse("https://prajeshkumarg.github.io/Cook_AI_PrivacyPolicy/")));
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean inputCheck(ArrayList<String> ingredients_list, String str) {
        int size = ingredients_list.size();
        if(!ingredients_list.contains(str)){
            return true;
        }
        else {
            Snackbar.make(nestedScrollView_main,"You have already added that ingredient", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getApplicationContext()
                            .getColor(R.color.textColor)).setTextColor(getColor(R.color.white)).show();
            return false;}
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean sizeCheck(ArrayList<String> stringArrayList){
        if(stringArrayList.size()>=4){
            return true;
        }
        else {
            Snackbar.make(nestedScrollView_main,"Minimum 4 ingredients required", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getApplicationContext()
                            .getColor(R.color.textColor)).setTextColor(getColor(R.color.white)).show();
            return false;}
    }

    private void check_vesselImage(){
        if(ingredients_list.isEmpty()){
            image_emptyvessel.setVisibility(View.VISIBLE);
            textView_pantryStatus.setVisibility(View.VISIBLE);
            image_filledvessel.setVisibility(View.INVISIBLE);
            button_findrecipes.setVisibility(View.INVISIBLE);
        }
        else{
            image_emptyvessel.setVisibility(View.INVISIBLE);
            textView_pantryStatus.setVisibility(View.INVISIBLE);
            image_filledvessel.setVisibility(View.VISIBLE);
            button_findrecipes.setVisibility(View.VISIBLE);
        }
    }
}