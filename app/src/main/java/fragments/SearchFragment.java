package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.PlantMap;
import seniorproject.arboretumapp.R;
import views.PlantDetails;

public class SearchFragment extends Fragment {
        // List view
        private ListView lv;

        // Listview Adapter
        ArrayAdapter<String> adapter;

        // Search EditText
        EditText inputSearch;


        // ArrayList for Listview
        ArrayList<HashMap<String, String>> productList;

        public SearchFragment(){

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
                View rootView = inflater.inflate(R.layout.search_view, container, false);

                lv = (ListView) rootView.findViewById(R.id.search_view);
                inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
                HashMap<String, String> displayNames = PlantMap.getInstance().getNameToCodeMap();


            List<String> names = PlantMap.getInstance().getDisplayNamesList();
            StringBuffer sb = new StringBuffer();
            for(String n : names){
                sb.append(n+"\n");
            }

            Log.v("gabby", sb.toString());

                // Adding items to listview
                //adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, products);
                adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.sci_name, PlantMap.getInstance().getDisplayNamesList());
                //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.com_name, PlantMap.getInstance().getComName());


                lv.setAdapter(adapter);
                inputSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        SearchFragment.this.adapter.getFilter().filter(cs);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                    }
                });
            lv.setOnItemClickListener(onListClick);

            return rootView;
        }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String key = parent.getAdapter().getItem(position).toString();
            HashMap<String, String> plantMap = PlantMap.getInstance().getNameToCodeMap();

            PlantDetails.getDialog(null, plantMap.get(key), parent.getContext(), view).show();

        }
    };
}

