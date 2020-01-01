package fr.istic.mob.star2cd.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.utils.CircularTextView;
import fr.istic.mob.star2cd.utils.StarContract;
import fr.istic.mob.star2cd.utils.StopAdapter;

public class StopFragment extends Fragment {

    private CircularTextView busTextView;
    private ListView list;
    private Cursor cursor;
    private BusRoute busRoute;
    private int direction;

    public StopFragment() {
    }

    private StopFragment(BusRoute busRoute, int direction) {
        this.busRoute = busRoute;
        this.direction = direction;
    }

    public static StopFragment newInstance(BusRoute busRoute, int direction) {
        return new StopFragment(busRoute, direction);
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
        list = view.findViewById(R.id.list);

        String str[] = new String[2];
        str[0] = "1";
        str[1] = "0";

        cursor = getContext().getContentResolver().query(
                StarContract.Stops.CONTENT_URI,
                null, null, str, null);

        final StopAdapter stopAdapter = new StopAdapter(getContext(), cursor);
        list.setAdapter(stopAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), " " + stopAdapter.getItem(i).getStopName(), Toast.LENGTH_SHORT).show();
            }
        });


        busTextView = (CircularTextView) view.findViewById(R.id.circularTextView);
        busTextView.setStrokeWidth(1);
        busTextView.setStrokeColor("#ffffff");
        busTextView.setSolidColor("#ADFF2F");

        return view;
    }
}
