package id.arieridwan.hackito.features.replies;

import android.util.Log;

import java.util.List;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.network.ApiServices;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by arieridwan on 26/06/2017.
 */

class DialogRepliesPresenterImpl implements DialogRepliesPresenter {

    private DialogRepliesView dialogRepliesView;
    private ApiServices apiServices;

    public DialogRepliesPresenterImpl(DialogRepliesView dialogRepliesView, ApiServices services) {
        this.dialogRepliesView = dialogRepliesView;
        this.apiServices = services;
    }

    @Override
    public void loadReplies(List<Integer> list) {
        Observable observable = Observable.from(list)
                .doOnNext(items -> Log.e("loadReplies: ", list.size()+""))
                .flatMap(id -> apiServices.comment(id))
                .doOnNext(itemComments -> Log.e("loadReplies: ", itemComments.getId()+""))
                .filter(items -> {
                    if(!items.isDeleted()){
                        return true;
                    }else {
                        return false;
                    }
                })
                .toList();

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ItemComments>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(List<ItemComments> list) {
                        dialogRepliesView.getItemReplies(list);
                    }
                });
    }
}
