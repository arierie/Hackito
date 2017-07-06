package id.arieridwan.hackito.utils;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import id.arieridwan.hackito.BuildConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class StringHelperTest {

    @Test
    public void tesGetRelativeTimeMock() throws Exception {
        // mock case
        String mockTestCase = StringHelper.getRelativeTime(anyLong());
        assertThat(mockTestCase, is(StringHelper.getRelativeTime(anyLong())));
    }

    @Test
    public void tesGetRelativeTimeStatic() throws Exception {
        // static case with jakarta time
        long timeStamp = 878068800;
        // it will not change again, the timezone for test is fix
        String staticResult = "Oct 28, 1997";
        String staticTestResult = StringHelper.getRelativeTime(timeStamp);
        TimeZone tz = TimeZone.getDefault();
        if(tz.getID().contains("Jakarta")){
            assertThat(staticTestResult, is(staticResult));
        }
    }

    @Test
    public void tesGetRelativeTimeZero() throws Exception {
        // zero timestamp
        long timeStamp = 0;
        String staticResult = "Jan 1, 1970";
        String staticTestResult = StringHelper.getRelativeTime(timeStamp);
        assertThat(staticTestResult, is(staticResult));
    }

    @Test
    public void testGetHost() throws Exception {
        // static url
        String url = "https://hackerhunt.co";
        String result = "hackerhunt.co";
        String tesResult = StringHelper.getHost(url);
        assertThat(tesResult, is(result));
    }

    @Test
    public void testGetHostNull() throws Exception {
        // null url
        String url = null;
        String result = "-";
        String tesResult = StringHelper.getHost(url);
        assertThat(tesResult, is(result));
    }

    @Test
    public void testGetHostEmpty() throws Exception {
        // empty url
        String url = "";
        String result = "-";
        String tesResult = StringHelper.getHost(url);
        assertThat(tesResult, is(result));
    }

}