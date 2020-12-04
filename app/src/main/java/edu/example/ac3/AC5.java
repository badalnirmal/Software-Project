package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AC5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c5);

        TextView bank_details = findViewById(R.id.bank_details);
        bank_details.setText("Everest Bank");

        EditText conversion_amount = findViewById(R.id.conversion_amount);
        TextView converted_amount = findViewById(R.id.converted_amount);
        String temp=conversion_amount.getText().toString();
        final Button go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AC5.this,AC6.class);//AC6 is Customer Support
                //Should go to AC4
                startActivity(intent);
            }
        });
    }
}