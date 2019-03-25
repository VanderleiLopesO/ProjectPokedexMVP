package com.vanderlei.pokedex.ui;

import com.vanderlei.pokedex.models.Pokemon;

import java.util.ArrayList;

public interface MainContract {

    interface Presenter {
        void startRequisition(int offset);

        void downloadMoreItems(int visibleItemCount, int pastItemCount, int totalItemCount);
    }

    interface View {
        void setList(ArrayList<Pokemon> list);
        void showLoading();
        void hideLoading();
    }

}
