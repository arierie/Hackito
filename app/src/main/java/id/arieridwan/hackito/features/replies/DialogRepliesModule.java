package id.arieridwan.hackito.features.replies;

import dagger.Module;
import dagger.Provides;
import id.arieridwan.hackito.CustomScope;

/**
 * Created by arieridwan on 06/07/2017.
 */

@Module
public class DialogRepliesModule {

    private final DialogRepliesContract.View mView;

    public DialogRepliesModule(DialogRepliesContract.View view) {
        this.mView = view;
    }

    @Provides
    @CustomScope
    DialogRepliesContract.View provideDialogContractView(){
        return mView;
    }

}
