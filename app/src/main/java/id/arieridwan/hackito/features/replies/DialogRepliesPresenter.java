package id.arieridwan.hackito.features.replies;

import java.util.List;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.network.ApiServices;

/**
 * Created by arieridwan on 26/06/2017.
 */

public interface DialogRepliesPresenter {
    void loadReplies(List<Integer> list);
}
