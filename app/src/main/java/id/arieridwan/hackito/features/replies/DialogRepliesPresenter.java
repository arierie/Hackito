package id.arieridwan.hackito.features.replies;

import android.util.Log;
import java.util.List;

import javax.inject.Inject;

import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.network.ApiServices;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 26/06/2017.
 */

public class DialogRepliesPresenter implements DialogRepliesContract.Presenter {

    private DialogRepliesContract.View mView;
    private ApiServices apiServices;

    @Inject
    public DialogRepliesPresenter(DialogRepliesContract.View view, ApiServices apiServices) {
        this.mView = view;
        this.apiServices = apiServices;
    }

    @Override
    public void loadReplies(List<Integer> list) {
        mView.startLoading();
        Observable observable = Observable.from(list)
                .flatMap(id -> apiServices.comment(id))
                .filter(items -> {
                    if (!items.isDeleted()) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .toList();

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ItemComments>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(List<ItemComments> list) {
                        Log.e("onNext: ", list.size() + "");
                        mView.getItemReplies(list);
                    }
                });
    }
}
