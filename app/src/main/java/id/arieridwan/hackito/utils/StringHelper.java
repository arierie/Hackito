package id.arieridwan.hackito.utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by arieridwan on 21/06/2017.
 */

public class StringHelper {
    public static String getRelativeTime(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            Date currenTimeZone = calendar.getTime();
            long longTimeStamp = currenTimeZone.getTime();
            String relativeTIme = String.valueOf(DateUtils
                    .getRelativeTimeSpanString(longTimeStamp,
                            System.currentTimeMillis(),
                            DateUtils.SECOND_IN_MILLIS));
            return relativeTIme;
        }catch (Exception e) {
            Log.e("getRelativeTime: ", e.getMessage().toString());
        }
        return "-";
    }
    public static String getHost (String url){
        try {
            URL url_transform = new URL(url);
            String domain = url_transform.getHost();
            return  domain.startsWith("www.") ? domain.substring(4) : domain;
        }
        catch (MalformedURLException e) {
            Log.e("getHost: ", e.getMessage().toString());
            return "-";
        }
    }
}
