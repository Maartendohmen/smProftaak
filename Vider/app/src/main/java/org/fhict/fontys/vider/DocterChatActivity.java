package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
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
import java.util.List;

public class DocterChatActivity extends AppCompatActivity {

    private FirebaseListOptions<ChatMessage> sendAdapterOptions;
    private FirebaseListAdapter<ChatMessage> sendAdapter;
    private ListView messagesList;
    private FirebaseListOptions<ChatMessage> receivedAdapterOptions;
    private FirebaseListAdapter<ChatMessage> receivedAdapter;
    private List<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_chat);

        Intent intent = getIntent();
        String receiverUID = intent.getStringExtra("docterUid");
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        messageList = new ArrayList<>();
        messagesList = findViewById(R.id.list_of_messages);
        ImageButton sendButton = findViewById(R.id.btnSend);
        EditText input = (EditText) findViewById(R.id.input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);

        sendButton.setEnabled(false);
        getMessages(userUID);
        sendButton.setOnClickListener(view -> newChatMessage(receiverUID, userUID, input));
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
                .push()
                .setValue(new ChatMessage(input.getText().toString(),
                        receiverUID,
                        senderUID));

        // Clear the input
        input.setText("");
    }

    private void getMessages(String userUID) {
        
        sendAdapterOptions = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Messages").equalTo("messageSender",userUID)
                        .equalTo("messageReceiver",userUID),ChatMessage.class)
                .setLayout(R.layout.message_send)
                .build();


        sendAdapter = new FirebaseListAdapter<ChatMessage>(sendAdapterOptions) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView message = (TextView)v.findViewById(R.id.message_text);
                TextView time = (TextView)v.findViewById(R.id.message_time);
                TextView user = (TextView)v.findViewById(R.id.message_user);

                user.setText(model.getMessageSender());
                message.setText(model.getMessageText());
                time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }
        };
        messagesList.setAdapter(sendAdapter);
        sendAdapter.startListening();
    }
}

