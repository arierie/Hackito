package id.arieridwan.hackito.features.replies;

import dagger.Component;
import id.arieridwan.hackito.CustomScope;
import id.arieridwan.hackito.di.NetComponent;

/**
 * Created by arieridwan on 06/07/2017.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = DialogRepliesModule.class)
public interface DialogRepliesComponent {
    void inject(DialogRepliesFragment fragment);
}
