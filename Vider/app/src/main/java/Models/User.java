package Models;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by maxhe on 25-5-2018.
 */

public class User {
    private GoogleSignInAccount account;

    public User(GoogleSignInAccount account) {
        this.account = account;
    }
}
