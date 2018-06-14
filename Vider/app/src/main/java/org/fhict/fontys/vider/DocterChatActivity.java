package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.fhict.fontys.vider.Models.ChatMessage;
import org.fhict.fontys.vider.Models.User;

public class DocterChatActivity extends AppCompatActivity {

    FirebaseListOptions<ChatMessage> sendMessages;
    FirebaseListAdapter<ChatMessage> sendAdapter;
    ListView sendMessagesList;
    FirebaseListOptions<ChatMessage> receivedMessages;
    FirebaseListAdapter<ChatMessage> receivedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_chat);

        Intent intent = getIntent();
        String receiverUID = intent.getStringExtra("docterUid");
        String senderUID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        sendMessagesList = findViewById(R.id.lstSend);
        ImageButton sendButton = findViewById(R.id.sendMessageImagebutton);
        EditText input = (EditText)findViewById(R.id.messageEditText);
        sendButton.setEnabled(false);
        getSendMessages(senderUID);
        sendButton.setOnClickListener(view -> newChatMessage(receiverUID,senderUID,input));
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(input.getText().toString().equals("")){
                    sendButton.setEnabled(false);
                }
                else{
                    sendButton.setEnabled(true);
                }
            }
        });
    }

    private void newChatMessage(String receiverUID, String senderUID, EditText input){

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

    private void getSendMessages(String senderUID){
        sendMessages = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Messages").orderByChild("messageSender").equalTo(senderUID),ChatMessage.class)
                .setLayout(R.layout.message_send)
                .build();

        System.out.println("TAG " + sendMessages.getSnapshots().isEmpty());

        sendAdapter = new FirebaseListAdapter<ChatMessage>(sendMessages) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView message = findViewById(R.id.txtMessageSend);
                message.setText(model.getMessageText());
            }
        };
        sendMessagesList.setAdapter(sendAdapter);
        sendAdapter.startListening();
    }
}
