package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {






    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    public static String activityType = "";
    // Used to see what page the person is on, here are all of the following options
    // ""
    // "Academic Achievements"
    // "Athletic Achievements"
    // "Clubs and Organizations Achievements"
    // "Community Achievements"
    // "Honors Achievements"
    // "Performing Arts Achievements"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        System.out.println("making sure");
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.academic, R.id.athletics,R.id.clubs,R.id.community,R.id.performing)
                .setOpenableLayout(drawer)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    //Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.nav_home);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.academic) {
                    //Toast.makeText(MainActivity.this, "Academic Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.academic);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.athletics) {
                    //Toast.makeText(MainActivity.this, "Athletics Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.athletics);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.performing) {
                    //Toast.makeText(MainActivity.this, "Performing Arts Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.performing);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.clubs) {
                    //Toast.makeText(MainActivity.this, "Clubs Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.clubs);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.community) {
                    //Toast.makeText(MainActivity.this, "Community Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.community);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.honors) {
                    //Toast.makeText(MainActivity.this, "Honors Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.honors);
                    drawer.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.share) {
                    //Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.share);
                    drawer.closeDrawers();
                    return true;
                }
                return false;
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // buttons on click listener


        // Create a new user with a first and last name
        /* Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
        System.out.println("hello");

// Add a new document with a generated ID
        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                });

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for  (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle item selection

        if (id == R.id.nav_home) {
            // Handle navigation to Home fragment
            navigateToFragment(R.id.nav_home);
            System.out.println("hello done");
            return true;
        } else if (id == R.id.academic) {
            // Handle navigation to Academic fragment
            navigateToFragment(R.id.academic);
            return true;
        } else if (id == R.id.athletics) {
            // Handle navigation to Athletics fragment
            navigateToFragment(R.id.athletics);
            return true;
        } else if (id == R.id.clubs) {
            // Handle navigation to Clubs fragment
            navigateToFragment(R.id.clubs);
            return true;
        } else if (id == R.id.community) {
            // Handle navigation to Community fragment
            navigateToFragment(R.id.community);
            return true;
        } else if (id == R.id.performing) {
            // Handle navigation to Performing fragment
            navigateToFragment(R.id.performing);
            return true;
        } else if (id == R.id.honors) {
            // Handle navigation to Honors fragment
            navigateToFragment(R.id.honors);
            return true;
        } else if (id == R.id.share) {
            // Handle navigation to Share fragment
            navigateToFragment(R.id.share);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void navigateToFragment(int fragmentId) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(fragmentId);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





}