package id.arieridwan.hackito.features.replies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.hackito.BuildConfig;
import id.arieridwan.hackito.R;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyListOf;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DialogRepliesFragmentTest {

    private DialogRepliesFragment fragment;

    @Before
    public void setUp() throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        fragment = DialogRepliesFragment.newInstance(integers);
        startFragment(fragment);
    }

    public static void startFragment(Fragment fragment) {
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class)
                .create()
                .start()
                .resume()
                .get();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    @Test
    public void testComponentViewNotNull() throws Exception {
        RecyclerView rvReplies = (RecyclerView) fragment.getView().findViewById(R.id.rv_replies);
        RelativeLayout progressview = (RelativeLayout) fragment.getView().findViewById(R.id.progressview);
        TextView tvLabel = (TextView) fragment.getView().findViewById(R.id.tv_label);
        assertNotNull("rvReplies is null",rvReplies);
        assertNotNull("progressview is null",progressview);
        assertNotNull("tv_label is null",tvLabel);
    }

}