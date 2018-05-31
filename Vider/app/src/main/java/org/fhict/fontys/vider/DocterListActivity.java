package org.fhict.fontys.vider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;

import org.fhict.fontys.vider.Models.User;

public class DocterListActivity extends AppCompatActivity {
    private FirebaseListAdapter<User> doctersAdaper;
    private ListView doctersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_list);
        doctersList = findViewById(R.id.DoctersListView);
        getAllDocters();

    }

    private void getAllDocters(){
        //The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("Messages"), ChatMessage.class)
                .setLayout(R.layout.user)
                .build();

        doctersAdaper = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(View v, User user, int position) {
                // Get references to the views of message.xml
                TextView username = (TextView)v.findViewById(R.id.user_username);
                TextView woonplaats = (TextView)v.findViewById(R.id.user_woonplaats);

                // Set the parameters
                username.setText(user.getName());
                woonplaats.setText(user.getWoonplaats());
            }
        };
        doctersList.setAdapter(doctersAdaper);
        doctersAdaper.startListening();
    }
}
