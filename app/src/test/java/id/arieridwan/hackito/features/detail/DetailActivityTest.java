package id.arieridwan.hackito.features.detail;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import id.arieridwan.hackito.BuildConfig;
import id.arieridwan.hackito.R;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DetailActivityTest {
    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(DetailActivity.class).create().get();
    }

    @Test
    public void testComponentViewNotNull() throws Exception {
        TextView tvPoint = (TextView) activity.findViewById(R.id.tv_point);
        TextView tvTitle = (TextView) activity.findViewById(R.id.tv_title);
        TextView tvSubtitle = (TextView) activity.findViewById(R.id.tv_subtitle);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        TextView tvLabel = (TextView) activity.findViewById(R.id.tv_label);
        RecyclerView rvComment = (RecyclerView) activity.findViewById(R.id.rv_comment);
        RelativeLayout view = (RelativeLayout) activity.findViewById(R.id.view);
        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipeLayout);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        CoordinatorLayout detailParent = (CoordinatorLayout) activity.findViewById(R.id.detail_parent);
        assertNotNull("tvPoint is null",tvPoint);
        assertNotNull("tvTitle is null",tvTitle);
        assertNotNull("tvSubtitle is null",tvSubtitle);
        assertNotNull("toolbar is null",toolbar);
        assertNotNull("toolbarLayout is null",toolbarLayout);
        assertNotNull("tvLabel is null",tvLabel);
        assertNotNull("rvComment is null",rvComment);
        assertNotNull("view is null",view);
        assertNotNull("swipeLayout is null",swipeLayout);
        assertNotNull("fab is null",fab);
        assertNotNull("detailParent is null",detailParent);
    }

}