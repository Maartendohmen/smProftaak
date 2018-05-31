package org.fhict.fontys.vider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseListAdapter;

import org.fhict.fontys.vider.Models.User;

import java.util.List;

public class DocterListActivity extends AppCompatActivity {
    private FirebaseListAdapter<User> doctersAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_list);
    }
}
