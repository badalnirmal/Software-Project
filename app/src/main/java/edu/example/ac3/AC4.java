package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class AC4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c4);
        final TextView try_text = findViewById(R.id.textView3);
        //Starting the Fused location object to get the user location
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(AC4.this);
        //Check if user is allowed the location access, if not get location access from the user
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) AC4.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        //Once permissions are set, the app is able to get last known location that any other apps have accessed from the device or any last known recorded location of the app
        // if there is no last known location the app crashes and returns null
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            // use this location to call the get places api
                        }
                        else
                        {
                            //here create a toast token if the location cannot be located
                        }

                    }
                });

        //Getting the curr_from and curr_to strings that user has selected from the previous activity
        Intent intent= getIntent();
        Bank bank= (Bank)intent.getSerializableExtra("bank object with curr from and curr to");

        //Sort by Spinner to sort by the selected method
        final CharSequence[] sort_types = {"Distance","Service Charge"};
        final Spinner sorting_method = findViewById(R.id.sorting_method);
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,sort_types);
        sorting_method.setAdapter(adapter1);
        final String[] drop_down1_choice = new String[1];


        sorting_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                //getting the string value of the dropdown menu choice selected
                drop_down1_choice[0] = sorting_method.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
}