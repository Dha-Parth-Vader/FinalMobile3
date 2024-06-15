package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Googlesignin extends AppCompatActivity {
    private static final int SIGN_IN = 0;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signin;
    private GoogleSignInAccount account;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlesignin);

        signin = findViewById(R.id.sign_in_button);

        signin.setColorScheme(2);
        setGooglePlusButtonText("Sign in with Google");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        String emailAddress;

    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN);
    }

    protected void setGooglePlusButtonText(String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signin.getChildCount(); i++) {
            View v = signin.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            if (account != null) {
                // Signed in successfully, show authenticated UI.
                databaseChecking();
                startActivity(new Intent(Googlesignin.this, MainActivity.class));
                finish(); // Finish the current activity so the user can't return to it.
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
            e.printStackTrace();
        }
    }

    public void databaseChecking() {
        String firstName = account.getGivenName();
        String lastName = account.getFamilyName();
        String displayName = account.getDisplayName();
        String emailAddress = account.getEmail();
        Uri pfp = account.getPhotoUrl();

        Map<String, Object> userDataCollected = new HashMap<>();
        userDataCollected.put("firstName", firstName);
        userDataCollected.put("lastName", lastName);
        userDataCollected.put("displayName", displayName);
        userDataCollected.put("emailAddress", emailAddress);
        userDataCollected.put("profilePicture", pfp);
        System.out.println("hello");

        db.collection("users").document().collection("userInfo").add(userDataCollected);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            startActivity(new Intent(Googlesignin.this, MainActivity.class));
            finish();
        }
    }
}