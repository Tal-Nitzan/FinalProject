package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    static User currentUser;
    private Fragment_Map fragment_map;
    LocationManager locationManager;
    String provider;
    private DrawerLayout drawerLayout;
    private MaterialButton splash_BTN_track;
    private MaterialButton main_btn_addPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
        setUser();
        for (String dataType : Utils.databaseStates) {
            setNumberOfDeliveries(dataType);
        }
        initMap();
    }


    public void findViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        splash_BTN_track = findViewById(R.id.splash_BTN_track);
        main_btn_addPackage = findViewById(R.id.main_btn_addPackage);
    }

    private void initViews() {
        splash_BTN_track.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragment_map.toggleTracking();
            }
        });

        main_btn_addPackage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_AddDelivery.class);
                startActivity(intent);
            }
        });

    }

    void setUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        MainActivity.currentUser = new User()
                .setGmailAddress(firebaseUser.getEmail());
    }

    public static void setNumberOfDeliveries(String dataType) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid).child(dataType);
        switch (dataType) {
            case "pending deliveries":
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MainActivity.currentUser.setNumOfActiveDeliveries((int) dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
                break;
            case "completed deliveries":
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MainActivity.currentUser.setNumOfCompletedDeliveries((int) dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
                break;
            case "cancelled deliveries":
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MainActivity.currentUser.setNumOfCancelledDeliveries((int) dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
                break;
        }

    }


    private void initMap() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        fragment_map = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.records_LAY_map, fragment_map).commit();
    }


    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public void ClickMap(View view) {
        closeDrawer(drawerLayout);
    }

    public void ClickList(View view) {
        redirectActivity(this, Activity_ListDeliveries.class);
    }



    public void ClickHistory(View view) {
        redirectActivity(this, Activity_CompletedDeliveries.class);
    }


    public void ClickCanceled(View view) {
        redirectActivity(this, Activity_Cancelled.class);
    }

    public void ClickPersonalDetails(View view) {
        redirectActivity(this, Activity_PersonalDetails.class);
    }

    public void ClickLogOff(View view) {
        Utils.logout(this);
        Log.d("pttt", "got here");
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}