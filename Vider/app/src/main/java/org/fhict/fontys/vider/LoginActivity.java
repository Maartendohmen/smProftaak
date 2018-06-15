package org.fhict.fontys.vider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.fhict.fontys.vider.Models.Role;
import org.fhict.fontys.vider.Models.User;
import org.fhict.fontys.vider.Utilities.AuthenticationReference;
import org.fhict.fontys.vider.Utilities.DatabaseReference;
import org.fhict.fontys.vider.Utilities.SimpleDialog;

/**
 * @javadoc Rik van Spreuwel
 */

public class LoginActivity extends AppCompatActivity {
    private final static int SIGN_IN_REQUEST_CODE = 1;
    private User user;
    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            // Load chat room contents
            new getUser(FirebaseAuth.getInstance().getUid()).execute();
        }
    }

    /**
     * The onActivityResult() method for the login activity.
     * If the result is ok, we get the user data.
     * if the result is not ok, we finish the app.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                new getUser(FirebaseAuth.getInstance().getUid()).execute();
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }


    /**
     * The login method, this calls the firebase authentication to login the user
     *
     * @param view the current view
     */
    public void Login(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            // Load chat room contents
            new getUser(FirebaseAuth.getInstance().getUid()).execute();
        }
    }


    /**
     * The getUser method retrieves the users data from firebase.
     * This method creates a reference to the right user and adds a asynchronic listener (!)
     * which retrieves the user.
     *
     */
    private class getUser extends AsyncTask<Void, Void, Void> {
        private String uid;
        public getUser(String uid){
            this.uid = uid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            hideDialog();
        }

        /**
         * This method gets the user from the database, if the user is null, it means the currentuser has just been created
         * if this is the case, it starts the registerIntent, which will add user data (name and residence)
         * If it is not the case, it gets the user data and redirects to home
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            com.google.firebase.database.DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("CHECK");
                    user = dataSnapshot.getValue(User.class);
                    if (user == null || user.getRole() == null){
                        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(registerIntent);
                        finish();
                        hideDialog();
                    } else {
                        goToHomeScreen();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            reference.addListenerForSingleValueEvent(listener);
            return null;
        }
    }

    /**
     * this method checks wether the user is a Patient or a Docter and
     * sends them to the right activity.
     */
    public void goToHomeScreen(){
        Intent homescreen = null;

        //return to groupscreen
        if(user.getRole().equals(Role.PATIENT)){
            homescreen = new Intent(this,HomePatientActivity.class);
            homescreen.putExtra("currentUser", user);
            hideDialog();
            startActivity(homescreen);
        }

        else if(user.getRole().equals(Role.DOCTER)){
            homescreen = new Intent(this, HomeDocterActivity.class);
            homescreen.putExtra("currentUser", user);
            hideDialog();
            startActivity(homescreen);
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}