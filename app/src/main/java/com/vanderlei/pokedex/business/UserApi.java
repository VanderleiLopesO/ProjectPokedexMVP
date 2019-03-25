package com.vanderlei.pokedex.business;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vanderlei.pokedex.utils.Constants.BASE_URL;

public class UserApi {

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
