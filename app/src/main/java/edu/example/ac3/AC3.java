package edu.example.ac3;

// this is a test

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayDeque;

public class AC3 extends AppCompatActivity {

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
                    Intent intent = new Intent(AC3.this, AC4.class);//AC4 is banks listing page(change AC6 to AC4)
                    Bank bank = new Bank();
                    bank.curr_from = drop_down1_choice[0];
                    bank.curr_to = drop_down2_choice[0];
                    intent.putExtra("bank object with curr from and curr to", bank);
                    startActivity(intent);
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
}