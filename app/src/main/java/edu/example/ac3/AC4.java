package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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

        /*Getting location of user*/
        final double[] latitude = new double[1];
        final double[] longitude = new double[1];
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        /*######################### Code below is self created by Andorid Studio*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //We need to check what to do if the device is unable to locate ####################################
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude[0] = location.getLatitude();
                            longitude[0] = location.getLongitude();

                        }
                        else
                        {

                        }
                    }
                });

        Intent intent= getIntent();
        Bank bank= (Bank)intent.getSerializableExtra("bank object with curr from and curr to");

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