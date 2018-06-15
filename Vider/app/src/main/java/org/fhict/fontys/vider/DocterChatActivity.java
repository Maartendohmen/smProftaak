package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.fhict.fontys.vider.Models.ChatMessage;
import org.fhict.fontys.vider.Models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocterChatActivity extends AppCompatActivity {

    private ListView messagesList;
    private List<ChatMessage> messageList;
    private ArrayAdapter<ChatMessage> messageArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_chat);

        Intent intent = getIntent();
        String doctorUID = intent.getStringExtra("docterUid");
        String patientUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        messageList = new ArrayList<>();
        messagesList = findViewById(R.id.list_of_messages);
        ImageButton sendButton = findViewById(R.id.btnSend);
        EditText input = (EditText) findViewById(R.id.input);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        setSupportActionBar(toolbar);

        sendButton.setEnabled(false);
        getMessages(patientUID,doctorUID);
        sendButton.setOnClickListener(view -> newChatMessage(patientUID, doctorUID, input));
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (input.getText().toString().equals("")) {
                    sendButton.setEnabled(false);
                } else {
                    sendButton.setEnabled(true);
                }
            }
        });
    }

    private void newChatMessage(String receiverUID, String senderUID, EditText input) {

        // Read the input field and push a new instance
        // of ChatMessage to the Firebase database
        FirebaseDatabase.getInstance()
                .getReference("Messages")
                .child(senderUID).child(new Date().toString())
                .setValue(new ChatMessage(input.getText().toString(),
                        receiverUID,
                        senderUID));

        FirebaseDatabase.getInstance()
                .getReference("Messages")
                .child(receiverUID ).child(new Date().toString())
                .setValue(new ChatMessage(input.getText().toString(),
                        receiverUID,
                        senderUID));

        // Clear the input
        input.setText("");
    }

    private void getMessages(String patientUID, String doctorUID) {

        messageArrayAdapter = new ArrayAdapter<ChatMessage>(this, R.layout.message_send, messageList);
        messagesList.setAdapter(messageArrayAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        messageArrayAdapter.notifyDataSetChanged();
    }
}

