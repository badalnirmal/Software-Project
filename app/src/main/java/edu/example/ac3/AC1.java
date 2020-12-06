package edu.example.ac3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AC1 extends AppCompatActivity {


    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button Sign_up;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c1);

        firebaseAuth = FirebaseAuth.getInstance();
        Email = (EditText)findViewById(R.id.etemail);
        Password = (EditText)findViewById(R.id.etPass);
        Login = (Button)findViewById(R.id.bt1);
        Sign_up = (Button)findViewById(R.id.bt2);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = Email.getText().toString();
                String pwd = Password.getText().toString();
                if(email.isEmpty()){
                    Email.setError("Please enter Email id");
                    Email.requestFocus();
                }
                else if(pwd.isEmpty()){
                    Password.setError("Please enter your password");
                    Password.requestFocus();
                }

                else if(email.isEmpty()&& pwd.isEmpty() ){
                    Toast.makeText(AC1.this,"Fields are Empty",Toast.LENGTH_SHORT).show();
                }
                else if(!email.isEmpty()&& !pwd.isEmpty() ){
                    firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(! task.isSuccessful()){
                                Toast.makeText(AC1.this,"Login Error, Please Login in again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(AC1.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AC1.this,AC3.class);
                                startActivity(intent);
                            }
                        }
                    });
                }

                else
                {
                    Toast.makeText(getApplicationContext(),"Error occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AC1.this,AC2.class);
                startActivity(intent);
            }
        });
    }

}