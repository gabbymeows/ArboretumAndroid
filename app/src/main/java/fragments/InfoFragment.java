package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import seniorproject.arboretumapp.MainActivity;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 2/9/2016.
 */
public class InfoFragment extends Fragment {

    TextView messageText;
    View rootView;

    public InfoFragment(){

    }

    @Override
    public void onSaveInstanceState(final Bundle state){
        super.onSaveInstanceState(state);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.info_fragment_view, container, false);
        messageText = (TextView) rootView.findViewById(R.id.message);

        Firebase ref = MainActivity.getRef();

        ref.child("app").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String message = "";
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    message = (String) postSnapshot.getValue();
                }

                messageText.setText(message);
                rootView.refreshDrawableState();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        return rootView;
    }
}
