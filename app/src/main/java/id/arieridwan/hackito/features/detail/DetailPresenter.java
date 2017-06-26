package id.arieridwan.hackito.features.detail;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import id.arieridwan.hackito.base.BasePresenter;
import id.arieridwan.hackito.models.ItemComments;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 24/06/2017.
 */

public class DetailPresenter extends BasePresenter<DetailView> {

    public DetailPresenter(DetailView view) {
        attachView(view);
    }

    public void loadComments(List<Integer> list) {
        view.startLoading();
        Observable getItems = Observable.from(list)
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
                .observeOn(AndroidSchedulers.mainThread());

        addSubscribe(getItems, new Subscriber<ArrayList<ItemComments>>() {
            @Override
            public void onCompleted() {
                view.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError: ", e.getMessage().toString());
            }

            @Override
            public void onNext(ArrayList<ItemComments> items) {
                view.getItemComments(items);
            }
        });
    }
}
