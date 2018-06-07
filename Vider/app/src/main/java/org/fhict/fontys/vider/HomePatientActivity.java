package org.fhict.fontys.vider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by maxhe on 1-6-2018.
 */

public class HomePatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        Toast.makeText(getBaseContext(),"PATIENT",Toast.LENGTH_LONG).show();
    }
}
