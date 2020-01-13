package fr.istic.mob.star2cd.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.utils.CircularTextView;
import fr.istic.mob.star2cd.utils.StarContract;

/**
 * Search Adapter
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class SearchAdapter extends CursorAdapter {

    private Cursor cursor;

    public SearchAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        return cursor.getInt(0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.search_view_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView searchTextView = view.findViewById(R.id.searchTextView);
        CircularTextView searchCTV = view.findViewById(R.id.searchCTV);

        String stopName = cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME));
        String shortName = cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME));
        String routeTextColor = cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR));
        String routeColor = cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR));

        searchCTV.setStrokeWidth(1);
        searchCTV.setText(shortName);
        searchCTV.setStrokeColor("#" + routeTextColor);
        searchCTV.setSolidColor("#" + routeColor);
        searchTextView.setText(stopName);
    }
}
