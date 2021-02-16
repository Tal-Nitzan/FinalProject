package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class Activity_AddDelivery extends AppCompatActivity {

    private DatabaseReference mDatabase; //test

    EditText editText;
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //test

        editText = findViewById(R.id.edit_text);
        textView1 = findViewById(R.id.text_view);
        textView2 = findViewById(R.id.text_view2);

        Places.initialize(getApplicationContext(), "AIzaSyCMOY2aSN3GOhaEEJZa0hpLuZlCCxRllDQ");


        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(Activity_AddDelivery.this);
                startActivityForResult(intent, 100);

                mDatabase.child("users").child("UserID").child("Locations").push().setValue("A Point"); //test
                mDatabase.child("users").child("UserID").child("Locations").push().setValue("Another Point"); //test
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
           Place place = Autocomplete.getPlaceFromIntent(data);
           editText.setText(place.getAddress());
           textView1.setText(String.format("Locality Name : %s", place.getName()));
           textView2.setText(String.valueOf(place.getLatLng()));
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(),Toast.LENGTH_LONG).show();
        }
    }
}