package id.arieridwan.hackito;

import android.app.Application;
import id.arieridwan.hackito.di.AppModule;
import id.arieridwan.hackito.di.DaggerNetComponent;
import id.arieridwan.hackito.di.NetComponent;
import id.arieridwan.hackito.di.NetModule;
import id.arieridwan.hackito.utils.Constants;

/**
 * Created by arieridwan on 27/06/2017.
 */

public class App extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Constants.BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}
