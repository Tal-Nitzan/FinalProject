package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_History extends AppCompatActivity {

    private DatabaseReference mDatabase; //test

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //test

        mDatabase.child("users").child("UserID").child("Locations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String string = snapshot.getValue(String.class); //test
                Log.d("pttt", string); //test
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
    }


    public void ClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickMap(View view) {
        MainActivity.redirectActivity(this, MainActivity.class);
    }


    public void ClickList(View view) {

        MainActivity.redirectActivity(this, Activity_ListDeliveries.class);
    }



    public void ClickHistory(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }


    public void ClickPersonalDetails(View view) {
        MainActivity.redirectActivity(this, Activity_PersonalDetails.class);
    }

    public void ClickLogOff(View view) {
        Log.d("pttt", "got here2");
        Utils.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}