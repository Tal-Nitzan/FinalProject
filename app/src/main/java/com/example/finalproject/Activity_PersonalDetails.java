package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_PersonalDetails extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView personalDetails_LBL_gmail;
    private TextView personalDetails_LBL_activeDeliveries;
    private TextView personalDetails_LBL_completedDeliveries;
    private TextView personalDetails_LBL_canceledDeliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.removeStatusBar(this);

        setContentView(R.layout.activity_personal_details);

        findViews();
        initViews();
    }

    private void findViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        personalDetails_LBL_gmail = findViewById(R.id.personalDetails_LBL_gmail);
        personalDetails_LBL_activeDeliveries = findViewById(R.id.personalDetails_LBL_activeDeliveries);
        personalDetails_LBL_completedDeliveries = findViewById(R.id.personalDetails_LBL_completedDeliveries);
        personalDetails_LBL_canceledDeliveries = findViewById(R.id.personalDetails_LBL_canceledDeliveries);
    }

    private void initViews() {
        personalDetails_LBL_gmail.setText(MainActivity.currentUser.getGmailAddress());
        personalDetails_LBL_activeDeliveries.setText(String.valueOf(MainActivity.currentUser.getNumOfActiveDeliveries()));
        personalDetails_LBL_completedDeliveries.setText(String.valueOf(MainActivity.currentUser.getNumOfCompletedDeliveries()));
        personalDetails_LBL_canceledDeliveries.setText(String.valueOf(MainActivity.currentUser.getNumOfCancelledDeliveries()));
    }

    public void ClickMenu(View view) {

        DrawerUtils.openDrawer(drawerLayout);

    }

    public void ClickLogo(View view) {
        DrawerUtils.closeDrawer(drawerLayout);
    }

    public void ClickMap(View view) {
        Utils.redirectActivity(this, MainActivity.class);
    }


    public void ClickList(View view) {

        Utils.redirectActivity(this, Activity_ListDeliveries.class);
    }



    public void ClickCompleted(View view) {

        Utils.redirectActivity(this, Activity_Completed.class);
    }

    public void ClickCanceled(View view) {
        Utils.redirectActivity(this, Activity_Cancelled.class);
    }


    public void ClickPersonalDetails(View view) {
        DrawerUtils.closeDrawer(drawerLayout);
    }

    public void ClickLogOff(View view) {
        Utils.logout(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        DrawerUtils.closeDrawer(drawerLayout);
    }
}