package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_PersonalDetails extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView personalDetails_LBL_gmail;
    TextView personalDetails_LBL_activeDeliveries;
    TextView personalDetails_LBL_completedDeliveries;
    TextView personalDetails_LBL_canceledDeliveries;

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

        MainActivity.redirectActivity(this, Activity_CompletedDeliveries.class);
    }

    public void ClickCanceled(View view) {
        MainActivity.redirectActivity(this, Activity_Cancelled.class);
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