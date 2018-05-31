package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.fhict.fontys.vider.Models.User;

import java.util.List;

/**
 * @javadoc Rik van Spreuwel
 */
public class DocterListActivity extends AppCompatActivity {
    private FirebaseListAdapter<User> doctersAdaper;
    private ListView doctersList;

    /**
     * In the oncreate of this activity we do several things:
     *  - We find de doctersList view so we can add things to the list
     *  - We call the getAllDocters method to populate the list with docters
     *  - We set an on click listener to the list, so that when you click on a docter
     *  you open a chat with that docter
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_list);
        doctersList = findViewById(R.id.DoctersListView);
        getAllDocters();

        doctersList.setOnItemClickListener((parent, view, position, id) -> {
            //Get the name from the array that is in the same position as the chosen listitem.
            Intent intent = new Intent(view.getContext(), DocterChatActivity.class);
            intent.putExtra("docterUid", doctersAdaper.getItem(position).getUid());
            startActivity(intent);
        });
    }

    /**
     * This method is made to populate the listview with docters
     *
     * In this method we first make an FirebaseListOptions, to populate our list with
     * the certain layout of the user.xml (see setLayout() method). This also sets the query of where
     * to get the docters from.
     *
     * After that we define our FirebaseListAdapter, which will populate our user.xml view with the username and woonplaats
     * of the user.
     */
    private void getAllDocters(){
        //The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("woonplaats"), User.class)
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
