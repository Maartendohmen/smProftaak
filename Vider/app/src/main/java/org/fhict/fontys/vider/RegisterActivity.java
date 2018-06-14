package org.fhict.fontys.vider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.fhict.fontys.vider.Models.Role;
import org.fhict.fontys.vider.Models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by maxhe on 31-5-2018.
 *
 * @javadoc by Rik van Spreuwel
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtResidence;
    private Button btnRegister;
    private FirebaseAuth mAuth;

   @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);
       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        txtName = findViewById(R.id.txtName);
        txtResidence = findViewById(R.id.txtResidence);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> userToDatabase(txtName.getText().toString(), txtResidence.getText().toString()));
    }


    /**
     * This method saves the user data to the database. After saving the user to the database,
     * it starts the login activity again, which will redirect you to the home page.
     *
     * @param residence of the user to save
     * @param name of the user to save
     */
    private void userToDatabase(String residence, String name){
        if (name.isEmpty() || residence.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(FirebaseAuth.getInstance().getUid(), name, Role.PATIENT, FirebaseAuth.getInstance().getCurrentUser().getEmail(), residence);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Users").child(user.getUid()).setValue(user);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * The onBackPressed() method is made to make sure no account is created without a name and residence.
     * This method shows a alertDialog that gives the user a choice to delete their account or cancel.
     */
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Back");
        builder.setMessage("Are you sure you want to stop registering?");
        builder.setPositiveButton("Stop",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().getCurrentUser().delete();
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Your account has not been made", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
