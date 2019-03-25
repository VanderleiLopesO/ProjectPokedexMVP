package com.vanderlei.pokedex.ui;

import android.util.Log;

import com.vanderlei.pokedex.business.ApiProvider;
import com.vanderlei.pokedex.business.UserApi;
import com.vanderlei.pokedex.models.Pokemon;
import com.vanderlei.pokedex.models.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainPresenter implements MainContract.Presenter {

    private Retrofit retrofit = UserApi.getRetrofitInstance();
    private boolean isLoad;
    private int items;
    private static int QUANTITY_MAX_ITEM = 20;
    private MainContract.View mView;

    MainPresenter(MainContract.View view) {
        mView = view;
    }

    void init() {
        isLoad = true;
        items = 0;
        startRequisition(items);
    }

    @Override
    public void startRequisition(int offset) {
        ApiProvider service = retrofit.create(ApiProvider.class);
        Call<Response> call = service.getPokemon(QUANTITY_MAX_ITEM, offset);

        mView.showLoading();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                isLoad = true;
                if (response.isSuccessful()) {
                    Response apiResponse = response.body();
                    ArrayList<Pokemon> list = apiResponse.getResults();

                    mView.hideLoading();
                    mView.setList(list);
                } else {
                    mView.hideLoading();
                    Log.e("Error", "" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                isLoad = true;
                mView.hideLoading();
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

    @Override
    public void downloadMoreItems(int visibleItemCount, int pastItemCount, int totalItemCount) {
        mView.showLoading();

        if (isLoad) {
            mView.hideLoading();

            if ((visibleItemCount + pastItemCount) >= totalItemCount) {
                items += QUANTITY_MAX_ITEM;
                isLoad = false;
                startRequisition(items);
            }
        }
    }

}
