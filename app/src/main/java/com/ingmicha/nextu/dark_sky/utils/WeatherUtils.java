package com.ingmicha.nextu.dark_sky.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Michael Alejandro Ura A DesarrolloApps on 5/6/18.
 */

public class WeatherUtils {

    public String unixTimeStampToDate(long unixDate, String formarDate){

        Date date = new Date(unixDate*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat(formarDate);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        return sdf.format(date).toUpperCase();
    }

}
