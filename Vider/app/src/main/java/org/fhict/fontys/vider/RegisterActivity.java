package org.fhict.fontys.vider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirm;
    private Button btnRegister;
    private FirebaseAuth mAuth;

   @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        txtName = findViewById(R.id.txtName);
        txtResidence = findViewById(R.id.txtResidence);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirm = findViewById(R.id.txtConfirmPW);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> register());
    }


    /**
     * this method registers a new user to the database.
     * First it gets all the data from the data fields and checks if they meet the
     * minimum requirements.
     * Then it registers the new user.
     */
    private void register(){

        String email = txtEmail.getText().toString();
        String password = md5(txtPassword.getText().toString());
        String confirm = md5(txtConfirm.getText().toString());
        String name = txtName.getText().toString();
        String residence = txtResidence.getText().toString();

        if (email.isEmpty() || password.isEmpty() || confirm.isEmpty() || name.isEmpty() || residence.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this,LoginActivity.class);

            if(password.equals(confirm)){
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    System.out.println("TAG create user : succes");
                                    userToDatabase(email, residence, mAuth.getCurrentUser().getUid(), name);
                                    Toast.makeText(getBaseContext(),"Aanmelden is gelukt je kunt nu inloggen",Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                }
                                else{
                                    System.out.println("TAG create user : failed");
                                    Toast.makeText(getBaseContext(),"Aanmelden mislukt",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(getBaseContext(),"Wachtwoordvelden komen niet overeen",Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * This method saves the user data to the database.
     *
     * @param email of the user to save
     * @param residence of the user to save
     * @param uid of the user to save
     * @param name of the user to save
     */
    private void userToDatabase(String email, String residence, String uid, String name){
        User user = new User(uid, name, Role.PATIENT, email, residence);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(user.getUid()).setValue(user);
    }



    /**
     * This method hashes the string to md5
     *
     * @param s String to hash
     * @return returns the hashed string
     */
    public static String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
