package com.hdz.step.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class DateFormatHelper {

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static SimpleDateFormat getFormat(String partten) {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern(partten);
        return format;
    }

    public static String formatData(String dataFormat, long timeStamp) {
        try {
            SimpleDateFormat format = getFormat(dataFormat);
            return format.format(new Date(timeStamp));
        } catch (Exception e) {
            Console.printStackTrace(e);
        }

        return "";
    }

    public static String formatData(String dataFormat, Date timeStamp) {
        try {
            SimpleDateFormat format = getFormat(dataFormat);
            return format.format(timeStamp);
        } catch (Exception e) {
            Console.printStackTrace(e);
        }

        return "";
    }
}
