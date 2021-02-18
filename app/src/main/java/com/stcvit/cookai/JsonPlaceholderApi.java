package com.stcvit.cookai;

import android.graphics.LinearGradient;

import com.stcvit.cookai.model.IngredientsPost;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonPlaceholderApi {

    @POST("cookAi?code=IyD3KFOx6OTHgDI5lzih3a5mT9PMzhHbPVJRsa0UFh2242PFNWUPSA==")
    Call<List<IngredientsPost>> postIngredients(
            @Body IngredientsPost ingredientsPost
    );
}
