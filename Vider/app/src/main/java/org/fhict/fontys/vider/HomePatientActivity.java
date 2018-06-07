package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomePatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);

        Toast.makeText(this, "Welcome patient", Toast.LENGTH_SHORT).show();
    }

    public void chatWithDocter(View view){
        Intent intent = new Intent(this, DocterListActivity.class);
        startActivity(intent);
    }
}
