package id.arieridwan.hackito.utils;

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
    public static String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "-";
    }
    public static String getHost (String url){
        try {
            URL url_transform = new URL(url);
            String baseUrl = url_transform.getProtocol() + "://" + url_transform.getHost();
            return  baseUrl;
        }
        catch (MalformedURLException e) {
            Log.e("getHost: ", e.getMessage());
            return "http://";
        }
    }
}
