package test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 */
public final class DateUtils {
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";

    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static String getDatePattern() {
        return FORMAT_LONG;
    }
    public static String getNow() {
        return format(new Date());
    }
    public static String getNow(String format) {
        return format(new Date(), format);
    }
    public static String format(Date date) {
        return format(date, getDatePattern());
    }
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }
    
    public static String getFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
        String first = sdf.format(calendar.getTime())+" 00:00:00";
        return first;
    }
    public static String getPrevDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG);
        String first = sdf.format(calendar.getTime());
        return first;
    }
    
    public static void main(String[] args) {
//		String date = "20230912";
//		Date newDate = parse(date, "yyyyMMdd");
//		newDate = parse(getFirstDay(newDate), FORMAT_LONG);
//		newDate = addMonth(newDate, 6);
//		System.out.println(getPrevDate(newDate));
//		
//    	SimpleDateFormat detailTime = new SimpleDateFormat("yyyy.MM.dd HH:mm");
//    	System.out.println(detailTime.format(new Date()));
    	
    	String s = "2021-07-30 00:00:00";
    	Date newDate = parse(s, "yyyy-MM-dd HH:mm:ss");
    	System.out.println(newDate.getTime());
    	
    	String s1 = "2021-07-30 23:59:59";
    	Date newDate1 = parse(s1, "yyyy-MM-dd HH:mm:ss");
    	System.out.println(newDate1.getTime());
    	
    	
//    	Calendar calendar= Calendar.getInstance();
    	System.out.println("hehehe......");
//    	calendar.setTime(newDate);
//    	if(calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
//    		SimpleDateFormat detailTime = new SimpleDateFormat("yyyy.MM.dd");
//    		System.out.println(detailTime.format(newDate));
//    	}else {
//    		SimpleDateFormat detailTime = new SimpleDateFormat("yyyy.MM.dd HH:mm");
//    		System.out.println(detailTime.format(newDate));
//    	}
	}
}