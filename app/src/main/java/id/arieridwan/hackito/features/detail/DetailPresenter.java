package id.arieridwan.hackito.features.detail;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.network.ApiServices;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 27/06/2017.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private ApiServices apiServices;
    private DetailContract.View mView;

    @Inject
    public DetailPresenter(ApiServices apiServices, DetailContract.View mView) {
        this.apiServices = apiServices;
        this.mView = mView;
    }

    @Override
    public void loadComments(List<Integer> list) {
        mView.startLoading();
        Observable.from(list)
                .flatMap(integers -> Observable.from(new Integer[]{integers}))
                .flatMap(id -> apiServices.comment(id))
                .filter(itemComments -> {
                    if (!itemComments.isDeleted()) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ItemComments>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.startLoadingError(e);
                        Log.e("onError: ", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(List<ItemComments> list) {
                        mView.getItemComments(list);
                    }
                });

    }

}
