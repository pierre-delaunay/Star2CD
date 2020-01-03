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

import fr.istic.mob.star2cd.R;
import fr.istic.mob.star2cd.utils.BusRoutesAdapter;
import fr.istic.mob.star2cd.utils.StarContract;

/**
 * Bus Route Fragment
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class BusRouteFragment extends Fragment {

    // https://developer.android.com/training/basics/fragments/communicating.html

    private Spinner spinnerBusLine, spinnerBusDirection;
    private Button searchButton, dateButton, timeButton;
    private EditText dateEditText, timeEditText;
    private BusRouteFragmentListener fragmentListener;

    public interface BusRouteFragmentListener {
        void searchOnClick();
    }

    public static BusRouteFragment newInstance() {
        return new BusRouteFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        this.dateButton = view.findViewById(R.id.dateButton);
        this.timeButton = view.findViewById(R.id.timeButton);
        this.searchButton = view.findViewById(R.id.searchButton);
        this.dateEditText = view.findViewById(R.id.dateEditText);
        this.timeEditText = view.findViewById(R.id.timeEditText);

        // Hide spinner until a line has been selected by the user
        //spinnerBusDirection.setVisibility(View.GONE);
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
                    fragmentListener.searchOnClick();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
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
        Cursor cursor = getContext().getContentResolver().query(
                StarContract.BusRoutes.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String shortName = cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME));
            String color = cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR));
            busRoutesStr.add(shortName);
            busRoutesColors.add("#" + color);
        }

        final ArrayAdapter<String> adapter = new BusRoutesAdapter(
                getContext(), R.layout.busroute_spinner_item,
                busRoutesStr, busRoutesColors);
        spinnerBusLine.setAdapter(adapter);

        spinnerBusLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                initSpinnerBusDirection(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Initialize the spinner with the possible directions for this route
     *
     * @param routeShortName selected route
     */
    private void initSpinnerBusDirection(final String routeShortName) {
        this.spinnerBusDirection.setVisibility(View.VISIBLE);

        //String shortName = appDatabase.busRouteDao().findRouteLongName(routeShortName);
        String shortName = "TBD";
        String[] splits = shortName.split(" <> ");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(splits[0]);
        arrayList.add(splits[splits.length - 1]);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);

        spinnerBusDirection.setAdapter(arrayAdapter);
    }

    /**
     * After a click on search button
     *
     * @param view View
     */
    public void search(View view) {
        Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_LONG).show();
    }
}
