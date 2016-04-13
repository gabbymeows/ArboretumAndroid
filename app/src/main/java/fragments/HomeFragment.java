package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import models.Announcement;
import models.FormatTimestamp;
import models.PlantMap;
import seniorproject.arboretumapp.MainActivity;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 2/4/2016.
 */
public class HomeFragment extends Fragment {

    ListView lv;
    ArrayAdapter<String> adapter;
    List<String> announcementList;
    View rootView;

    public HomeFragment() {
    }

    @Override
    public void onSaveInstanceState(final Bundle state){
        super.onSaveInstanceState(state);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.announcement_view, container, false);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy");
        String strDate = fmt.format(calendar.getTime());

        WebView webView = (WebView) rootView.findViewById(R.id.webViewCalendar);
        webView.getSettings().setJavaScriptEnabled(true);
        String iframe = "<iframe src=\"https://calendar.google.com/calendar/embed?src=arborapp16%40gmail.com&ctz=America/Chicago\" style=\"border: 0\" width=\"350\" height=\"280\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        webView.loadData(iframe, "text/html", "utf-8");

        TextView announcements = (TextView) rootView.findViewById(R.id.a1);
        this.announcementList = new ArrayList<String>();


//        Firebase.setAndroidContext(this.getContext());
//        Firebase ref = new Firebase("https://arboretum-admin-dash.firebaseio.com/");

        Firebase ref = MainActivity.getRef();




        ref.child("announcements").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.v("yoo", "There are " + snapshot.getChildrenCount() + " announcements");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Announcement ann = postSnapshot.getValue(Announcement.class);
                    Log.v("yoo", ann.getMessage());
                    Log.v("yoo", ann.getTimestamp().toString());
                    getAnns().add(FormatTimestamp.getDisplayDate(ann.getTimestamp().toString()) + ann.getMessage() + "      ");
                    //announcementList.concat(ann.getTimestamp().toString().substring(0,10)+": "+ann.getMessage()+"\n");
                    //System.out.println(post.getAuthor() + " - " + post.getTitle());


                }
                Collections.reverse(getAnns());

                if(adapter != null) {
                    lv.setAdapter(adapter);
                    rootView.refreshDrawableState();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        //getAnns().add("testtestst");
        //Log.v("yoo", announcementList.get(0));



        lv = (ListView) rootView.findViewById(R.id.annList);
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.sci_name, announcementList);
        lv.setAdapter(adapter);

        //announcements.setText(announcementList);

        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new SimpleDateFormat("dd/ MM/ yyyy"));
        //TextView date = (TextView) rootView.findViewById(R.id.textView8);
        //date.setText(strDate);



        return rootView;
    }

    public List<String> getAnns(){
        return this.announcementList;
    }
}
