package id.arieridwan.hackito.features.main;

import dagger.Module;
import dagger.Provides;
import id.arieridwan.hackito.CustomScope;

/**
 * Created by arieridwan on 27/06/2017.
 */

@Module
public class MainModule {

    private final MainContract.View mView;

    public MainModule(MainContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainContract.View providesMainContractView() {
        return mView;
    }

}
