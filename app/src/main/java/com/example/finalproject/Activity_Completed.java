package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

public class Activity_Completed extends AppCompatActivity {

    private DatabaseReference mDatabase; //test

    private DrawerLayout drawerLayout;
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private RecyclerView history_LST_deliveries;
    private TextView history_LBL_noDeliveries;
    static int numOfCompletedDeliveries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.removeStatusBar(this);

        setContentView(R.layout.activity_completed);
        history_LST_deliveries = findViewById(R.id.history_LST_deliveries);
        history_LBL_noDeliveries = findViewById(R.id.history_LBL_noDeliveries);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        drawerLayout = findViewById(R.id.drawer_layout);
        fetchDeliveriesFromDB(this, deliveries);
    }

    private void fetchDeliveriesFromDB(Context context, ArrayList<Delivery> deliveries) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid).child(Utils.databaseStates[1]);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveries.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Delivery delivery = postSnapshot.getValue(Delivery.class);
                    delivery.setId(postSnapshot.getKey());
                    if (delivery.getState() == STATE.COMPLETED) {
                        numOfCompletedDeliveries++;
                        deliveries.add(delivery);
                        Adapter_Delivery adapter_delivery = new Adapter_Delivery(context, deliveries);
                        history_LST_deliveries.setLayoutManager(new LinearLayoutManager(context));
                        history_LST_deliveries.setAdapter(adapter_delivery);
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
            history_LBL_noDeliveries.setVisibility(View.VISIBLE);
        } else {
            history_LBL_noDeliveries.setVisibility(View.INVISIBLE);
        }
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
        DrawerUtils.closeDrawer(drawerLayout);
    }

    public void ClickCanceled(View view) {
        Utils.redirectActivity(this, Activity_Cancelled.class);
    }


    public void ClickPersonalDetails(View view) {
        Utils.redirectActivity(this, Activity_PersonalDetails.class);
    }

    public void ClickLogOff(View view) {
        Log.d("pttt", "got here2");
        Utils.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DrawerUtils.closeDrawer(drawerLayout);
    }
}