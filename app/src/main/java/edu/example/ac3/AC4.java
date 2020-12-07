package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        String currency_from = (String) intent.getSerializableExtra("curr_from_string");
        String currency_to = (String) intent.getSerializableExtra("curr_to_string");
        final ArrayList<Bank1> banks_final = new ArrayList<>();

        //System.out.println(banks.size());
        for (int i=0 ;i<banks.size();i++) {
            if (banks.get(i).getDistance() != null) {
                banks_final.add(banks.get(i));
                banks_final.get(i).setCurr_from_s(currency_from);
                banks_final.get(i).setCurr_to_s(currency_to);
            }
        }
/*
This code is here to test the import from intent as it gets added to the banks_final array
        System.out.println(banks_final.size());
        for (int i=0 ;i<banks_final.size();i++)
        {
            System.out.println(banks_final.get(i).getB_name());
            System.out.println(banks_final.get(i).getSer_charge());
            System.out.println(banks_final.get(i).getCurr_to_s());
            System.out.println(banks_final.get(i).getCurr_to());
            System.out.println(banks_final.get(i).getCurr_from_s());
            System.out.println(banks_final.get(i).getCurr_from());
            System.out.println(banks_final.get(i).getDistance());
        }


*/
        System.out.println(banks.size());
        RecyclerView rvBanks = findViewById(R.id.list_banks);

        final BankAdapter adapter = new BankAdapter(banks_final);
        if(banks.size()!= 0) {

            rvBanks.setAdapter(adapter);
            rvBanks.setLayoutManager(new LinearLayoutManager(this));
            rvBanks.getChildAt(0);
            rvBanks.getContext();

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

        final Button sort_button = findViewById(R.id.sort_button);
        sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drop_down1_choice[0] == "Service Charge")
                {
                    adapter.swapItems(banks_final);
                }
                if(drop_down1_choice[0] == "Distance")
                {
                    adapter.swapItems(banks_final);
                }
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