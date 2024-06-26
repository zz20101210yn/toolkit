package com.paic.jrkj.tk.util;

import org.apache.commons.lang.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 13:38:42 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 13:38:42
 */
public class DateFormatUtil {

    private DateFormatUtil(){}

    private static FastDateFormat getDateFormat(String pattern){
        return FastDateFormat.getInstance(pattern);
    }

    public static String getCurrentDT(String pattern){
        return format(Calendar.getInstance().getTime(),pattern);
    }

    public static String format(Date date,String pattern){
        return getDateFormat(pattern).format(date);
    }

    public static Date parse(String text,String pattern)
            throws ParseException {
        return new SimpleDateFormat(pattern).parse(text);
    }
}
