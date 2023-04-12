package com.XZY_SUNSHINE.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String dateFormatTime(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
