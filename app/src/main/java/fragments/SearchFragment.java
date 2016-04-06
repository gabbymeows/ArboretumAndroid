package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import seniorproject.arboretumapp.R;

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
                // Listview Data
                String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                        "iPhone 4S", "Samsung Galaxy Note 800",
                        "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

                lv = (ListView) rootView.findViewById(R.id.search_view);
                inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

                // Adding items to listview
                //adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, products);
                adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.product_name, products);
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

            return rootView;
        }
}

