package com.example.user.leonardonewsapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperMethods {

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static Date formatDate(String dateString){
        SimpleDateFormat format;
        //If length > 20, the date contains milliseconds
        //and will throw a ParseException if parsed with pattern "yyyy-MM-dd'T'HH:mm:ss'Z'"
        //Java.Time class is not used because DateTimeFormatter.ofPattern requires API 26 and above

        //The following pattern includes milliseconds
        if(dateString.length() > 20) format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //The folowing pattern does not include milliseconds
        else format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            date = null;
            e.printStackTrace();
        }

        return date;
    }

    //Gets time difference between two Date objects
    public static long[] timeDifference(Date startDate, Date endDate){
        if(startDate == null | endDate == null) return null;

        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;

        return new long[]{elapsedHours, elapsedMinutes};
    }
}
