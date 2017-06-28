package id.arieridwan.hackito.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collection;

import id.arieridwan.hackito.BuildConfig;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ItemCommentsTest {

    @Mock
    private ItemComments itemComments;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        itemComments = new ItemComments();
    }

    @Test
    public void tesSetterGetter() throws Exception {
        itemComments.setBy(Mockito.anyString());
        itemComments.setId(Mockito.anyInt());
        itemComments.setParent(Mockito.anyInt());
        itemComments.setText(Mockito.anyString());
        itemComments.setTime(Mockito.anyInt());
        itemComments.setType(Mockito.anyString());
        itemComments.setKids(Mockito.anyListOf(Integer.TYPE));
        itemComments.setDeleted(Mockito.anyBoolean());
        assertThat(itemComments.getBy(), is(Mockito.anyString()));
        assertThat(itemComments.getId(), is(Mockito.anyInt()));
        assertThat(itemComments.getParent(), is(Mockito.anyInt()));
        assertThat(itemComments.getText(), is(Mockito.anyString()));
        assertThat(itemComments.getTime(), is(Mockito.anyInt()));
        assertThat(itemComments.getType(), is(Mockito.anyString()));
        assertThat(itemComments.getKids(), is(Mockito.anyListOf(Integer.TYPE)));
        assertThat(itemComments.isDeleted(), is(Mockito.anyBoolean()));
    }

}