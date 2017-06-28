package id.arieridwan.hackito.features.main;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import id.arieridwan.hackito.models.ItemStories;
import id.arieridwan.hackito.network.ApiServices;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 27/06/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private ApiServices apiServices;
    private MainContract.View mView;

    @Inject
    public MainPresenter(ApiServices apiServices, MainContract.View mView) {
        this.apiServices = apiServices;
        this.mView = mView;
    }

    @Override
    public void loadTopStories() {
        mView.startLoading();
        apiServices
                .topStories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", e.getMessage().toString());
                        mView.stopLoadingError(e);
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        mView.getTopStories(integers);
                    }
                });
    }

    @Override
    public void loadItem(List<Integer> list) {
        mView.startLoading();
        Observable.from(list)
                .flatMap(integers -> Observable.from(new Integer[]{integers}))
                .flatMap(id -> apiServices.item(id))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ItemStories>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", e.getMessage());
                        mView.stopLoadingError(e);
                    }

                    @Override
                    public void onNext(List<ItemStories> itemStories) {
                        mView.getItem(itemStories);
                    }
                });
    }
}
