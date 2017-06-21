package id.arieridwan.hackito.features.main;

import java.util.List;
import id.arieridwan.hackito.models.Item;

/**
 * Created by arieridwan on 20/06/2017.
 */

public interface MainView {
    void getDataSuccess(List<Item> list);
    void startLoading();
    void stopLoading();
}
