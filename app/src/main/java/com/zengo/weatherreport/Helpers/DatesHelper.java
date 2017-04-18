package com.zengo.weatherreport.Helpers;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rob on 18/04/2017.
 * Helper for dates - needs looking at in detail
 *
 */

public class DatesHelper
{
    /**
     * Given a string in our weather format, return a Date - so we can maybe alter it
     * @param dt
     * @return
     */
    @Nullable
    public static Date getDate(String dt)
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = format.parse(dt);
            return date;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Return true if this date is todays date - so we can colour code the weather for this date differently
     * @param dt
     * @return
     */
    public static boolean isToday(String dt)
    {
        try
        {
            // our weather date returned from api
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date weatherdate = format.parse(dt);

            // today with time removed
            Date todayWithNoTime = format.parse(format.format(new Date()));

            // return true if equal
            return weatherdate.compareTo(todayWithNoTime) == 0;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
