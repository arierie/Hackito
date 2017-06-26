package id.arieridwan.hackito.features.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.adapter.DetailAdapter;
import id.arieridwan.hackito.base.MvpActivity;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.models.ItemStories;
import id.arieridwan.hackito.utils.Constants;

public class DetailActivity extends MvpActivity<DetailPresenter> implements DetailView,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view)
    RelativeLayout view;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_label)
    TextView tvLabel;

    private List<Integer> mComment = new ArrayList<>();
    private List<ItemComments> mItemComments = new ArrayList<>();

    private DetailAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected DetailPresenter onCreatePresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdapter = new DetailAdapter(mItemComments);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.post(() -> onRefresh());
        rvComment.setAdapter(mAdapter);
        rvComment.setHasFixedSize(true);
        rvComment.setLayoutManager(mLayoutManager);
    }

    private void getIntentExtra() {
        Intent i = getIntent();
        if (i.hasExtra(Constants.COMMENT)) {
            try {
                addListFromSerializable(i);
            } catch (Exception e) {
                Log.e("onCreate: ", e.getMessage().toString());
            }
        }
    }

    private void addListFromSerializable(Intent i) {
        ItemStories mItem = (ItemStories) i.getSerializableExtra(Constants.COMMENT);
        setData(mItem);
        if (mItem.getKids() != null) {
            mComment.clear();
            mComment.addAll(mItem.getKids());
            presenter.loadComments(mComment);
            tvLabel.setText("Comments");
        } else {
            tvLabel.setText("No comment yet");
            stopLoading();
        }
    }

    @Override
    public void getItemComments(List<ItemComments> list) {
        mItemComments.clear();
        mItemComments.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void setData(ItemStories item) {
        tvPoint.setText(item.getScore() + " points");
        tvTitle.setText(item.getTitle());
        tvSubtitle.setText("By " + item.getAuthor());
        fab.setOnClickListener(view -> {
            new FinestWebView.Builder(activity).show(item.getUrl());
        });
    }

    @Override
    public void startLoading() {
        swipeLayout.setRefreshing(true);
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopLoading() {
        swipeLayout.setRefreshing(false);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        view.setVisibility(View.INVISIBLE);
        getIntentExtra();
    }
}
