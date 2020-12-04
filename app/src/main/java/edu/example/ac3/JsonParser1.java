package edu.example.ac3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser1 {
    private HashMap<String,String> parseJsonObject1(JSONObject object){
        HashMap<String ,String > datalist1 = new HashMap<>();
        try {
            String distance = object.getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("text");
            //String duration = object.getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getString("text");
            datalist1.put("distance",distance);
            //datalist1.put("duration",duration);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datalist1;
    }
    private List<HashMap<String,String>> parseJsonArray1(JSONArray jsonArray){
        List<HashMap<String,String>> dataList1 = new ArrayList<>();
        for (int i=0; i<jsonArray.length();i++)
        {
            try {
                HashMap<String ,String > data = parseJsonObject1((JSONObject) jsonArray.get(i));
                dataList1.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  dataList1;
    }
    public List<HashMap<String,String>> parseResult1(JSONObject jsonObject){
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("rows");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonArray1(jsonArray);
    }
}
