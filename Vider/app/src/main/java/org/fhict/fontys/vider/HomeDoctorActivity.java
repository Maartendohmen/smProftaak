package org.fhict.fontys.vider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.fhict.fontys.vider.Utilities.AuthenticationReference;

/**
 * Created by maxhe on 25-5-2018.
 */

public class HomeDoctorActivity extends AppCompatActivity {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        user = AuthenticationReference.getAuth().getCurrentUser();
        Toast.makeText(getBaseContext(),"DOCTOR",Toast.LENGTH_LONG).show();
    }
}
