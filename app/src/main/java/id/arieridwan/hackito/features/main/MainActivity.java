package id.arieridwan.hackito.features.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.adapter.StoriesAdapter;
import id.arieridwan.hackito.base.MvpActivity;
import id.arieridwan.hackito.models.Item;

public class MainActivity extends MvpActivity<MainPresenter>
        implements MainView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_stories)
    RecyclerView rvStories;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private List<Item> mList = new ArrayList<>();
    private StoriesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mAdapter = new StoriesAdapter(mList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvStories.setHasFixedSize(true);
        rvStories.setAdapter(mAdapter);
        rvStories.setLayoutManager(mLayoutManager);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.post(() -> {
            swipeLayout.setRefreshing(true);
            presenter.loadTopStories();
        }
        );
    }

    @Override
    public void getDataSuccess(List<Item> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
        rvStories.setVisibility(View.GONE);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void stopLoading() {
        swipeLayout.setRefreshing(false);
        rvStories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        presenter.loadTopStories();
    }
}
