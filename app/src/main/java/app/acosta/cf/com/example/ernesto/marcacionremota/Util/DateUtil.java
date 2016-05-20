package app.acosta.cf.com.example.ernesto.marcacionremota.Util;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ernesto on 17/2/2016.
 */
public class DateUtil {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "hh:mm";

    public static CharSequence convertToString(Date date, String format) {
        return DateFormat.format(format, date);
    }

    public static CharSequence convertCurrentTimeToString() {
        Calendar time = Calendar.getInstance();
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int min = time.get(Calendar.MINUTE);
        return Integer.valueOf(hour) + ":" + Integer.valueOf(min);
    }

    public static CharSequence convertCurrentDateToString() {
        return DateFormat.format(DATE_FORMAT, new Date());
    }
}
