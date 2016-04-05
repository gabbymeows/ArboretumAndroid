package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import adapters.GridViewAdapter;
import models.GridTile;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 2/9/2016.
 */
public class InfoFragment extends Fragment {

    public InfoFragment(){

    }

    @Override
    public void onSaveInstanceState(final Bundle state){
        super.onSaveInstanceState(state);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.info_fragment_view, container, false);

        return rootView;
    }
}
