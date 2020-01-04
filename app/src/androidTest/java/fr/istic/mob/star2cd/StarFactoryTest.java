package fr.istic.mob.star2cd;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.model.Stop;
import fr.istic.mob.star2cd.model.StopTime;
import fr.istic.mob.star2cd.utils.StarFactory;

import static org.junit.Assert.*;

/**
 * Star Factory Unit Test Class
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
@RunWith(AndroidJUnit4.class)
public class StarFactoryTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("fr.istic.mob.star2cd", appContext.getPackageName());
    }

    @Test
    public void getBusRoutesCursorTest() {
        List<BusRoute> busRoutes = StarFactory.getAllBusRoutes(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotNull(busRoutes);
        assertEquals(busRoutes.get(0).getRouteShortName(), "C1");
        assertEquals(busRoutes.get(4).getRouteShortName(), "C5");

        BusRoute C1 = StarFactory.getBusRoute(InstrumentationRegistry.getInstrumentation().getTargetContext(), 1);
        assertNotNull(C1);
        assertEquals(C1.getId(), 1);
    }

    @Test
    public void getStopsCursorTest() {
        List<Stop> stops = StarFactory.getStops(InstrumentationRegistry.getInstrumentation().getTargetContext(), "1", "1");
        assertNotNull(stops);
    }

    @Test
    public void getStopsTimeCursorTest() {
        List<StopTime> stopTimes = StarFactory.getStopTimesAtStop(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                "3305", "50", "20191229", "10:36:00");
        assertNotNull(stopTimes);
        assertEquals(stopTimes.size(), 0);
    }
}
