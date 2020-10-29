package com.mybus.mybusapp.Utils;

import android.app.Activity;


import com.mybus.mybusapp.classes.Constants;
import com.mybus.mybusapp.classes.UtilityApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by wokman on 4/2/2017.
 */

public class DateHandler {

//    static String lang = UtilityApp.INSTANCE.getLanguage();

    public static Date GetDate(Object d) {
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        parser.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        Date date = new Date();
        try {
            date = parser.parse(String.valueOf(d));
        } catch (Exception e) {

        }
        return date;
    }

    public static Date GetDateNow() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));

        return calendar.getTime();

    }


    public static Date GetDateFromDateHour(Object d) {
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = parser.parse(String.valueOf(d));
        } catch (Exception e) {

        }
        return date;
    }

    public static String GetLongToDateString(long time_stamp) {
        try {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date time = new Date((time_stamp * 1000));
            String dateStr = parser.format(time);

            return dateStr;
        } catch (Exception e) {

        }
        return "";
    }

    public static String GetDateOnlyString(long time_stamp) {
        try {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

            Date time = new Date((time_stamp * 1000));
            String dateStr = parser.format(time);

            return dateStr;
        } catch (Exception e) {

        }
        return "";
    }

    public static long GetDateTimeLong(String data) {
        try {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            parser.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
            long dateLong = parser.parse(data).getTime();

            return dateLong / 1000;
        } catch (Exception e) {

        }
        return 0;
    }

    public static long GetDateTimeLongCalender(int year, int month, int day, int hour, int min) {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));
            calendar.set(year, month, day, hour, min, 0);

//            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            long dateLong = calendar.getTimeInMillis();
//            long dateLong = parser.parse(data).getTime();

            return dateLong / 1000;
        } catch (Exception e) {

        }
        return 0;
    }


    public static long GetDateOnlyLong(String data) {
        try {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

            long dateLong = parser.parse(data).getTime();

            return dateLong / 1000;
        } catch (Exception e) {

        }
        return 0;
    }

    public static String GetMonthName(Object d) {


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));

        calendar.setTime(GetDate(d));

        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale(UtilityApp.INSTANCE.getLanguage()));
        return monthName;
    }


    public static String GetSyrianMonthName(Object d) {

        String[] monthesAr = new String[]{
                "كانون الثاني"
                , "شباط"
                , "آذار"
                , "نيسان"
                , "أيار"
                , "حزيران"
                , "تموز"
                , "آب"
                , "أيلول"
                , "تشرين الأول"
                , "تشرين الثاني"
                , "كانون الأول"
        };

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));

        calendar.setTime(GetDate(d));

        String monthName;
//        if (UtilityApp.INSTANCE.getLanguage().equals("ar")) {
        int monthInd = calendar.get(Calendar.MONTH);
        monthName = monthesAr[monthInd];
//        } else {
//            monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("en"));
//        }

        return monthName;
    }

    public static String GetYearNumber(Object d) {


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));

        calendar.setTime(GetDate(d));

        int yearNumber = calendar.get(Calendar.YEAR);
        return yearNumber + "";
    }

    public static String GetDayOfMonth(Object d) {


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));

        calendar.setTime(GetDate(d));

        int monthNumber = calendar.get(Calendar.DAY_OF_MONTH);
        return monthNumber + "";
    }

    public static String GetDayOfWeek(Object d) {

        String[] dayAr = new String[]{"الأحد", "الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"};
        String[] dayEn = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));

        calendar.setTime(GetDate(d));

