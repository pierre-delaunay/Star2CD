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
import fr.istic.mob.star2cd.adapters.RouteDetailAdapter;
import fr.istic.mob.star2cd.utils.StarContract;
import fr.istic.mob.star2cd.utils.StarFactory;

/**
 * Route Detail Fragment
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class RouteDetailFragment extends Fragment {

    private StopTime stopTime;
    private int routeId;
    private Context mContext;

    /**
     * Static factory
     *
     * @return new instance of RouteDetailFragment
     */
    public static RouteDetailFragment newInstance(StopTime stopTime, int routeId) {
        return new RouteDetailFragment(stopTime, routeId);
    }

    private RouteDetailFragment(StopTime stopTime, int routeId) {
        this.stopTime = stopTime;
        this.routeId = routeId;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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
        View view = inflater.inflate(R.layout.fragment_routedetail, container, false);
        ListView list = view.findViewById(R.id.list);

        String[] params = {String.valueOf(stopTime.getTripId()), stopTime.getArrivalTime()};

        Log.i("ROUTEDETAIL", "tripId : " + stopTime.getTripId() + " arrivalTime : " + stopTime.getArrivalTime());
        Cursor cursor = mContext.getContentResolver().query(
                StarContract.RouteDetails.CONTENT_URI,
                null, null, params, null);

        final RouteDetailAdapter routeDetailAdapter = new RouteDetailAdapter(mContext, cursor);
        list.setAdapter(routeDetailAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do nothing
            }
        });

        BusRoute busRoute = StarFactory.getBusRoute(getContext(), routeId);
        CircularTextView busTextView = view.findViewById(R.id.routeDetailCTV);
        Objects.requireNonNull(busRoute);
        busTextView.setStrokeWidth(1);
        busTextView.setText(busRoute.getRouteShortName());
        busTextView.setStrokeColor("#" + busRoute.getRouteTextColor());
        busTextView.setSolidColor("#" + busRoute.getRouteColor());

        return view;
    }
}
