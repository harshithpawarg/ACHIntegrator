package com.mca.service;

import com.kolejnik.bizdays.calendar.AmericanBusinessCalendarFactory;
import com.kolejnik.bizdays.calendar.BusinessCalendar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHelper {

    public Boolean isHoliday(Date date) {
        AmericanBusinessCalendarFactory factory = new AmericanBusinessCalendarFactory();
        BusinessCalendar businessCalendar = factory.getInstance();
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return businessCalendar.isHoliday(LocalDate.of(calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH) + 1, calender.get(Calendar.DAY_OF_MONTH)));
    }

    public Boolean isWeekEnd(Date date) {
        Boolean res = false;
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int day = calender.get(Calendar.DAY_OF_WEEK);
        if (day == 1 || day == 7) {
            res = true;
        }
        return res;
    }

    public Boolean isWeekly(Date startDate, Date date) {
        Boolean isWeekly = false;
        Calendar calender = Calendar.getInstance();
        calender.setTime(startDate);
        int startDateday = calender.get(Calendar.DAY_OF_WEEK);
        calender.setTime(date);
        int dateday = calender.get(Calendar.DAY_OF_WEEK);
        if (startDateday == dateday) {
            isWeekly = true;
        }
        return isWeekly;
    }

    public Date format(Date date) {
        Date result = date;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
            String formatedDate = simpleDateFormat.format(result);

            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM-dd-yyyy");
            result = simpleDateFormat1.parse(formatedDate);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Date> getDatesBetweenDates(Date fromDate, Date toDate) {
        List<Date> missedDate = new ArrayList<>();
        int days = (int)((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
        for(int index = 0; index < days; index++) {
            Calendar calender = Calendar.getInstance();
            calender.setTime(fromDate);
            calender.add(Calendar.DAY_OF_MONTH, index + 1);
            int dateDay = calender.get(Calendar.DAY_OF_WEEK);
            if (dateDay != 1 && dateDay != 7) {
                missedDate.add(calender.getTime());
            }
        }
        return missedDate;
    }
}
