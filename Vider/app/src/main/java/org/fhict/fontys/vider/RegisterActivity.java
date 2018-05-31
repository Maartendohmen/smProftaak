package org.fhict.fontys.vider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.fhict.fontys.vider.Models.Role;
import org.fhict.fontys.vider.Models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by maxhe on 31-5-2018.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText txtName;
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
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirm = findViewById(R.id.txtConfirmPW);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> register());
    }

    private void register(){

        String email = txtEmail.getText().toString();
        String password = md5(txtPassword.getText().toString());
        String confirm = md5(txtConfirm.getText().toString());

        if(password.equals(confirm)){
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                System.out.println("TAG create user : succes");
                                userToDatabase(email,mAuth.getCurrentUser().getUid());
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

    private void userToDatabase(String email, String uid){
        User user = new User(uid,email, Role.PATIENT);
    }

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
