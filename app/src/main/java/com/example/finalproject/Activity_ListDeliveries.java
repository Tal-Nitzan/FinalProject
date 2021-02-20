package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_ListDeliveries extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView listDeliveries_LST_deliveries;
    private TextView listDeliveries_LBL_noDeliveries;
    ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    static int numOfActiveDeliveries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.removeStatusBar(this);

        setContentView(R.layout.activity_list_deliveries);

        drawerLayout = findViewById(R.id.drawer_layout);
        listDeliveries_LBL_noDeliveries = findViewById(R.id.listDeliveries_LBL_noDeliveries);
        listDeliveries_LST_deliveries = findViewById(R.id.listDeliveries_LST_deliveries);
        fetchDeliveriesFromDB(this, deliveries);
    }

    private void fetchDeliveriesFromDB(Context context, ArrayList<Delivery> deliveries) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid).child(Utils.databaseStates[0]);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveries.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Delivery delivery = postSnapshot.getValue(Delivery.class);
                    delivery.setId(postSnapshot.getKey());
                    if (delivery.getState() == STATE.PENDING) {
                        numOfActiveDeliveries++;
                        deliveries.add(delivery);
                        Adapter_Delivery adapter_delivery = new Adapter_Delivery(context, deliveries);
                        adapter_delivery.setClickListener(new Adapter_Delivery.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String[] states = {"Delivered", "Cancelled"};
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Change state of delivery");
                                builder.setItems(states, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delivery.setState(STATE.values()[which + 1]);
                                        DatabaseReference newRef = database.getReference("users").child(uid).child(Utils.databaseStates[which+1]);
                                        newRef.push().setValue(delivery);
                                        myRef.child(deliveries.get(position).getId()).removeValue();
                                        deliveries.remove(position);
                                        numOfActiveDeliveries--;
                                        adapter_delivery.notifyDataSetChanged();
                                    }
                                });
                                builder.show();
                            }

                        });
                        listDeliveries_LST_deliveries.setLayoutManager(new LinearLayoutManager(context));
                        listDeliveries_LST_deliveries.setAdapter(adapter_delivery);
                    }

                }
                setNoDeliveries();
            }

            @Override
            public void onCancelled(DatabaseError error) {
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

        MainActivity.redirectActivity(this, Activity_CompletedDeliveries.class);
    }

    public void ClickCanceled(View view) {
        MainActivity.redirectActivity(this, Activity_Cancelled.class);
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