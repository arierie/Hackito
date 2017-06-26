package id.arieridwan.hackito.features.stories;

import java.util.List;
import id.arieridwan.hackito.models.ItemStories;

/**
 * Created by arieridwan on 20/06/2017.
 */

public interface StoriesView {
    void getTopStories(List<Integer> list);
    void getItem(List<ItemStories> list);
    void callData();
    void startLoading();
    void stopLoading();
}
