package fr.istic.mob.star2cd.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.model.StopTime;
import fr.istic.mob.star2cd.utils.StarContract;

/**
 * StopTime Adapter
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StopTimeAdapter extends CursorAdapter {

    private Cursor cursor;

    public StopTimeAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView stopTimeTextView = (TextView) view;
        String arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow("arrival_time"));
        stopTimeTextView.setText(arrivalTime);
    }

    @Override
    public StopTime getItem(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        StopTime stopTime = new StopTime();
        stopTime.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns._ID))));
        stopTime.setStopId(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)));
        stopTime.setTripId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID))));
        stopTime.setArrivalTime(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)));
        stopTime.setDepartureTime(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)));
        stopTime.setStopSequence(Integer.valueOf(cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE))));
        return stopTime;
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        return cursor.getInt(0);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }
}
