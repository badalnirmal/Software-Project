package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class AC4 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c4);
        final TextView try_text = findViewById(R.id.textView3);

        //Getting the curr_from and curr_to strings that user has selected from the previous activity
        Intent intent= getIntent();
        ArrayList<Bank1> banks = (ArrayList<Bank1>) intent.getSerializableExtra("bank object arraylist");
        ArrayList<Bank1> banks_final = new ArrayList<>();

        //System.out.println(banks.size());
        for (int i=0 ;i<banks.size();i++)
        {
            if(banks.get(i).getDistance()!=null)
                banks_final.add(banks.get(i));
        }
        for (int i=0 ;i<banks_final.size();i++)
        {
            System.out.println(banks.get(i).getB_name());
            System.out.println(banks.get(i).getDistance());
        }

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

        final Button go_back_ac3 = findViewById(R.id.go_back_ac3);
        go_back_ac3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AC4.this,AC3.class);//Should go back to AC3
                startActivity(intent);
            }
        });

    }

}
//http://maps.googleapis.com/maps/api/distancematrix/json?origins=27.7048067,85.3074633&destinations=27.6973423,85.2988963&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o
//String dis_url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+latitude+","+longitude+
//                            "&destinations="+lat+","+lng+
//                            "&mode=walking&key=AIzaSyBQ4rRPlNEWHxxh8yW8dnef-AmIqNr-_1o";