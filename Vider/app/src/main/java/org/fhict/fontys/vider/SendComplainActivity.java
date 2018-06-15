package org.fhict.fontys.vider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class SendComplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_complain);

        EditText complain = findViewById(R.id.editTextComplain);
        ImageButton imageButton = findViewById(R.id.imageButton);
        Button sendButton = findViewById(R.id.sendButton);

        imageButton.setOnClickListener(v -> {
            System.out.println("Klik imagebutton");
            OpenfotoIntent();
        });
        sendButton.setOnClickListener(v -> {
            System.out.println("Klik sendbutton");
            SendComplain();
        });
    }

    private void OpenfotoIntent() {
    }

    private void SendComplain() {
    }



}
