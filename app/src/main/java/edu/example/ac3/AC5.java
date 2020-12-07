package edu.example.ac3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.input;
import static java.lang.Integer.parseInt;

public class AC5 extends AppCompatActivity {
    private EditText Amount_to_convert;
    private EditText Converted_Amount;
    private Button Go_Back;
    private Button Return_Homepage;
    private Button Print;
    private Bank1 bank_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c5);
        Go_Back = (Button) findViewById(R.id.go_back);
        Return_Homepage = (Button) findViewById(R.id.return_homepage);

        TextView bank_details = findViewById(R.id.bank_details);
        bank_final = (Bank1) getIntent().getSerializableExtra("bank_final");
        String from_s = (String) getIntent().getSerializableExtra("from_s");
        String to_s = (String) getIntent().getSerializableExtra("to_s");

        //System.out.println(bank_final.getB_name()+" "+bank_final.getCurr_from() + from_s);
        bank_details.setText(String.format("%s\n%s away.\n%.2f %s -> %.2f %s",
                bank_final.getB_name(), bank_final.getDistance(),
                bank_final.curr_from,from_s,
                bank_final.curr_to,to_s));

        //moving back to page 4
        Go_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AC5.this, AC4.class);
                startActivity(intent);
            }
        });

        //moving back to page 2/homepage
        Return_Homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AC5.this, AC3.class);
                startActivity(intent);
            }
        });
    }
    void converttocurr()
    {
        try{
            Amount_to_convert = (EditText) findViewById(R.id.conversion) ;
            Converted_Amount = (EditText) findViewById(R.id.converted_amount);
            double amount= parseInt(Amount_to_convert.getText().toString());

            double conversionrate = bank_final.getCurr_to();
            //System.out.println(bank_final.getCurr_to());
            double result=amount*conversionrate;
            Converted_Amount.setText(String.valueOf(result));

        }catch(Exception e){
            e.printStackTrace();
            // Log.e("",e.getMessage());
        }

    }

    public void click(View view)
    {
        converttocurr();
        Print=(Button) findViewById(R.id.bt1);
        Print.setEnabled(true);
    }


}