package id.arieridwan.hackito.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by arieridwan on 20/06/2017.
 */

public abstract class MvpFragment <P extends BasePresenter> extends BaseFragment {
    protected P presenter;

    protected abstract P onCreatePresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = onCreatePresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettachView();
        }
    }
}
