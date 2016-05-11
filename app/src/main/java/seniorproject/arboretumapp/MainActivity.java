package seniorproject.arboretumapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import fragments.MapFragment;
import fragments.HomeFragment;
import fragments.InfoFragment;
import fragments.PlantGridFragment;
import fragments.SearchFragment;
import models.PlantMap;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static Firebase ref;
    private static LocationManager locationManager;
    private static int radius;
    SeekBar yourDialogSeekBar;
    private static Context mainContext;
    //private Set<String> favoritePlantsList;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(yourDialogSeekBar == null)
            radius = 75;
        else
            radius = yourDialogSeekBar.getProgress()+15;
        setContentView(R.layout.activity_main);
        mainContext = this;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://arboretum-admin-dash.firebaseio.com/");

        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.v("gab", "log in worked");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.v("gab", "log in failed "+firebaseError.getMessage()+" details: "+firebaseError.getDetails()+" code: "+firebaseError.getCode());
            }

        };

        ref.authWithPassword("arborapp16@gmail.com", "awesomeplants16", authResultHandler);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        TextView tabHeader1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_header, null);
        tabHeader1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.calendar_38, 0, 0);

        TextView tabHeader0 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_header, null);
        tabHeader0.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.con_tree_38, 0, 0);

        TextView tabHeader2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_header, null);
        tabHeader2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.marker_38, 0, 0);

        TextView tabHeader3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_header, null);
        tabHeader3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_38, 0, 0);
        TextView tabHeader4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_header, null);
        tabHeader4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info_38, 0, 0);

        //TextView tabHeader5 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_header, null);
        //tabHeader5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.med_info, 0, 0);


        tabLayout.getTabAt(0).setCustomView(tabHeader0);
        tabLayout.getTabAt(1).setCustomView(tabHeader1);
        tabLayout.getTabAt(2).setCustomView(tabHeader2);
        tabLayout.getTabAt(3).setCustomView(tabHeader3);
        tabLayout.getTabAt(4).setCustomView(tabHeader4);

        if(!PlantMap.getInstance().cacheExists()){
            ProgressDialog pd = ProgressDialog.show(MainActivity.this,"Downloading Plant Database", "Downloading...", true, false);
            updateThread searchThread = new updateThread(pd);
            searchThread.start();
        }
        else{
            PlantMap.getInstance().readPlantDataFromFile();
        }


        //stuff for loading saved favorites from file
        Set<String> savedFavs = (Set<String>)loadClassFile(new File(getFilesDir()+"save.bin"));
        if(savedFavs != null){
            PlantMap.getInstance().setFavoritePlantsList(savedFavs);
            PlantMap.getInstance().reloadSavedFavs();
        }


    }



    public static Firebase getRef(){
        return ref;
    }

    public Object loadClassFile(File f){
        try{
            ObjectInputStream ois = new ObjectInputStream((new FileInputStream(f)));
            Object o = ois.readObject();
            return o;
        }
        catch(Exception ex){
            Log.v("Saved fav set", ex.getMessage());
        }
        return null;
    }

    public static LocationManager getLocationManager(){
        return locationManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update_database) {



                AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        ProgressDialog pd = ProgressDialog.show(MainActivity.this,"Update Plant Database", "Updating...", true, false);
                        updateThread searchThread = new updateThread(pd);
                        searchThread.start();
                        //PlantMap.getInstance().updateData();


                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
                Date lastModified = PlantMap.getInstance().getLastUpdated();
                if (lastModified == null){
                    builder.setTitle("Uhhhh... this shouldn't happen");
                }
                builder.setTitle("Update Plant Database Now?\nLast Updated: " + new SimpleDateFormat("MMM d, yyyy").format(lastModified));

// Set other dialog properties


// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            //PlantMap.getInstance().updatePlantData(getBaseContext());
            return true;
        }


        if (id == R.id.seekbar){

            final Dialog yourDialog = new Dialog(this);
            yourDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.radius_slidebar, (ViewGroup)findViewById(R.id.your_dialog_root_element));
            yourDialog.setContentView(layout);

            yourDialogSeekBar = (SeekBar)layout.findViewById(R.id.RADIUSseekBarID);
            yourDialogSeekBar.incrementProgressBy(1);
            yourDialogSeekBar.setMax(185);
            yourDialogSeekBar.setProgress(getRadius() - 15);
            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: " + radius + " feet");
            yourDialogSeekBar.setSaveEnabled(true);
            SeekBar.OnSeekBarChangeListener yourSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //add code here
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
                    ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: "+(progress+15)+" feet");
//
// switch(progress){
//                        case 0:
//                            MainActivity.setRadius(15);
//                            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: 15ft");
//                            break;
//                        case 1:
//                            MainActivity.setRadius(25);
//                            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: 25ft");
//                            break;
//                        case 2:
//                            MainActivity.setRadius(50);
//                            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: 50ft");
//                            break;
//                        case 3:
//                            MainActivity.setRadius(100);
//                            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: 100ft");
//                            break;
//                        case 4:
//                            MainActivity.setRadius(200);
//                            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: 200ft");
//                            break;
//                        default:
//                            MainActivity.setRadius(200);
//                            ((TextView)layout.findViewById(R.id.RADIUStextViewProgressID)).setText("Radius: 200ft");
//                            break;
//
//                    }
                    MainActivity.setRadius(seekBark.getProgress()+15);

                }
            };
            yourDialogSeekBar.setOnSeekBarChangeListener(yourSeekBarListener);

            yourDialog.show();

            Button closeButton = (Button) yourDialog.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yourDialog.dismiss();

                }
            });

        }


//
//        if (id == R.id.update_gridview) {
//
//            PlantMap.getInstance().getNearPlants("40.096237", "-88.217199");
//            System.out.println("UPDAING THIS SHIT!");
//
//        }
//        if (id == R.id.update_gridview2) {
//
//            PlantMap.getInstance().getNearPlants("40.096539", "-88.218136");
//            System.out.println("UPDAING THIS SHIT!");
//
//        }




            return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @Override
    public void onStop() {

        super.onStop();
        Set<String> favs = PlantMap.getInstance().getFavoritePlantsList();
        try{
            ObjectOutputStream oos = new ObjectOutputStream((new FileOutputStream((new File(getFilesDir()+"save.bin")))));
            oos.writeObject(favs);
            oos.flush();
            oos.close();
        }
        catch(FileNotFoundException ex){
            Log.v("Favs Set", ex.getMessage());
        }
        catch(IOException ex){
            Log.v("Favs Set", ex.getMessage());
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if (position == 0){
                //return new HomeFragment();
                return new PlantGridFragment();
            }
            if(position == 1){
                //return new PlantGridFragment();
                return new HomeFragment();
            }

            if (position == 2)
                return new MapFragment();

            if (position == 3)
                return new SearchFragment();

            if (position == 4)
                return new InfoFragment();

            //if (position == 5)
                //return new PlantDetailsFragment();

            return PlaceholderFragment.newInstance(position + 1);
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
                //case 5:
                //    return "SECTION 6";
            }
            return null;
        }
    }


    public static void setRadius(int radius2){
        radius = radius2;
    }

    public static int getRadius(){
        return radius;
    }

    public static Context getMainContext() {return mainContext;}

    private class updateThread extends Thread {

        private ProgressDialog pd;

        public updateThread(ProgressDialog pd2) {
            this.pd = pd2;
        }

        @Override
        public void run() {
            PlantMap.getInstance().updateData();
            handler.sendEmptyMessage(0);
        }

        private Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                pd.dismiss();
            }
        };
    }

}
