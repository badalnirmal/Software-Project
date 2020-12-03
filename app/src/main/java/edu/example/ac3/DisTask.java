package edu.example.ac3;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DisTask extends AsyncTask<String,Integer,String> {
    @Override
    protected String doInBackground(String... strings) {
        String data=null;
        try {
            data = downloadURL1(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    return data;
    }

    @Override
    protected void onPostExecute(String s) {
        new ParserTask1().execute(s);
    }

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

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
                HashMap<String, String> hashMapList1 = hashMaps.get(0);
                String distance = hashMapList1.get("distance");
                //String duration = hashMapList1.get("duration");
                System.out.println(distance);
                //System.out.println(duration);
                //System.out.println(hashMaps.size());
        }
    }
}
