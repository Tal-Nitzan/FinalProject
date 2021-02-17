package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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
        ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
//        ArrayList<Delivery> deliveries = DeliveriesMockDB.generateDeliveries();

        fetchDeliveriesFromDB(this, deliveries);
    }

    private void fetchDeliveriesFromDB(Context context, ArrayList<Delivery> deliveries) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid).child("deliveries");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveries.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Delivery delivery = postSnapshot.getValue(Delivery.class);
                    deliveries.add(delivery);
                    Log.d("cccc", "Delivery: " + delivery.getAddress() + " " + delivery.getPhoneNumber());
                    Adapter_Delivery adapter_delivery = new Adapter_Delivery(context, deliveries);
                    adapter_delivery.setClickListener(new Adapter_Delivery.MyItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(Activity_ListDeliveries.this, Float.toString(deliveries.get(position).getWeight()), Toast.LENGTH_SHORT).show();
                        }

                    });

                    // TODO add photo if there are no deliveries

                    listDeliveries_LST_deliveries.setLayoutManager(new LinearLayoutManager(context));
                    listDeliveries_LST_deliveries.setAdapter(adapter_delivery);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("cccc", "Failed to read value.", error.toException());
            }
        });
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