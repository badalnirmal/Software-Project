package edu.example.ac3;

// this is a test

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
import android.widget.Button;
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

public class AC3 extends AppCompatActivity {

    final static ArrayList<Bank1> list_of_banks = new ArrayList<>();
    double latitude;
    double longitude;
    final static ArrayList<Distance> distance = new ArrayList<Distance>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c3);
         /*
        Intent fname_intent =getIntent();
        // Need to recheck is this is imported from user Database of Intent activity from AC1 i.e USER class from AC1
        String f_name= fname_intent.getStringExtra("FNAME");//first name of user imported from Database
        */
         TextView user_fname = findViewById(R.id.user_fname);
        user_fname.setText("Welcome User");

        final Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AC3.this,AC1.class);//AC1 is login activity page(change AC6 to AC1)
                //check for the activity name for login activity and replace AC1.class
                 startActivity(intent);
                 recreate();// this is for the reloading of AC1 page //recheck with TA
            }
        });

        final CharSequence[] curr_types = {"NRS","USD","GBP","CNY","EUR"};

        final Spinner drop_down1 = findViewById(R.id.drop_down1);
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,curr_types);
        drop_down1.setAdapter(adapter1);
        final String[] drop_down1_choice = new String[1];


        drop_down1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
            {
                //getting the string value of the dropdown menu choice selected
                drop_down1_choice[0] = drop_down1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner drop_down2 = findViewById(R.id.drop_down2);
        adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,curr_types);
        drop_down2.setAdapter(adapter1);
        final String[] drop_down2_choice = new String[1];

        final TextView check=findViewById(R.id.check);
        //check.setText(drop_down1.getSelectedItem().toString());

        drop_down2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
            {
                //getting the string value of the dropdown menu choice selected
                drop_down2_choice[0] = drop_down2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Button show_banks = findViewById(R.id.show_banks);

        show_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drop_down1_choice[0].equals(drop_down2_choice[0])) {
                    //Starting the Fused location object to get the user location

                    FusedLocationProviderClient fusedLocationClient;
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(AC3.this);
                    //Check if user is allowed the location access, if not get location access from the user
                    if (ActivityCompat.checkSelfPermission(AC3.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AC3.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) AC3.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }

                    //Once permissions are set, the app is able to get last known location that any other apps have accessed from the device or any last known recorded location of the app
                    // if there is no last known location the app crashes and returns null
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(AC3.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {

                                        //The values below are the user's latitude and Longitude from the user location
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                        final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude +
                                                "&radius=5000"+ //radius set to 5km
                                                //"&rankby=distance" + //rank the output by distance
                                                "&types=bank" + //getting the keyword bank
                                                "&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o"; //API Key For the Nearby
                                        System.out.println(url);
                                        new PlaceTask().execute(url);

                                        //Data from the Places API is to be returned here
//Calls  Place Task Class
                                    }
                                    else
                                    {
                                        Toast.makeText(AC3.this, "Error Occurred while Fetching Location!", Toast.LENGTH_SHORT).show();
                                        //check if this toast works and figure out what to do if location cannot be fetched.
                                        //Get Current Location Code in youtube video below
                                        //https://www.youtube.com/watch?v=pjFcJ6EB8Dg
                                        //11:55
                                    }

                                }
                            });
                    Intent intent = new Intent(AC3.this, AC4.class);//AC4 is banks listing page(change AC6 to AC4)
                    intent.putExtra("bank object arraylist", list_of_banks);
                    intent.putExtra("curr_from_string",drop_down1_choice[0] );
                    intent.putExtra("curr_to_string", drop_down2_choice[0]);
                    startActivity(intent);
                    for(int i=0; i<list_of_banks.size();i++)
                    {
                        list_of_banks.get(i).setDistance(distance.get(i).distance_val);
                    }
                    //System.out.println(distance.size());
                    //System.out.println(list_of_banks.size());
