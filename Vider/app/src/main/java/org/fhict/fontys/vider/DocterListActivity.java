package org.fhict.fontys.vider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.fhict.fontys.vider.Models.User;

import java.util.List;

public class DocterListActivity extends AppCompatActivity {
    private FirebaseListAdapter<User> doctersAdaper;
    private ListView doctersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_list);
        doctersList = findViewById(R.id.DoctersListView);
    }

    private void getAllDocters(){
//        //The error said the constructor expected FirebaseListOptions - here you create them:
//        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("Messages"), ChatMessage.class)
//                .setLayout(R.layout.message)
//                .build();
//
//        doctersAdaper = new FirebaseListAdapter<User>(options) {
//            @Override
//            protected void populateView(View v, User model, int position) {
//                // Get references to the views of message.xml
//                TextView messageText = (TextView)v.findViewById(R.id.message_text);
//                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
//
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
//            }
//        };
//        doctersList.setAdapter(doctersAdaper);
//        doctersAdaper.startListening();
    }
}
