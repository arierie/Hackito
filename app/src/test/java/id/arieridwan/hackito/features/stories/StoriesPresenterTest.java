package id.arieridwan.hackito.features.stories;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import id.arieridwan.hackito.network.ApiServices;

/**
 * Created by arieridwan on 27/06/2017.
 */

public class StoriesPresenterTest {
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Mock
    private StoriesView storiesView;
    @Mock
    private ApiServices apiServices;

    @Test
    public void fetchValidDataShouldLoadIntoView() {

    }
}
