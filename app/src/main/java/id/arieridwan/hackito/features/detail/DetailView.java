package id.arieridwan.hackito.features.detail;

import java.util.List;
import id.arieridwan.hackito.models.ItemComments;

/**
 * Created by arieridwan on 24/06/2017.
 */

public interface DetailView {
    void getItemComments(List<ItemComments> list);
    void startLoading();
    void stopLoading();
}