//                    for(int i=0; i<distance.size();i++)
//                    {
//                        System.out.println(list_of_banks.get(i).getB_name());
//                        list_of_banks.get(i).setDistance(distance.get(i).distance_val);
//                        System.out.println(distance.get(i).distance_val);
//                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please select two different currencies to convert.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button cust_supt = findViewById(R.id.cust_supt);
        cust_supt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AC3.this,AC6.class);//AC6 is Customer Support
                startActivity(intent);
            }
        });
    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
                //Calls Download URL to get the data from API
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Returns the JSON data from API to the AsyncTask So it can Execute PostExecute Method
            return data;
        }
        //This executes the JSON Parser class which then calls the JSON Parser class to return values
        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    // Download URL Method here reads the JSON input into the line and returns back to Background Process Method in PlaceTask Class
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
    // This class is called by Post Execute from PlaceTask Class which gets the JSON data mapped in HashMap and that hashmap is Post Executed to get individual values
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
        //This Post Execute Gets the names of bank and latitude and longitude to feed into the next Distance Matrix API
        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            for (int i = 0; i<hashMaps.size();i++)
            {
                Bank1 bank = new Bank1();
                HashMap<String ,String > hashmaPList = hashMaps.get(i);
                //Pass the latitude and longitude to DisTask to get the diatance from Distance Matrix API
                double lat = Double.parseDouble(hashmaPList.get("lat"));
                double lng = Double.parseDouble(hashmaPList.get("lng"));
                String name = hashmaPList.get("name");
                if(name.contains("Agricultural")) {
                    bank.setB_name(name);
                    bank.setSer_charge(200.00);//Check this value for all banks
                    list_of_banks.add(bank);
                }
                if(name.contains("Banijya")) {
                    bank.setB_name(name);
                    bank.setSer_charge(500.00);//Check this value for all banks
                    list_of_banks.add(bank);
                }
                if(name.contains("NIC")) {
                    bank.setB_name(name);
                    bank.setSer_charge(550.00);//Check this value for all banks
                    list_of_banks.add(bank);
                }
                if(name.contains("Everest Bank")) {
                    bank.setB_name(name);
                    bank.setSer_charge(250.00);//Check this value for all banks
                    list_of_banks.add(bank);
                }


                //Here feed the datas to the bank1 object and then put it in arraylist**************************************************************************************
                //Or return the received datas back to the line 74**********************************************************************************************************

                String dis_url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+latitude+","+longitude+
                        "&destinations="+lat+","+lng+
                        "&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o";
                //System.out.println(dis_url);
                new DisTask().execute(dis_url);

                //Calls Line 204
            }
        }

    }

    //This class is called by Parser Task of PlaceTask which is executing its current onPostExecution
    public class DisTask extends AsyncTask<String,Integer,String> {
        @Override

        //This method takes the URL for Distance Matrix API which gets the JSON data from API and post executes the data received to parse it
        protected String doInBackground(String... strings) {
            String data=null;
            try {
                data = downloadURL1(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
        //This post execution takes the JSON from API and parses it to receive the distance
        @Override
        protected void onPostExecute(String s) {
            new ParserTask1().execute(s);
        }

        //This method is called by Background process of DisTask to get JSON data from URL taken
        private String downloadURL1(String string) throws IOException {
            URL url = new URL(string);
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
            reader.close();

            return data;
        }


        //This class is called by Post Execute of DisTask to parse the JSON data received and then provide the result
        private class ParserTask1 extends AsyncTask<String,Integer, List<HashMap<String ,String >>> {
            @Override
            protected List<HashMap<String, String>> doInBackground(String... strings) {
                JsonParser1 jsonParser1 = new JsonParser1();

                List<HashMap<String,String>> mapList1 =null;
                JSONObject obj = null;
                try {
                    obj = new JSONObject(strings[0]);
                    mapList1 = jsonParser1.parseResult1(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return mapList1;
            }

            //This post execute method receives the HashMAp from the Background process of ParserTask1 which is parsed as JSON object and Array and take the elements out
            @Override
            protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
                HashMap<String, String> hashMapList1 = hashMaps.get(0);
                Distance one = new Distance();
                one.distance_val=hashMapList1.get("distance");
                distance.add(one);
            }
        }
    }
    public class Distance{
        String distance_val;
    }

}
//http://maps.googleapis.com/maps/api/distancematrix/json?origins=27.7048067,85.3074633&destinations=27.6973423,85.2988963&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o
//String dis_url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+latitude+","+longitude+
//                            "&destinations="+lat+","+lng+
//                            "&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o";