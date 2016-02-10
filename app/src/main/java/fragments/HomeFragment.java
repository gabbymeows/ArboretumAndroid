package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 2/4/2016.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.home_fragment_view, container, false);

        return rootView;
    }
}
