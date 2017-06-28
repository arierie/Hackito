package id.arieridwan.hackito.features.detail;

import dagger.Module;
import dagger.Provides;
import id.arieridwan.hackito.CustomScope;

/**
 * Created by arieridwan on 27/06/2017.
 */

@Module
public class DetailModule {

    private final DetailContract.View mView;

    public DetailModule(DetailContract.View view) {
        this.mView = view;
    }

    @Provides
    @CustomScope
    DetailContract.View provideDetailContractView(){
        return mView;
    }

}
