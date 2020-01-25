package fr.istic.mob.star2cd.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Star Utility
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StarUtility {

    /**
     * Store string in shared preferences
     *
     * @param key   String
     * @param value String
     */
    public static void storeInSharedPrefs(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Retrieve string value from shared preferences
     *
     * @param key String
     * @return String value
     */
    public static String retrieveFromSharedPrefs(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");
        if (!value.equalsIgnoreCase("")) {
            return value;
        }
        return null;
    }

    /**
     * Convert Date
     *
     * @param date "01/01/2020"
     * @return String 20200101
     */
    public static String convertDateToDB(String date) {
        String[] splits = date.split("/");
        return splits[2] + splits[1] + splits[0];
    }

    /**
     * Convert Date
     *
     * @param date 20200101
     * @return String "01/01/2020"
     */
    public static String convertDateFromDB(String date) {
        String year, month, day;
        year = date.substring(0, 4);
        month = date.substring(4, 6);
        day = date.substring(6);
        return day + "/" + month + "/" + year;
    }

    /**
     * Returns day of the week
     *
     * @param dateString yyyymmdd
     * @return name of the day of the week : monday, tuesday...
     */
    public static String dayOfTheWeek(String dateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd", Locale.ENGLISH);
            Date chosenDate = simpleDateFormat.parse(dateString);
            if (chosenDate != null) {
                return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(chosenDate).toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
