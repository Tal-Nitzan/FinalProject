package com.example.finalproject;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private TextView listDeliveries_LBL_noDeliveries;
    ArrayList<Delivery> deliveries = new ArrayList<Delivery>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_deliveries);

        drawerLayout = findViewById(R.id.drawer_layout);
        listDeliveries_LBL_noDeliveries = findViewById(R.id.listDeliveries_LBL_noDeliveries);
        listDeliveries_LST_deliveries = findViewById(R.id.listDeliveries_LST_deliveries);
//        listDeliveries_LBL_noDeliveries.setVisibility(View.INVISIBLE);
        // get deliveries from DB TODO
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
                    delivery.setId(postSnapshot.getKey());
                    deliveries.add(delivery);
                    Adapter_Delivery adapter_delivery = new Adapter_Delivery(context, deliveries);
                    adapter_delivery.setClickListener(new Adapter_Delivery.MyItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Remove Delivery");

                            builder.setMessage("Are you sure you want to remove the delivery to " + delivery.getAddress() + "?");

                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myRef.child(deliveries.get(position).getId()).removeValue();
                                    deliveries.remove(position);
                                    adapter_delivery.notifyDataSetChanged();
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();
                        }

                    });


                    // TODO add photo if there are no deliveries

                    listDeliveries_LST_deliveries.setLayoutManager(new LinearLayoutManager(context));
                    listDeliveries_LST_deliveries.setAdapter(adapter_delivery);

                }
                setNoDeliveries();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("cccc", "Failed to read value.", error.toException());
            }
        });
    }

    private void setNoDeliveries() {
        if (deliveries.size() == 0) {
            listDeliveries_LBL_noDeliveries.setVisibility(View.VISIBLE);
        } else {
            listDeliveries_LBL_noDeliveries.setVisibility(View.INVISIBLE);
        }
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