package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
        View rootView = inflater.inflate(R.layout.home_fragment2, container, false);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy");
        String strDate = fmt.format(calendar.getTime());

        WebView webView = (WebView) rootView.findViewById(R.id.webViewCalendar);
        webView.getSettings().setJavaScriptEnabled(true);
        String iframe = "<iframe src=\"https://calendar.google.com/calendar/embed?src=arborapp16%40gmail.com&ctz=America/Chicago\" style=\"border: 0\" width=\"340\" height=\"340\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        String iframe2 = "<iframe src=\"https://calendar.google.com/calendar/embed?title=Events&amp;height=600&amp;wkst=1&amp;bgcolor=%23FFFFFF&amp;src=arborapp16%40gmail.com&amp;color=%231B887A&amp;ctz=America%2FChicago\" style=\"border-width:0\" width=\"350\" height=\"319\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        webView.loadData(iframe, "text/html", "utf-8");

        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new SimpleDateFormat("dd/ MM/ yyyy"));
        //TextView date = (TextView) rootView.findViewById(R.id.textView8);
        //date.setText(strDate);



        return rootView;
    }
}
