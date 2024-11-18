package com.example.project_palm_on;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String getTimeAgo(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        try {
            Date past = sdf.parse(time);
            Date now = new Date();

            long difference = now.getTime() - past.getTime();

            if (difference < DateUtils.MINUTE_IN_MILLIS) {
                return "Beberapa detik yang lalu";
            } else if (difference < DateUtils.HOUR_IN_MILLIS) {
                long minutes = difference / DateUtils.MINUTE_IN_MILLIS;
                return minutes + " menit yang lalu";
            } else if (difference < DateUtils.DAY_IN_MILLIS) {
                long hours = difference / DateUtils.HOUR_IN_MILLIS;
                return hours + " jam yang lalu";
            } else {
                long days = difference / DateUtils.DAY_IN_MILLIS;
                return days + " hari yang lalu";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Waktu tidak valid";
    }
}
