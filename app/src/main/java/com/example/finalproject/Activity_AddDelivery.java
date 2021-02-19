package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Activity_AddDelivery extends AppCompatActivity {

    private DatabaseReference mDatabase; //test

    EditText editText;
    Button addDelivery_BTN_submit;
    private TextInputLayout addDelivery_EDT_inputName;
    private TextInputLayout addDelivery_EDT_inputPhone;
    private TextInputLayout addDelivery_EDT_inputWeight;

    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //test

        findViews();
        initViews();


        Places.initialize(getApplicationContext(), "AIzaSyDuooLeJ259V3F_V4Eh9Z-qFK169XFqXI8");

    }



    private void findViews() {
        editText = findViewById(R.id.edit_text);
        addDelivery_BTN_submit = findViewById(R.id.addDelivery_BTN_submit);
        addDelivery_EDT_inputName = findViewById(R.id.addDelivery_EDT_inputName);
        addDelivery_EDT_inputPhone = findViewById(R.id.addDelivery_EDT_inputPhone);
        addDelivery_EDT_inputWeight = findViewById(R.id.addDelivery_EDT_inputWeight);
    }

    private void initViews() {

        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(Activity_AddDelivery.this);
                startActivityForResult(intent, 100);
            }
        });

        addDelivery_BTN_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitClicked();
            }
        });
    }


    private void submitClicked() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = firebaseUser.getUid();

        String phoneNumber = addDelivery_EDT_inputPhone.getEditText().getText().toString();
        String receiverName = addDelivery_EDT_inputName.getEditText().getText().toString();
        float weight = Float.parseFloat(addDelivery_EDT_inputWeight.getEditText().getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date todayDate = new Date();
        String todayDateString = sdf.format(todayDate);
        Delivery delivery = new Delivery()
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .setReceiverName(receiverName)
                .setWeight(weight)
                .setDeliveryDateString(todayDateString)
                .setDeliveryDate(todayDate)
                .setState(STATE.PENDING);
        mDatabase.child("users").child(uid).child(Utils.databaseStates[0]).push().setValue(delivery);
        Intent intent = new Intent(Activity_AddDelivery.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
           Place place = Autocomplete.getPlaceFromIntent(data);
           editText.setText(place.getAddress());
           address = place.getAddress();
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(),Toast.LENGTH_LONG).show();
        }
    }
}