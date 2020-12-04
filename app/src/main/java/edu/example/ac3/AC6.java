package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AC6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c6);

        final Button go_back_ac3 = findViewById(R.id.go_back_ac3);
        go_back_ac3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AC6.this,AC5.class);//Should go back to AC3
                startActivity(intent);
            }
        });


    }
}