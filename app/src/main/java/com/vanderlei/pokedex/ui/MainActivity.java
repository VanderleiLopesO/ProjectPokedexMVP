package com.vanderlei.pokedex.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.vanderlei.pokedex.R;
import com.vanderlei.pokedex.models.Pokemon;
import com.vanderlei.pokedex.ui.adapter.MainAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.loading)
    ProgressBar loading;

    private MainAdapter mainAdapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        setUpRecyclerView();
        presenter.init();
    }

    private void setUpRecyclerView() {
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        //This is something like a endless scroll, made to download items in according to scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    presenter.downloadMoreItems(visibleItemCount, pastVisibleItems, totalItemCount);

                }
            }
        });
    }

    @Override
    public void setList(ArrayList<Pokemon> list) {
        mainAdapter.setList(list);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
