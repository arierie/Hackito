package id.arieridwan.hackito.features.main;

import android.util.Log;

import java.util.ArrayList;

import id.arieridwan.hackito.base.BasePresenter;
import id.arieridwan.hackito.models.Item;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 20/06/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        super.attachView(view);
    }

    void loadTopStories() {
        view.startLoading();
        Observable getItems = apiServices.topStories()
                .flatMap(longs -> Observable.from(longs))
                .flatMap(id -> apiServices.item(id))
                .limit(10)
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        addSubscribe(getItems, new Subscriber<ArrayList<Item>>() {
            @Override
            public void onCompleted() {
                view.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError: ", e.getMessage());
            }

            @Override
            public void onNext(ArrayList<Item> list) {
                view.getDataSuccess(list);
            }
        });
    }

}
