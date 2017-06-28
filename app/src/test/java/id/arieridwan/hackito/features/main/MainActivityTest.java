package id.arieridwan.hackito.features.main;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
public class MainActivityTest {

    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void testComponentViewNotNull() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_stories);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipeLayout);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) activity.findViewById(R.id.main_content);
        assertNotNull("toolbar is null",toolbar);
        assertNotNull("recyclerView is null",recyclerView);
        assertNotNull("swipeRefreshLayout is null",swipeRefreshLayout);
        assertNotNull("coordinatorLayout is null",coordinatorLayout);
    }

}