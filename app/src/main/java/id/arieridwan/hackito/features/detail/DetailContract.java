package id.arieridwan.hackito.features.detail;

import android.content.Intent;
import java.util.List;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.models.ItemStories;

/**
 * Created by arieridwan on 27/06/2017.
 */

public interface  DetailContract {

    interface View {
        void getItemComments(List<ItemComments> list);
        void getIntentExtra();
        void addListFromSerializable(Intent i);
        void setData(ItemStories item);
        void startLoading();
        void startLoadingError(Throwable e);
        void stopLoading();
    }

    interface Presenter {
        void loadComments(List<Integer> list);
    }
}
