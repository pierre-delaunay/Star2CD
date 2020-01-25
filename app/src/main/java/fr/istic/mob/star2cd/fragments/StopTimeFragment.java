package fr.istic.mob.star2cd.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.model.StopTime;
import fr.istic.mob.star2cd.utils.CircularTextView;
import fr.istic.mob.star2cd.utils.StarContract;
import fr.istic.mob.star2cd.utils.StarFactory;
import fr.istic.mob.star2cd.utils.StarUtility;
import fr.istic.mob.star2cd.adapters.StopTimeAdapter;

/**
 * Stop Time Fragment
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StopTimeFragment extends Fragment {

    private StopTimeFragmentListener fragmentListener;
    private int routeId, stopId, direction;
    private Context mContext;

    /**
     * Static factory
     *
     * @return new instance of StopTimeFragment
     */
    public static StopTimeFragment newInstance(int stopId, int routeId, int direction) {
        return new StopTimeFragment(stopId, routeId, direction);
    }

    private StopTimeFragment(int stopId, int routeId, int direction) {
        this.stopId = stopId;
        this.routeId = routeId;
        this.direction = direction;
    }

    public interface StopTimeFragmentListener {

        /**
         * Triggered after a click on stop time
         *
         * @param chosenStopTime chosen StopTime
         * @param routeId        route identifier
         */
        void onStopTimeClick(StopTime chosenStopTime, int routeId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (getActivity() instanceof StopTimeFragment.StopTimeFragmentListener) {
            fragmentListener = (StopTimeFragment.StopTimeFragmentListener) getActivity();
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
        View view = inflater.inflate(R.layout.fragment_stoptime, container, false);
        ListView list = view.findViewById(R.id.list);

        Log.i("STOPTIME", String.valueOf(stopId));

        // selectionArgs[0] : stop_id
        // selectionArgs[1] : route_id
        // selectionArgs[2] : direction_id
        // selectionArgs[3] : dayOfTheWeek
        // selectionArgs[4] : endDate
        // selectionArgs[5] : arrivalTime
        String arrivalTime = StarUtility.retrieveFromSharedPrefs(mContext, "time");
        String endDate = StarUtility.retrieveFromSharedPrefs(mContext, "date");
        String day = StarUtility.dayOfTheWeek(endDate);

        Log.i("STOPTIME", "arrival time : " + arrivalTime + " endDate : " + endDate + " day " + day);

        String[] params = {String.valueOf(stopId), String.valueOf(routeId), String.valueOf(direction), day, endDate, arrivalTime};

        Cursor cursor = mContext.getContentResolver().query(
                StarContract.StopTimes.CONTENT_URI,
                null, null, params, null);

        final StopTimeAdapter stopTimeAdapter = new StopTimeAdapter(getContext(), cursor);
        list.setAdapter(stopTimeAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StopTime chosenStopTime = stopTimeAdapter.getItem(i);
                fragmentListener.onStopTimeClick(chosenStopTime, routeId);
            }
        });

        BusRoute busRoute = StarFactory.getBusRoute(getContext(), routeId);
        CircularTextView busTextView = view.findViewById(R.id.stopTimesCTV);
        Objects.requireNonNull(busRoute);
        busTextView.setStrokeWidth(1);
        busTextView.setText(busRoute.getRouteShortName());
        busTextView.setStrokeColor("#" + busRoute.getRouteTextColor());
        busTextView.setSolidColor("#" + busRoute.getRouteColor());

        return view;
    }
}
