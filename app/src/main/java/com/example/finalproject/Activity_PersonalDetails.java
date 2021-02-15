package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity_PersonalDetails extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);


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

        MainActivity.redirectActivity(this, Activity_History.class);
    }


    public void ClickPersonalDetails(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickLogOff(View view) {
        Utils.logout(this);
    }

//    public void logout(Activity activity) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("Logout");
//
//        builder.setMessage("Are you sure you want to log out ?");
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(activity, Activity_Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.show();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}