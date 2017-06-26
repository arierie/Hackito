package id.arieridwan.hackito.features.stories;

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
import id.arieridwan.hackito.models.ItemStories;

public class StoriesActivity extends MvpActivity<StoriesPresenter>
        implements StoriesView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_stories)
    RecyclerView rvStories;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private int startIndex = 0;
    private List<Integer> mTopStories = new ArrayList<>();
    private List<ItemStories> mItem = new ArrayList<>();
    private StoriesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private boolean loading = true;
    private int lastVisiblesItem, visibleItemCount, totalItemCount;

    @Override
    protected StoriesPresenter onCreatePresenter() {
        return new StoriesPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mAdapter = new StoriesAdapter(mItem);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvStories.setHasFixedSize(true);
        rvStories.setAdapter(mAdapter);
        rvStories.setLayoutManager(mLayoutManager);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.post(() -> onRefresh());
        rvStories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    lastVisiblesItem = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + lastVisiblesItem) >= totalItemCount) {
                            loading = false;
                            callData();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void callData() {
        int endIndex = startIndex + 10;
        if (mTopStories.size() > startIndex && mTopStories.size() > endIndex) {
            List<Integer> id = new ArrayList<>();
            for (int i = startIndex; i < endIndex; i++) {
                id.add(mTopStories.get(i));
            }
            presenter.loadItem(id);
        }
    }

    @Override
    public void onRefresh() {
        startLoading();
        presenter.loadTopStories();
    }

    @Override
    public void getTopStories(List<Integer> list) {
        startIndex = 0;
        mTopStories.clear();
        mItem.clear();
        mTopStories.addAll(list);
        callData();
    }

    @Override
    public void getItem(List<ItemStories> list) {
        mItem.addAll(list);
        mAdapter.notifyDataSetChanged();
        startIndex = mItem.size();
    }

    @Override
    public void startLoading() {
        swipeLayout.setRefreshing(true);
        rvStories.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopLoading() {
        swipeLayout.setRefreshing(false);
        rvStories.setVisibility(View.VISIBLE);
        loading = true;
    }
}
