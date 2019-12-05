package fr.istic.mob.star2cd.model;

import androidx.annotation.NonNull;
/**
 * Stop model
 * @version 1.0.1
 * @author Charly C, Pierre D
 */
public class Stop {

    private String id;
    private String stopName;
    private String stopDesc;
    private String stopLat;
    private String stopLon;
    private int wheelchairBoarding;

    public Stop() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public String getStopLat() {
        return stopLat;
    }

    public void setStopLat(String stopLat) {
        this.stopLat = stopLat;
    }

    public String getStopLon() {
        return stopLon;
    }

    public void setStopLon(String stopLon) {
        this.stopLon = stopLon;
    }

    public int getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public void setWheelchairBoarding(int wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
    }

}