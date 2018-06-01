package org.fhict.fontys.vider.Utilities;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Maarten on 1-6-2018.
 */

public class DatabaseReference {

    private static com.google.firebase.database.DatabaseReference database;

    public DatabaseReference()
    {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static com.google.firebase.database.DatabaseReference getDatabase() {
        return database;
    }
}
