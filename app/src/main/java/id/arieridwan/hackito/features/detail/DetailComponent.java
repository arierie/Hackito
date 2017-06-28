package id.arieridwan.hackito.features.detail;

import dagger.Component;
import id.arieridwan.hackito.CustomScope;
import id.arieridwan.hackito.di.NetComponent;

/**
 * Created by arieridwan on 27/06/2017.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = DetailModule.class)
public interface DetailComponent {

    void inject(DetailActivity detailActivity);
}
