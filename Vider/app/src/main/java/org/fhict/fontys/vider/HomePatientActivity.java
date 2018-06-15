package org.fhict.fontys.vider;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Toast.makeText(this, "Welcome patient", Toast.LENGTH_SHORT).show();

        ImageView imgMijnKlachten = (ImageView) findViewById(R.id.imageViewHomePatientMijnKlachten);
        ImageView imgMijnKlachtenInsturen = (ImageView) findViewById(R.id.imageViewHomePatientKlachtenInsturen);
        ImageView imgChattenMetDokter = (ImageView) findViewById(R.id.imageViewHomePatientChattenMetDocter);
        ImageView imgMedicijnenBestellen = (ImageView) findViewById(R.id.imageViewHomePatientMedicijnenBestellen);
        ImageView imgInformatieOverKlachten = (ImageView) findViewById(R.id.imageViewHomePatientInformatieOverKlachten);
        imgMijnKlachten.setOnClickListener(v -> {
            System.out.println("Mijn klachten");
            Intent intent = new Intent(getBaseContext(), MyComplainsActivity.class);
            startActivity(intent);
        });
        imgMijnKlachtenInsturen.setOnClickListener(v -> {
            System.out.println("Mijn klachten insturen");
            Intent intent = new Intent(getBaseContext(), SendComplainActivity.class);
            startActivity(intent);
        });
        imgChattenMetDokter.setOnClickListener(v -> {
            System.out.println("Chatten met dokter");
            Intent intent = new Intent(getBaseContext(), DocterListActivity.class);
            startActivity(intent);
        });
        imgMedicijnenBestellen.setOnClickListener(v -> {
            System.out.println("Medicijnen bestellen");
            // TODO: add intent

        });

        imgInformatieOverKlachten.setOnClickListener(v -> {
            System.out.println("Informatie over kalchten");
            Intent intent = new Intent(getBaseContext(), InformationActivity.class);
            startActivity(intent);
        });



    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Succesfully signed out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You are still signed in", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
