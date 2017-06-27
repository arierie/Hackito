package id.arieridwan.hackito.features.comments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.adapter.CommentsAdapter;
import id.arieridwan.hackito.base.MvpActivity;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.models.ItemStories;
import id.arieridwan.hackito.utils.Constants;

public class CommentsActivity extends MvpActivity<CommentsPresenter> implements CommentsView,
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
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    private List<Integer> mComment = new ArrayList<>();
    private List<ItemComments> mItemComments = new ArrayList<>();

    private CommentsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected CommentsPresenter onCreatePresenter() {
        return new CommentsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLayout.setTitle(getString(R.string.app_name));
        toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mAdapter = new CommentsAdapter(mItemComments);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.post(() -> onRefresh());
        rvComment.setAdapter(mAdapter);
        rvComment.setHasFixedSize(true);
        rvComment.setLayoutManager(mLayoutManager);
    }

    @Override
    public void getIntentExtra() {
        Intent i = getIntent();
        if (i.hasExtra(Constants.COMMENT)) {
            try {
                addListFromSerializable(i);
            } catch (Exception e) {
                Log.e("onCreate: ", e.getMessage().toString());
            }
        }
    }

    @Override
    public void addListFromSerializable(Intent i) {
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
            if (!TextUtils.isEmpty(item.getUrl())) {
                new FinestWebView.Builder(activity).show(item.getUrl());
            } else {
                Toast.makeText(this, "Url is empty", Toast.LENGTH_SHORT).show();
            }
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
