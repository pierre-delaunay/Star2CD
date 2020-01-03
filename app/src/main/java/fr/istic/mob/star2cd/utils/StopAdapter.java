package fr.istic.mob.star2cd.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.model.Stop;

/**
 * Stop Adapter
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StopAdapter extends CursorAdapter {

    private Cursor cursor;

    public StopAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView stopTextview = (TextView) view;
        String stopName = cursor.getString(cursor.getColumnIndexOrThrow("stop_name"));
        stopTextview.setText(stopName);
    }

    @Override
    public Stop getItem(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        Stop stop = new Stop();
        return stop;
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        return cursor.getLong(0);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }
}