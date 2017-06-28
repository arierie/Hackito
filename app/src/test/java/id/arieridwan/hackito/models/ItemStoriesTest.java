package id.arieridwan.hackito.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import id.arieridwan.hackito.BuildConfig;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ItemStoriesTest {

    @Mock
    private ItemStories itemStories;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        itemStories = new ItemStories();
    }

    @Test
    public void testSetterGetter() throws Exception {
        itemStories.setAuthor(Mockito.anyString());
        itemStories.setDescendants(Mockito.anyInt());
        itemStories.setId(Mockito.anyInt());
        itemStories.setScore(Mockito.anyInt());
        itemStories.setTime(Mockito.anyInt());
        itemStories.setTitle(Mockito.anyString());
        itemStories.setType(Mockito.anyString());
        itemStories.setUrl(Mockito.anyString());
        itemStories.setKids(Mockito.anyListOf(Integer.TYPE));
        assertThat(itemStories.getAuthor(), is(Mockito.anyString()));
        assertThat(itemStories.getDescendants(), is(Mockito.anyInt()));
        assertThat(itemStories.getId(), is(Mockito.anyInt()));
        assertThat(itemStories.getScore(), is(Mockito.anyInt()));
        assertThat(itemStories.getTime(), is(Mockito.anyInt()));
        assertThat(itemStories.getTitle(), is(Mockito.anyString()));
        assertThat(itemStories.getType(), is(Mockito.anyString()));
        assertThat(itemStories.getUrl(), is(Mockito.anyString()));
        assertThat(itemStories.getKids(), is(Mockito.anyListOf(Integer.TYPE)));
    }

}