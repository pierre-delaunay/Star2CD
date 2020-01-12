package fr.istic.mob.star2cd.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.utils.CircularTextView;
import fr.istic.mob.star2cd.utils.StarContract;
import fr.istic.mob.star2cd.utils.StarFactory;
import fr.istic.mob.star2cd.utils.StopAdapter;

/**
 * Stop Fragment
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StopFragment extends Fragment {

    private int routeId;
    private int direction;
    private StopFragmentListener fragmentListener;
    private Context mContext;

    /**
     * Static factory
     *
     * @return new instance of StopFragment
     */
    public static StopFragment newInstance(int routeId, int direction) {
        return new StopFragment(routeId, direction);
    }

    private StopFragment(int routeId, int direction) {
        this.routeId = routeId;
        this.direction = direction;
    }

    public interface StopFragmentListener {

        /**
         * Triggered after a click on a stop
         *
         * @param stopId    stop identifier
         * @param routeId   route identifier
         * @param direction direction
         */
        void onStopClick(int stopId, int routeId, int direction);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (getActivity() instanceof BusRouteFragment.BusRouteFragmentListener) {
            fragmentListener = (StopFragment.StopFragmentListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        ListView list = view.findViewById(R.id.list);

        String[] params = {String.valueOf(routeId), String.valueOf(direction)};
        //Log.i("PARAMS : ", String.valueOf(routeId) + " " + String.valueOf(direction));

        Cursor cursor = mContext.getContentResolver().query(
                StarContract.Stops.CONTENT_URI,
                null, null, params, null);

        final StopAdapter stopAdapter = new StopAdapter(getContext(), cursor);
        list.setAdapter(stopAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int chosenStopId = Integer.valueOf(stopAdapter.getItem(i).getId());
                fragmentListener.onStopClick(chosenStopId, routeId, direction);
            }
        });

        BusRoute busRoute = StarFactory.getBusRoute(getContext(), routeId);
        CircularTextView busTextView = view.findViewById(R.id.circularTextView);
        TextView directionTextView = view.findViewById(R.id.directionTextView);
        Objects.requireNonNull(busRoute);
        busTextView.setStrokeWidth(1);
        busTextView.setText(busRoute.getRouteShortName());
        busTextView.setStrokeColor("#" + busRoute.getRouteTextColor());
        busTextView.setSolidColor("#" + busRoute.getRouteColor());
        directionTextView.setText(getDirectionName(busRoute, direction));

        return view;
    }

    /**
     * Returns the name of the direction
     *
     * @param busRoute  BusRoute
     * @param direction Direction
     * @return Name of the direction
     */
    private String getDirectionName(BusRoute busRoute, int direction) {
        String longName = busRoute.getRouteLongName();
        String[] splits = longName.split(" <> ");
        if (direction == 0) {
            return splits[0];
        } else {
            return splits[splits.length - 1];
        }
    }
}
