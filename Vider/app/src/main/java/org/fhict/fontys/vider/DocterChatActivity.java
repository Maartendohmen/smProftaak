package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.fhict.fontys.vider.Models.ChatMessage;
import org.fhict.fontys.vider.Models.User;
import org.fhict.fontys.vider.Utilities.MessageAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocterChatActivity extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapterMessages;
    private FirebaseListOptions<ChatMessage> adapterOptions;
    private ListView messagesList;
    private ArrayAdapter<ChatMessage> messageArrayAdapter;
    private DatabaseReference reference;
    private MessageAdapter messageAdapter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_chat);

        Intent intent = getIntent();
        String doctorUID = intent.getStringExtra("docterUid");
        currentUser = (User)intent.getSerializableExtra("currentUser");
        FirebaseUser patient = FirebaseAuth.getInstance().getCurrentUser();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        messagesList = findViewById(R.id.list_of_messages);
        ImageButton sendButton = findViewById(R.id.btnSend);
        EditText input = (EditText) findViewById(R.id.input);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        setSupportActionBar(toolbar);

        getMessages(patient.getUid(),doctorUID);
        sendButton.setOnClickListener(view -> newChatMessage(doctorUID, patient, input));
    }

    private void newChatMessage(String receiverUID, FirebaseUser sender, EditText input) {

        if (!input.getText().toString().isEmpty()){
            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .getReference("Messages")
                    .child(sender.getUid()).child(new Date().toString())
                    .setValue(new ChatMessage(input.getText().toString(),
                            receiverUID,
                            sender.getUid(),currentUser.getName()));

            FirebaseDatabase.getInstance()
                    .getReference("Messages")
                    .child(receiverUID ).child(new Date().toString())
                    .setValue(new ChatMessage(input.getText().toString(),
                            receiverUID,
                            sender.getUid(),currentUser.getName()));

            // Clear the input
            input.setText("");
        }
    }

    private void getMessages(String patientUID, String doctorUID) {
        System.out.println("TAG? "+patientUID);

        reference = FirebaseDatabase.getInstance().getReference("Messages").child(patientUID);
        List<ChatMessage> messages = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ChatMessage message = data.getValue(ChatMessage.class);

                    if(message.getMessageSender().equals(patientUID)){
                        message.setMessageSender("U");
                        System.out.println("TAG!! " + message.getMessageSender());
                    }

                    else if(message.getMessageReceiver().equals(doctorUID) || message.getMessageSender().equals(doctorUID)){
                        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Users").child(message.getMessageSender());
                        referenceUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                message.setSenderName(dataSnapshot.child("name").getValue(String.class));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    messages.add(message);
                    messageAdapter.notifyDataSetChanged();
                    messagesList.setSelection(messagesList.getCount()-1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messageAdapter = new MessageAdapter(this, messages);
        messagesList.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

}

