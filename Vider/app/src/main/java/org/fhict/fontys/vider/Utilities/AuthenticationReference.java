package org.fhict.fontys.vider.Utilities;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Maarten on 1-6-2018.
 */

public class AuthenticationReference {
    private static FirebaseAuth Auth;

    public AuthenticationReference(){Auth = FirebaseAuth.getInstance();}

    public static FirebaseAuth getAuth() {
        return Auth;
    }
}
