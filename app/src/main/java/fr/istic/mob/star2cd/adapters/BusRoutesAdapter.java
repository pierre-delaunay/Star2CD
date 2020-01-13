package fr.istic.mob.star2cd.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.istic.mob.star2cd.R;

/**
 * Bus Routes Adapter
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class BusRoutesAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> busRoutes;
    private ArrayList<String> colors;

    public BusRoutesAdapter(Context context, int resource, ArrayList<String> busRoutes, ArrayList<String> colors) {
        super(context, resource, busRoutes);
        this.context = context;
        this.busRoutes = busRoutes;
        this.colors = colors;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.busroute_spinner_item, parent, false);
        }

        String item = busRoutes.get(position);
        String colorItem = colors.get(position);

        if (item != null) {
            final TextView text1 = (TextView) row.findViewById(R.id.itemId);

            text1.setText(item);
            try {
                text1.setTextColor(Color.parseColor(colorItem));
            } catch (IllegalArgumentException e) {
                text1.setTextColor(Color.parseColor("#3F51B5"));
            }
        }

        return row;
    }
}
