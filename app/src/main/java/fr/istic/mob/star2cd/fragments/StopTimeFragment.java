package fr.istic.mob.star2cd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Stop Time Fragment
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class StopTimeFragment extends Fragment {

    private StopTimeFragmentListener fragmentListener;

    private StopTimeFragment() {
    }

    public static StopTimeFragment newInstance() {
        return new StopTimeFragment();
    }

    public interface StopTimeFragmentListener {
        void foo();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof BusRouteFragment.BusRouteFragmentListener) {
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
