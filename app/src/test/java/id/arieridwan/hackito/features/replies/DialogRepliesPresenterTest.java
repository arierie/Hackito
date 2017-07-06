package id.arieridwan.hackito.features.replies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.hackito.BuildConfig;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.network.ApiServices;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DialogRepliesPresenterTest {

    @Mock
    private DialogRepliesContract.View view;
    @Mock
    private ApiServices apiServices;

    private DialogRepliesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Override RxJava schedulers
        RxJavaHooks.setOnNewThreadScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
        presenter = new DialogRepliesPresenter(view, apiServices);
    }

    @Test
    public void tesLoadReplies() throws Exception {
        ItemComments itemComments = Mockito.mock(ItemComments.class);
        List<Integer> integers = new ArrayList<>();
        List<ItemComments> itemCommentsList = new ArrayList<>();
        when(apiServices.comment(anyInt())).thenReturn(Observable.just(itemComments));
        presenter.loadReplies(integers);
        // verify user interaction
        verify(view, times(1)).startLoading();
        verify(view, times(1)).getItemReplies(itemCommentsList);
        verify(view, times(1)).stopLoading();
        verify(view, never()).startLoadingError(new Exception("some error"));
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }
}