package id.arieridwan.hackito.features.comments;

import android.content.Intent;

import java.util.List;
import id.arieridwan.hackito.models.ItemComments;

/**
 * Created by arieridwan on 24/06/2017.
 */

public interface CommentsView {
    void getItemComments(List<ItemComments> list);
    void getIntentExtra();
    void addListFromSerializable(Intent i);
    void startLoading();
    void stopLoading();
}
