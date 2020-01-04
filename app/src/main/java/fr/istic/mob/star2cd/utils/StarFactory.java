package fr.istic.mob.star2cd.utils;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.model.Stop;
import fr.istic.mob.star2cd.model.StopTime;

/**
 * Star Factory
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StarFactory {

    private StarFactory() {
    }

    /**
     * Returns a list of bus routes
     *
     * @param context Context
     * @return List of available bus routes
     */
    public static List<BusRoute> getAllBusRoutes(Context context) {
        List<BusRoute> busRoutes = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                StarContract.BusRoutes.CONTENT_URI, null, null, null, null);

        Objects.requireNonNull(cursor);
        while (cursor.moveToNext()) {
            BusRoute busRoute = new BusRoute();
            busRoute.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID))));
            busRoute.setRouteShortName(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)));
            busRoute.setRouteLongName(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)));
            busRoute.setRouteDescription(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)));
            busRoute.setRouteColor(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)));
            busRoute.setRouteTextColor(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR)));
            busRoute.setRouteType(cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)));
            busRoutes.add(busRoute);
        }

        cursor.close();
        return busRoutes;
    }

    /**
     * Returns bus route
     *
     * @param context Context
     * @param routeId Route identifier
     * @return Bus route
     */
    public static BusRoute getBusRoute(Context context, int routeId) {
        List<BusRoute> busRoutes = getAllBusRoutes(context);
        for (BusRoute busRoute : busRoutes) {
            if (busRoute.getId() == routeId) return busRoute;
        }
        return null;
    }

    /**
     * Returns a list of stops
     *
     * @param context   Context
     * @param routeId   Route identifier
     * @param direction Direction (0 or 1)
     * @return List of stops
     */
    public static List<Stop> getStops(Context context, String routeId, String direction) {
        List<Stop> stops = new ArrayList<>();

        String[] params = {routeId, direction};

        Cursor cursor = context.getContentResolver().query(
                StarContract.Stops.CONTENT_URI,
                null, null, params, null);

        Objects.requireNonNull(cursor);
        while (cursor.moveToNext()) {
            Stop stop = new Stop();
            stop.setId(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns._ID)));
            stop.setStopDesc(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.DESCRIPTION)));
            stop.setStopName(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)));
            stop.setStopLat(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.LATITUDE)));
            stop.setStopLon(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.LONGITUDE)));
            stop.setWheelchairBoarding(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING))));
            stops.add(stop);
        }

        cursor.close();
        return stops;
    }

    /**
     * Returns list of stop times at a given stop
     *
     * @param context     Context
     * @param stopId      Stop identifier
     * @param routeId     Route identifier
     * @param endDate     Chosen date
     * @param arrivalTime Chosen time
     * @return List of stop times
     */
    public static List<StopTime> getStopTimesAtStop(Context context, String stopId, String routeId, String endDate, String arrivalTime) {
        List<StopTime> stopTimes = new ArrayList<>();

        String[] params = {stopId, routeId, endDate, arrivalTime};

        Cursor cursor = context.getContentResolver().query(
                StarContract.StopTimes.CONTENT_URI,
                null, null, params, null);

        Objects.requireNonNull(cursor);

        while (cursor.moveToNext()) {
            StopTime stopTime = new StopTime();
            stopTime.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns._ID))));
            stopTime.setStopId(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)));
            stopTime.setTripId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID))));
            stopTime.setArrivalTime(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)));
            stopTime.setDepartureTime(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)));
            stopTime.setStopSequence(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE))));
            stopTimes.add(stopTime);
        }
        cursor.close();
        return stopTimes;
    }
}
