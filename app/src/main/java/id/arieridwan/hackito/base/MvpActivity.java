package id.arieridwan.hackito.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by arieridwan on 20/06/2017.
 */

public abstract class MvpActivity <P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    protected abstract P onCreatePresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = onCreatePresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettachView();
        }
    }
}
