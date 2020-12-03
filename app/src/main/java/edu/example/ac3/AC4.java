package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AC4 extends AppCompatActivity {

    ArrayList<Bank1> list_of_banks = new ArrayList<>();
    double latitude;
    double longitude;

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
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            System.out.println(latitude);
                            System.out.println(longitude);
                            final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude +
                                    "&radius=5000"+ //radius set to 5km
                                    //"&rankby=distance" + //rank the output by distance
                                    "&types=bank" + //getting the keyword bank
                                    "&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o"; //API Key For the Nearby
                            System.out.println(url);
                            new PlaceTask().execute(url);
                        }
                        else
                        {
                            Toast.makeText(AC4.this, "Error Occurred while Fetching Location!", Toast.LENGTH_SHORT).show();
                            //check if this toast works and figure out what to do if location cannot be fetched.
                            //Get Current Location Code in youtube video below
                            //https://www.youtube.com/watch?v=pjFcJ6EB8Dg
                            //11:55
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
    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL (string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine())!=null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();;
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser= new JsonParser();
            List<HashMap<String,String >> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject((strings[0]));
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            for (int i = 0; i<hashMaps.size();i++)
            {
                Bank1 bank = new Bank1();
                HashMap<String ,String > hashmaPList = hashMaps.get(i);
                double lat = Double.parseDouble(hashmaPList.get("lat"));
                double lng = Double.parseDouble(hashmaPList.get("lng"));
                String name = hashmaPList.get("name");
                System.out.println(name);
                if((name.contains("Laxmi"))&& name.contains("Bank"))
                {
                    String dis_url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+latitude+","+longitude+
                            "&destinations="+lat+","+lng+
                            "&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o";

                    new DisTask().execute(dis_url);
                    bank.setB_name(name);
                   bank.setSer_charge(500.00);

                }
                list_of_banks.add(bank);

                //System.out.println(lat);
                //System.out.println(lng);
            }
        }

    }


}
//http://maps.googleapis.com/maps/api/distancematrix/json?origins=27.7048067,85.3074633&destinations=27.6973423,85.2988963&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o
//String dis_url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+latitude+","+longitude+
//                            "&destinations="+lat+","+lng+
//                            "&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o";