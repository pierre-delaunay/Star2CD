package fr.istic.mob.star2cd;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import fr.istic.mob.star2cd.utils.StarUtility;

import static org.junit.Assert.*;

/**
 * Star Utility Unit Test Class
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
@RunWith(AndroidJUnit4.class)
public class StarUtilityTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("fr.istic.mob.star2cd", appContext.getPackageName());
    }

    @Test
    public void storeRetrieveSharedPrefsTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        StarUtility.storeInSharedPrefs(appContext, "date", "20200101");
        assertEquals(StarUtility.retrieveFromSharedPrefs(appContext, "date"), "20200101");
    }

    @Test
    public void convertDateTest() {
        assertEquals(StarUtility.convertDateFromDB("20201229"), "29/12/2020");
        assertEquals(StarUtility.convertDateToDB("29/12/2020"), "20201229");
    }

    @Test
    public void dayOfTheWeekTest() {
        assertEquals(StarUtility.dayOfTheWeek("20200108"), "wednesday");
        assertEquals(StarUtility.dayOfTheWeek("20200109"), "thursday");
        assertEquals(StarUtility.dayOfTheWeek("20200110"), "friday");
    }
}
