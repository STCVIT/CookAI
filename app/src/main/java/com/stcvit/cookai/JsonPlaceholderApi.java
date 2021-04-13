package com.stcvit.cookai;

import com.stcvit.cookai.model.IngredientsPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceholderApi {

    @POST("cookAi?code=IyD3KFOx6OTHgDI5lzih3a5mT9PMzhHbPVJRsa0UFh2242PFNWUPSA==")
    Call<List<IngredientsPost>> postIngredients(
            @Body IngredientsPost ingredientsPost
    );

}
