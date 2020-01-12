package fr.istic.mob.star2cd.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.utils.BusRoutesAdapter;
import fr.istic.mob.star2cd.utils.StarContract;
import fr.istic.mob.star2cd.utils.StarFactory;
import fr.istic.mob.star2cd.utils.StarUtility;

/**
 * Bus Route Fragment
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class BusRouteFragment extends Fragment {

    private Spinner spinnerBusLine, spinnerBusDirection;
    private EditText dateEditText, timeEditText;
    private BusRouteFragmentListener fragmentListener;
    private int routeId, direction;
    private Context mContext;

    private void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    private void setDirection(int direction) {
        this.direction = direction;
    }

    public interface BusRouteFragmentListener {

        /**
         * Triggered after a click on Search button
         *
         * @param routeId   route identifier
         * @param direction direction
         */
        void searchOnClick(int routeId, int direction);
    }

    /**
     * Static factory
     *
     * @return new instance of BusRouteFragment
     */
    public static BusRouteFragment newInstance() {
        return new BusRouteFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (getActivity() instanceof BusRouteFragmentListener) {
            fragmentListener = (BusRouteFragmentListener) getActivity();
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
        View view = inflater.inflate(R.layout.fragment_busroute, container, false);

        this.spinnerBusLine = view.findViewById(R.id.spinnerBusLine);
        this.spinnerBusDirection = view.findViewById(R.id.spinnerBusDirection);
        Button dateButton = view.findViewById(R.id.dateButton);
        Button timeButton = view.findViewById(R.id.timeButton);
        Button searchButton = view.findViewById(R.id.searchButton);
        this.dateEditText = view.findViewById(R.id.dateEditText);
        this.timeEditText = view.findViewById(R.id.timeEditText);

        timeEditText.setFocusable(false);
        dateEditText.setFocusable(false);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentListener != null) {
                    if (!dateEditText.getText().toString().equals("") && !timeEditText.getText().toString().equals("")) {
                        String date = StarUtility.convertDateToDB(dateEditText.getText().toString());
                        StarUtility.storeInSharedPrefs(mContext, "date", date);
                        StarUtility.storeInSharedPrefs(mContext, "time", timeEditText.getText().toString());
                        fragmentListener.searchOnClick(routeId, direction);
                    } else {
                        Toast.makeText(mContext, getString(R.string.empty_inputs_message), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initSpinnerBusLine();

        return view;
    }

    /**
     * Handle click on date button
     * Source : https://github.com/trulymittal/DateTimePicker
     */
    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("dd/MM/yyyy", calendar1).toString();
                dateEditText.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    /**
     * Handle click on time button
     */
    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(getContext());

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i("HandleTimeButton", "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("hh:mm:00", calendar1).toString(); // default format : "h:mm a"
                timeEditText.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }

    /**
     * Initialize the spinner with all possible bus routes
     */
    private void initSpinnerBusLine() {
        ArrayList<String> busRoutesStr = new ArrayList<>();
        ArrayList<String> busRoutesColors = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(
                StarContract.BusRoutes.CONTENT_URI, null, null, null, null);

        Objects.requireNonNull(cursor);

        final List<BusRoute> busRoutes = StarFactory.getAllBusRoutes(mContext);

        for (BusRoute busRoute : busRoutes) {
            busRoutesStr.add(busRoute.getRouteShortName());
            busRoutesColors.add("#" + busRoute.getRouteColor());
        }

        final ArrayAdapter<String> adapter = new BusRoutesAdapter(
                getContext(), R.layout.busroute_spinner_item,
                busRoutesStr, busRoutesColors);
        spinnerBusLine.setAdapter(adapter);

        spinnerBusLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int busRouteId = busRoutes.get(position).getId();
                initSpinnerBusDirection(busRouteId);
                setRouteId(busRouteId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Initialize the spinner with the possible directions for this route
     *
     * @param routeId Route identifier
     */
    private void initSpinnerBusDirection(final int routeId) {
        this.spinnerBusDirection.setVisibility(View.VISIBLE);
        String longName = "";
        try {
            longName = StarFactory.getBusRoute(mContext, routeId).getRouteLongName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splits = longName.split(" <> ");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(splits[0]);
        arrayList.add(splits[splits.length - 1]);
        Objects.requireNonNull(getContext());
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);

        spinnerBusDirection.setAdapter(arrayAdapter);

        spinnerBusDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDirection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
