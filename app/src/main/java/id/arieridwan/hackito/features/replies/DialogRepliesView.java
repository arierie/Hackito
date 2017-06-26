package id.arieridwan.hackito.features.replies;

import java.util.List;
import id.arieridwan.hackito.models.ItemComments;

/**
 * Created by arieridwan on 26/06/2017.
 */

public interface DialogRepliesView {
    void getItemReplies(List<ItemComments> list);
    void startLoading();
    void stopLoading();
}