//        String[] days = new String[]{
//                RootApplication.getInstance().getResources().getString(R.string.sunday)
//                , RootApplication.getInstance().getResources().getString(R.string.monday)
//                , RootApplication.getInstance().getResources().getString(R.string.tuesday)
//                , RootApplication.getInstance().getResources().getString(R.string.wednesday)
//                , RootApplication.getInstance().getResources().getString(R.string.thursday)
//                , RootApplication.getInstance().getResources().getString(R.string.friday)
//                , RootApplication.getInstance().getResources().getString(R.string.saturday)
//        };
        String day = null;
        if (UtilityApp.INSTANCE.getLanguage().equals(Constants.Arabic)) {
            day = dayAr[(calendar.get(Calendar.DAY_OF_WEEK) - 1)];
        } else {
            day = dayEn[(calendar.get(Calendar.DAY_OF_WEEK) - 1)];
        }

        return day;
    }

    public static boolean CheckDateIsNow(String date) {

        long dateNow = GetDateOnlyLong(GetDateOnlyNowString());
        long inputDate = GetDateOnlyLong(date);
        System.out.println("date now ");
        return dateNow == inputDate;
    }

    public static String GetDateString(Date date, String format) {

        DateFormat parser = new SimpleDateFormat(format);

        try {
            return parser.format(date);
        } catch (Exception e) {

        }
        return "";
    }

    public static String[] GetDateAndTimeString(String date) {

        String[] dateTime = date.split(" ");

        return dateTime;
    }


    public static String GetDateString(Date d) {

        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        try {
            return parser.format(d);
        } catch (Exception e) {

        }
        return "";
    }

    public static String GetDateOnlyNowString() {
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            // Date s = parser.parse("");
            return parser.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    public static String GetTimeOnlyNowString() {
        DateFormat parser = new SimpleDateFormat("HH:00");
        Date date = new Date();
        try {
            // Date s = parser.parse("");
            return parser.format(date);
        } catch (Exception e) {
        }
        return "";
    }


    /**
     * format string date as yyyy-MM-dd
     *
     * @param o
     * @return
     */
    public static String FormatDate(Object o) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = parser.parse(o.toString());

            return parser.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * format date as yyyy-MM-dd hh:mm aa
     *
     * @param o
     * @return
     */
    public static String FormatDate2(Object o) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        try {
            Date date = parser.parse(o.toString());

            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * format date as MMM-dd
     *
     * @param o
     * @return
     */
    public static String FormatDate3(Object o) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formater = new SimpleDateFormat("MMMM yyyy", new Locale(UtilityApp.INSTANCE.getLanguage()));
        try {
            Date date = parser.parse(o.toString());

            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String FormatDate4(Object o, String inPattern, String outPattern) {
        SimpleDateFormat parser = new SimpleDateFormat(inPattern);

        SimpleDateFormat parser2 = new SimpleDateFormat(outPattern, new Locale(UtilityApp.INSTANCE.getLanguage()));
//        parser.setTimeZone(TimeZone.getTimeZone());
        try {
            Date date = parser.parse(o.toString());

            return parser2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public static String GetDayOfMonth(Activity activity, Object d) {
//
//
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));
//
//        calendar.setTime(GetDate(d));
//
//        int monthNumber = calendar.get(Calendar.DAY_OF_MONTH);
//        return monthNumber + "";
//    }
//
//    public static String GetDayOfWeek(Activity activity, Object d) {
//
//
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE));
//
//        calendar.setTime(GetDate(d));
//
//        String[] days = new String[]{
//                activity.getString(R.string.sunday)
//                , activity.getString(R.string.monday)
//                , activity.getString(R.string.thusday)
//                , activity.getString(R.string.wednesday)
//                , activity.getString(R.string.thursday)
//                , activity.getString(R.string.friday)
//                , activity.getString(R.string.satday)
//        };
//
//        String day = days[(calendar.get(Calendar.DAY_OF_WEEK) - 1)];
//        return day;
//    }

    /**
     * format time from HH:mm to hh:mm aa
     *
     * @param o
     * @return
     */
    public static String FormatTime(Object o) {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formater = new SimpleDateFormat("hh:mm aa");
        try {
            Date date = parser.parse(o.toString());

            return NumberHandler.INSTANCE.arabicToDecimal(formater.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * format time from HH:mm to HH:mm
     *
     * @param o
     * @return
     */
    public static String FormatTime2(Object o) {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        try {
            Date date = parser.parse(o.toString());

            return NumberHandler.INSTANCE.arabicToDecimal(formater.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String GetTimeFromDateString(long timeStamp) {

        Date date1 = new Date(timeStamp * 1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        return FormatTime(hour + ":" + minute);
    }

    public static String FormatHoursToString(Activity activity, int hours) {

        final String dayAr = "يوم";
        final String dayEn = "Day";
        final String hourAr = "ساعة";
        final String hourEn = "Hour";
        final String andAr = "و";
        final String andEn = "And";

        String dayStr;
        String hourStr;
        String andStr;

        String dayText = "";
        String hourText = "";
        double restHour;

        int day = hours / 24;
        restHour = hours % 24;

        if (UtilityApp.INSTANCE.getLanguage().equals(Constants.English)) {
            dayStr = dayEn;
            hourStr = hourEn;
            andStr = andEn;
        } else {
            dayStr = dayAr;
            hourStr = hourAr;
            andStr = andAr;
        }


        int hour = (int) (restHour * 60);

        if (day > 0) {
            dayText = day + " " + dayStr;
//            dayText = day < 3 ? "" : (day + " ");
//            dayText += day == 1 ? "يوم" : day == 2 ? "يومان" : day < 11 ? "ايام" : "يوم";
        }

        if (hour > 0) {
            hourText = day > 0 ? " " + andStr + " " : "";
            hourText = hour + " " + hourStr;
//            hourText += hour < 3 ? "" : (hour + " ");
//            hourText += hour == 1 ? "ساعة" : hour == 2 ? "ساعتين" : hour < 11 ? "ساعات" : "ساعة";
        }

        return dayText + hourText;
    }

    public static String ConvertMinutesToString(double minutes, boolean IsHour) {

        if (IsHour) {
            minutes = minutes * 60;
        }

        String dayText = "";
        String hourText = "";
        String minuteText = "";

        int day = (int) minutes / 1440;
        minutes = minutes % 1440;

        int hour = (int) minutes / 60;
        minutes = minutes % 60;


        if (day > 0) {
            dayText = day < 3 ? "" : (day + " ");
            dayText += day == 1 ? "يوم" : day == 2 ? "يومان" : day < 11 ? "ايام" : "يوم";
        }

        if (hour > 0) {
            hourText = day > 0 ? " و " : "";
            hourText += hour < 3 ? "" : (hour + " ");
            hourText += hour == 1 ? "ساعة" : hour == 2 ? "ساعتين" : hour < 11 ? "ساعات" : "ساعة";
        }

        if (minutes > 0) {
            minuteText = day > 0 || hour > 0 ? " و " : "";
            minuteText += minutes < 3 ? "" : (minutes + " ");
            minuteText += minutes == 1 ? "دقيقة" : minutes == 2 ? "دقيقتين" : minutes < 11 ? "دقائق" : "دقيقة";
        }


        return dayText + " " + hourText + " " + minuteText;
    }

    public static String ConvertMinutesToString2(double minutes, boolean IsHour) {

        if (IsHour) {
            minutes = minutes * 60;
        }

        String dayText = "";
        String hourText = "";
        String minuteText = "";

        int day = (int) minutes / 1440;
        minutes = minutes % 1440;

        int hour = (int) minutes / 60;
        minutes = minutes % 60;
        int minutesInt = (int) minutes;


        if (day > 0) {
            dayText = day < 1 ? "" : (day + " ي");
        }

        if (hour > 0) {
            hourText = day > 0 ? " و " : "";
            hourText += hour < 1 ? "" : (hour + " س");
        }

        if (minutes > 0) {
            minuteText = day > 0 || hour > 0 ? " و " : "";
            minuteText += minutesInt < 1 ? "" : (minutesInt + " د");
        }


        return dayText + " " + hourText + " " + minuteText;
    }


    public static String formatStringToAgo(/*Activity activity,*/ String dateText) {
        long time = GetDateTimeLong(dateText);

        return formatToAgo(/*activity, */time);

    }

    public static String formatToAgo(/*Activity activity,*/ long dateText) {

        final int yearInSec = 31104000;
        final int monthInSec = 2592000;
        final int weekInSec = 604800;
        final int dayInSec = 68400;
        final int hourInSec = 3600;
        final int minInSec = 60;

        final String sinceAr = "منذ";
        final String sinceEn = "since";
        final String sinceStr;


        long oldTime = dateText;
        long currentTime = System.currentTimeMillis() / 1000;

//        String lang = UtilityApp.INSTANCE.getLanguage();
//        System.out.println("Log diff "+ (currentTime - oldTime));

        if (UtilityApp.INSTANCE.getLanguage().equals(Constants.English)) {
            sinceStr = sinceEn;
        } else {
            sinceStr = sinceAr;
        }

        long diff = currentTime - oldTime;

//        Log.e("DateHandler", "Log diff time " + diff);
        String yearText = "";
        String monthText = "";
        String weekText = "";
        String dayText = "";
        String hourText = "";
        String minuteText = "";

        int year = (int) diff / yearInSec;
//        diff = diff % yearInSec;
        if (year > 0) {
            yearText = getDateStrings(year, "year", UtilityApp.INSTANCE.getLanguage());
//            if (year == 1) {
//                yearText = RootApplication.getInstance().getResources().getString(R.string.year);
//            } else if (year == 2) {
//                yearText = RootApplication.getInstance().getResources().getString(R.string.two_year);
//            } else if (year <= 10) {
//                yearText = year + " " + RootApplication.getInstance().getResources().getString(R.string.years);
//            } else {
//                yearText = year + " " + RootApplication.getInstance().getResources().getString(R.string.year);
//            }

            return sinceStr + " " + yearText;
        }

        int month = (int) diff / monthInSec;
//        diff = diff % monthInSec;
        if (month > 0) {
            monthText = getDateStrings(month, "month", UtilityApp.INSTANCE.getLanguage());
//            if (month == 1) {
//                monthText = RootApplication.getInstance().getResources().getString(R.string.month);
//            } else if (month == 2) {
//                monthText = RootApplication.getInstance().getResources().getString(R.string.two_month);
//            } else if (month <= 10) {
//                monthText = month + " " + RootApplication.getInstance().getResources().getString(R.string.months);
//            } else {
//                monthText = month + " " + RootApplication.getInstance().getResources().getString(R.string.month);
//            }
            return sinceStr + " " + monthText;
        }

        int week = (int) diff / weekInSec;
//        diff = diff % weekInSec;
        if (week > 0) {
            weekText = getDateStrings(week, "week", UtilityApp.INSTANCE.getLanguage());
//            if (week == 1) {
//                weekText = RootApplication.getInstance().getResources().getString(R.string.week);
//            } else if (week == 2) {
//                weekText = RootApplication.getInstance().getResources().getString(R.string.two_week);
//            } else if (week <= 10) {
//                weekText = week + " " + RootApplication.getInstance().getResources().getString(R.string.weeks);
//            } else {
//                weekText = week + " " + RootApplication.getInstance().getResources().getString(R.string.week);
//            }
            return sinceStr + " " + weekText;
        }

        int day = (int) diff / dayInSec;
//        diff = diff % dayInSec;
        if (day > 0) {
            dayText = getDateStrings(day, "day", UtilityApp.INSTANCE.getLanguage());
//            if (day == 1) {
//                dayText = RootApplication.getInstance().getResources().getString(R.string.day);
//            } else if (day == 2) {
//                dayText = RootApplication.getInstance().getResources().getString(R.string.two_day);
//            } else if (day <= 10) {
//                dayText = day + " " + RootApplication.getInstance().getResources().getString(R.string.days);
//            } else {
//                dayText = day + " " + RootApplication.getInstance().getResources().getString(R.string.day);
//            }
            return sinceStr + " " + dayText;
        }

        int hour = (int) diff / hourInSec;
//        diff = diff % hourInSec;
        if (hour > 0) {
            hourText = getDateStrings(hour, "hour", UtilityApp.INSTANCE.getLanguage());
//            if (hour == 1) {
//                hourText = RootApplication.getInstance().getResources().getString(R.string.hour);
//            } else if (hour == 2) {
//                hourText = RootApplication.getInstance().getResources().getString(R.string.two_hour);
//            } else if (hour <= 10) {
//                hourText = hour + " " + RootApplication.getInstance().getResources().getString(R.string.hours);
//            } else {
//                hourText = hour + " " + RootApplication.getInstance().getResources().getString(R.string.hour);
//            }
            return sinceStr + " " + hourText;
        }

        int minutes = (int) diff / minInSec;
        if (minutes > 0) {
            minuteText = getDateStrings(minutes, "minute", UtilityApp.INSTANCE.getLanguage());
//            if (minutes == 1) {
//                minuteText = RootApplication.getInstance().getResources().getString(R.string.minute);
//            } else if (minutes == 2) {
//                minuteText = RootApplication.getInstance().getResources().getString(R.string.two_minute);
//            } else if (minutes <= 10) {
//                minuteText = minutes + " " + RootApplication.getInstance().getResources().getString(R.string.minutes);
//            } else {
//                minuteText = minutes + " " + RootApplication.getInstance().getResources().getString(R.string.minute);
//            }
            return sinceStr + " " + minuteText;
        }

        return getDateStrings(0, "now", UtilityApp.INSTANCE.getLanguage());

    }

    private static String getDateStrings(int count, String type, String lang) {

        switch (type) {
            case "year":
                if (count == 1) {
                    return lang.equals(Constants.Arabic) ? "سنة" : "1 year";
                } else if (count == 2) {
                    return lang.equals(Constants.Arabic) ? "سنتين" : "2 years";
                } else if (count <= 10) {
                    return lang.equals(Constants.Arabic) ? count + " سنوات" : count + " years";
                } else {
                    return lang.equals(Constants.Arabic) ? count + " سنة" : count + " year";
                }

            case "month":
                if (count == 1) {
                    return lang.equals(Constants.Arabic) ? "شهر" : "1 month";
                } else if (count == 2) {
                    return lang.equals(Constants.Arabic) ? "شهرين" : "2 months";
                } else if (count <= 10) {
                    return lang.equals(Constants.Arabic) ? count + " شهور" : count + " months";
                } else {
                    return lang.equals(Constants.Arabic) ? count + " شهر" : count + " month";
                }
            case "week":
                if (count == 1) {
                    return lang.equals(Constants.Arabic) ? "إسبوع" : "1 week";
                } else if (count == 2) {
                    return lang.equals(Constants.Arabic) ? "اسبوعين" : "2 weeks";
                } else if (count <= 10) {
                    return lang.equals(Constants.Arabic) ? count + " أسابيع" : count + " weeks";
                } else {
                    return lang.equals(Constants.Arabic) ? count + " إسبوع" : count + " week";
                }
            case "day":
                if (count == 1) {
                    return lang.equals(Constants.Arabic) ? "يوم" : "1 day";
                } else if (count == 2) {
                    return lang.equals(Constants.Arabic) ? "يومين" : "2 days";
                } else if (count <= 10) {
                    return lang.equals(Constants.Arabic) ? count + " ايام" : count + " days";
                } else {
                    return lang.equals(Constants.Arabic) ? count + " يوم" : count + " day";
                }
            case "hour":
                if (count == 1) {
                    return lang.equals(Constants.Arabic) ? "ساعة" : "1 hour";
                } else if (count == 2) {
                    return lang.equals(Constants.Arabic) ? "ساعتين" : "2 hours";
                } else if (count <= 10) {
                    return lang.equals(Constants.Arabic) ? count + " ساعات" : count + " hours";
                } else {
                    return lang.equals(Constants.Arabic) ? count + " ساعة" : count + " hour";
                }
            case "minute":
                if (count == 1) {
                    return lang.equals(Constants.Arabic) ? "دقيقة" : "1 min";
                } else if (count == 2) {
                    return lang.equals(Constants.Arabic) ? "دقيقتان" : "2 mins";
                } else if (count <= 10) {
                    return lang.equals(Constants.Arabic) ? count + " دقائق" : count + " mins";
                } else {
                    return lang.equals(Constants.Arabic) ? count + " دقيقة" : count + " min";
                }
            case "now":
                return lang.equals(Constants.Arabic) ? "الأن" : "now";

        }
        return "";
    }

    public static double ConvertSecondsToMinutes(long dateText) {
        String str = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d = null;
        try {
            d = dateFormat.parse(dateText + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long oldTime = d.getTime();
        long currentTime = System.currentTimeMillis();

        long diff = currentTime - oldTime;
        long diffMinutes = diff / (60 * 1000);

        return diffMinutes;
    }
}
