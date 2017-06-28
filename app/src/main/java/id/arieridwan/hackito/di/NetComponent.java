package id.arieridwan.hackito.di;

import javax.inject.Singleton;
import dagger.Component;
import id.arieridwan.hackito.network.ApiServices;
import retrofit2.Retrofit;

/**
 * Created by arieridwan on 27/06/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    ApiServices apiServices();
}
