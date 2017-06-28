package id.arieridwan.hackito.features.main;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import id.arieridwan.hackito.BuildConfig;
import id.arieridwan.hackito.models.ItemStories;
import id.arieridwan.hackito.network.ApiServices;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by arieridwan on 27/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainPresenterTest {

    @Mock
    private ApiServices apiServices;

    @Mock
    private MainContract.View view;

    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Override RxAndroid schedulers
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
        presenter = new MainPresenter(apiServices, view);
    }

    @Test
    public void testDisplayDataTopStories() throws Exception {
        when(apiServices.topStories()).thenReturn(Observable.just(Collections.emptyList()));
        presenter.loadTopStories();
        // verify user interaction
        verify(view, times(1)).startLoading();
        verify(view, times(1)).getTopStories(anyListOf(Integer.TYPE));
        verify(view, never()).stopLoadingError(new Exception("some error"));
        verify(view, never()).stopLoading();
    }

    @Test
    public void testDisplayErrorTopStories() throws Exception {
        Exception exception = new Exception("Oops, something happened");
        when(apiServices.topStories()).thenReturn(Observable.error(exception));
        presenter.loadTopStories();
        // verify user interaction
        verify(view, times(1)).startLoading();
        verify(view, times(1)).stopLoadingError(any(Throwable.class));
        verify(view, never()).getTopStories(anyListOf(Integer.TYPE));
        verify(view, never()).stopLoading();
    }

    @Test
    public void testDisplayDataItem() throws Exception {
        ItemStories itemStories = Mockito.mock(ItemStories.class);
        List<Integer> integers = new ArrayList<>();
        List<ItemStories> itemStoriesList = new ArrayList<>();
        when(apiServices.item(anyInt())).thenReturn(Observable.just(itemStories));
        presenter.loadItem(integers);
        // verify user interaction
        verify(view, times(1)).startLoading();
        verify(view, times(1)).getItem(itemStoriesList);
        verify(view, times(1)).stopLoading();
        verify(view, never()).stopLoadingError(new Exception("some error"));
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

}
