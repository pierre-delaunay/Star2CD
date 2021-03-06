package fr.istic.mob.star2cd.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.utils.StarContract;

/**
 * Route Detail Adapter
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class RouteDetailAdapter extends CursorAdapter {

    private Cursor cursor;

    public RouteDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView routeDetailTextView = (TextView) view;
        String stopName = cursor.getString(cursor.getColumnIndexOrThrow("stop_name"));
        String arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow("arrival_time"));
        routeDetailTextView.setText(stopName + " @ "  + arrivalTime);
    }

    @Override
    public String[] getItem(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        String[] values = {cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME))};
        return values;
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
