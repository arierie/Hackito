package id.arieridwan.hackito.features.main;

import dagger.Component;
import id.arieridwan.hackito.CustomScope;
import id.arieridwan.hackito.di.NetComponent;

/**
 * Created by arieridwan on 27/06/2017.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
