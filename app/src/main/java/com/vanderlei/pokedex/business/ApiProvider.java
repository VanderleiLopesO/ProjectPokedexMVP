package com.vanderlei.pokedex.business;

import com.vanderlei.pokedex.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiProvider {

    @GET("pokemon")
    Call<Response> getPokemon(@Query("limit") int limit, @Query("offset") int offset);

}
