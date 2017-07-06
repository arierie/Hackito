package id.arieridwan.hackito.features.main;

import java.util.List;
import id.arieridwan.hackito.models.ItemStories;

/**
 * Created by arieridwan on 27/06/2017.
 */

public interface  MainContract {

    interface View {
        void getTopStories(List<Integer> list);
        void getItem(List<ItemStories> list);
        void startLoading();
        void stopLoadingError(Throwable e);
        void stopLoading();
    }

    interface Presenter {
        void loadTopStories();
        void loadItem(List<Integer> list);
    }
}
