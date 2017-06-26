package id.arieridwan.hackito.features.stories;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.hackito.base.BasePresenter;
import id.arieridwan.hackito.models.ItemStories;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 20/06/2017.
 */

public class StoriesPresenter extends BasePresenter<StoriesView> {

    public StoriesPresenter(StoriesView view) {
        super.attachView(view);
    }

    void loadTopStories() {
        view.startLoading();
        Observable getItems = apiServices.topStories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        addSubscribe(getItems, new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError: ", e.getMessage().toString());
            }

            @Override
            public void onNext(List<Integer> integers) {
                view.getTopStories(integers);
            }
        });
    }

    void loadItem(List<Integer> list) {
        view.startLoading();
        Observable getItems = Observable.from(list)
                .flatMap(integers -> Observable.from(new Integer[]{integers}))
                .flatMap(id -> apiServices.item(id))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        addSubscribe(getItems, new Subscriber<ArrayList<ItemStories>>() {
            @Override
            public void onCompleted() {
                view.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError: ", e.getMessage().toString());
            }

            @Override
            public void onNext(ArrayList<ItemStories> items) {
                view.getItem(items);
            }
        });
    }

}
