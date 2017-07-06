package id.arieridwan.hackito.features.replies;

import java.util.List;
import id.arieridwan.hackito.models.ItemComments;

/**
 * Created by arieridwan on 06/07/2017.
 */

public interface  DialogRepliesContract {
    interface View {
        void getItemReplies(List<ItemComments> list);
        void startLoading();
        void startLoadingError(Throwable e);
        void stopLoading();
    }
    interface Presenter {
        void loadReplies(List<Integer> list);
    }
}
