package org.fhict.fontys.vider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import org.fhict.fontys.vider.Utilities.AuthenticationReference;
import org.fhict.fontys.vider.Utilities.SimpleDialog;


public class LoginActivity extends AppCompatActivity {

    private EditText tbEmail;
    private EditText tbPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.tbEmail = findViewById(R.id.tbLoginEmail);
        this.tbPassword = findViewById(R.id.tbLoginPW);

        new AuthenticationReference();
        new org.fhict.fontys.vider.Utilities.DatabaseReference();

        //if user had already been logged in, skip loginscreen and go straight to groupscreen
        if (AuthenticationReference.getAuth().getCurrentUser() != null)
        {
            Intent homescreen = new Intent(this,HomeActivity.class);
            startActivity(homescreen);
            finish();
        }
    }

    public void Login(View view) {

        //if fields are empty give dialog
        if (tbEmail.getText().toString().equals("") || tbPassword.getText().toString().equals("")) {
            new SimpleDialog(this,"No Credentials","Please fill in both the required fields");
        }
        //if fields are not empty, try to login
        else if (tbEmail.getText().toString().isEmpty() == false || tbPassword.getText().toString().isEmpty() == false){
            AuthenticationReference.getAuth().signInWithEmailAndPassword(tbEmail.getText().toString(), tbPassword.getText().toString());
            FirebaseUser current = AuthenticationReference.getAuth().getCurrentUser();

            //if login was unnsuccesfully,give message
            if (current == null) {
                new SimpleDialog(this,"Wrong credentials","Please fill in the right credentials");
            } else {
                //return to groupscreen
                Intent homescreen = new Intent(this, HomeActivity.class);
                startActivity(homescreen);
                finish();
            }
        }
    }
}