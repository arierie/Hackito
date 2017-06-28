package id.arieridwan.hackito.features.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thefinestartist.finestwebview.FinestWebView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.App;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.adapter.CommentsAdapter;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.models.ItemStories;
import id.arieridwan.hackito.utils.Constants;

/**
 * Created by arieridwan on 27/06/2017.
 */

public class DetailActivity extends AppCompatActivity
        implements DetailContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.view)
    RelativeLayout view;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.detail_parent)
    CoordinatorLayout detailParent;

    private List<Integer> mComment = new ArrayList<>();
    private List<ItemComments> mItemComments = new ArrayList<>();

    private CommentsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Inject
    DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        DaggerDetailComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .detailModule(new DetailModule(this))
                .build().inject(this);
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
    public void getItemComments(List<ItemComments> list) {
        mItemComments.clear();
        mItemComments.addAll(list);
        mAdapter.notifyDataSetChanged();
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
    public void setData(ItemStories item) {
        tvPoint.setText(item.getScore() + " points");
        tvTitle.setText(item.getTitle());
        tvSubtitle.setText("By " + item.getAuthor());
        fab.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(item.getUrl())) {
                new FinestWebView.Builder(this).show(item.getUrl());
            } else {
                Snackbar.make(detailParent, R.string.url_empy, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addListFromSerializable(Intent i) {
        ItemStories mItem = (ItemStories) i.getSerializableExtra(Constants.COMMENT);
        setData(mItem);
        if (mItem.getKids() != null) {
            mComment.clear();
            mComment.addAll(mItem.getKids());
            mPresenter.loadComments(mComment);
            tvLabel.setText("Comments");
        } else {
            tvLabel.setText("No comment yet");
            stopLoading();
        }
    }

    @Override
    public void startLoading() {
        swipeLayout.setRefreshing(true);
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startLoadingError(Throwable e) {
        swipeLayout.setRefreshing(false);
        view.setVisibility(View.VISIBLE);
        Snackbar.make(detailParent, R.string.error_text, Snackbar.LENGTH_SHORT)
                .setAction(R.string.try_again, view1 -> onRefresh());
    }

    @Override
    public void stopLoading() {
        swipeLayout.setRefreshing(false);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        view.setVisibility(View.INVISIBLE);
        getIntentExtra();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
