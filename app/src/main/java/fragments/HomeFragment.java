package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy");
        String strDate = fmt.format(calendar.getTime());

        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new SimpleDateFormat("dd/ MM/ yyyy"));
        TextView date = (TextView) rootView.findViewById(R.id.textView8);
        date.setText(strDate);



        return rootView;
    }
}
