package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_ListDeliveries extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView listDeliveries_LST_deliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_deliveries);

        drawerLayout = findViewById(R.id.drawer_layout);
        listDeliveries_LST_deliveries = findViewById(R.id.listDeliveries_LST_deliveries);

        // get deliveries from DB TODO
        ArrayList<Delivery> movies = DeliveriesMockDB.generateDeliveries();

        Adapter_Delivery adapter_delivery = new Adapter_Delivery(this, movies);
        adapter_delivery.setClickListener(new Adapter_Delivery.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Activity_ListDeliveries.this, Float.toString(movies.get(position).getWeight()), Toast.LENGTH_SHORT).show();
            }

        });

        listDeliveries_LST_deliveries.setLayoutManager(new LinearLayoutManager(this));
        listDeliveries_LST_deliveries.setAdapter(adapter_delivery);
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
        MainActivity.closeDrawer(drawerLayout);
    }



    public void ClickHistory(View view) {

        MainActivity.redirectActivity(this, Activity_History.class);
    }


    public void ClickPersonalDetails(View view) {
        MainActivity.redirectActivity(this, Activity_PersonalDetails.class);
    }

    public void ClickLogOff(View view) {
        Utils.logout(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}