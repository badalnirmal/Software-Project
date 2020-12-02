package edu.example.ac3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AC2 extends AppCompatActivity {
    TextInputLayout reg_fname,reg_lname,reg_email,reg_phone,reg_password,con_password;
    Button regBtn,regtologinbtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private static final String USERS="user";
    private static final String TAG="AC2";
    private HelperClass user;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference mDatabase= database.getReference(USERS);
    private Boolean validateFName()
    {
        String val=reg_fname.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_fname.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_fname.setError(null);
            return true;
        }
    }
    private Boolean validateLName()
    {
        String val=reg_lname.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_lname.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_lname.setError(null);
            return true;
        }
    }
    private Boolean validateEmail()
    {
        String val=reg_email.getEditText().getText().toString();
        String emailpattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty())
        {
            reg_email.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailpattern))
        {
            reg_email.setError("Invalid email");
            return false;
        }
        else {
            reg_email.setError(null);
            return true;
        }
    }
    private Boolean validatePhone()
    {
        String val=reg_phone.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_phone.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches("[0-9]{10}"))
        {
            reg_phone.setError("Invalid Phone- Must be 10 digit");
            return false;
        }
        else {
            reg_phone.setError(null);
            return true;
        }
    }
    private Boolean validatePassword()
    {
        String val=reg_password.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_password.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_password.setError(null);
            return true;
        }
    }
    private Boolean match_password()
    {
        String val=reg_password.getEditText().getText().toString();
        String val1=con_password.getEditText().getText().toString();
        if(!val.equals(val1))
        {
            con_password.setError("Password did not match");
            return false;
        }
        else{
            con_password.setError(null);
            return true;
        }
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c2);

        reg_fname=findViewById(R.id.fName);
        reg_lname=findViewById(R.id.LName);
        reg_email=findViewById(R.id.email);
        reg_phone=findViewById(R.id.phone);
        reg_password=findViewById(R.id.password);
        con_password=findViewById(R.id.confirm_password);
        regBtn=findViewById(R.id.signup);
        regtologinbtn=findViewById(R.id.tologin);




        regBtn.setOnClickListener(new android.view.View.OnClickListener(){

            @Override
            public void onClick(View view) {
                rootNode =FirebaseDatabase.getInstance();
                reference=rootNode.getReference("users");

                String fname=reg_fname.getEditText().getText().toString();
                String lname=reg_lname.getEditText().getText().toString();
                String email=reg_email.getEditText().getText().toString();
                String phone=reg_phone.getEditText().getText().toString();
                String password=reg_password.getEditText().getText().toString();
                String confirm_password=con_password.getEditText().getText().toString();
                if(!validateFName()||!validateLName()||!validateEmail()||!validatePhone()||!validatePassword()||!match_password())
                {
                    return;
                }

                else
                {
                    /*TRY THIS#############################################################################################*/
                    //FirebaseAuth fAuth = FirebaseAuth.getInstance();
                    //registerUser(email,password,fAuth);
                    //String UID = fAuth.getUid();
                    // do register user first then get his UID
                    // then push that UID into the database
                    // then in login activity get the UID from instance and call database instance using that pushed UID to get the user data

                    user =new HelperClass(fname,lname,email,phone,password,confirm_password);
                    reference.child(phone).setValue(user);
                    registerUser(email,password);
                }
            }

        });
        regtologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AC2.this,AC1.class);
                startActivity(intent);
            }
        });
    }


    public void registerUser(String email, String pwd)
    {
        fAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "SUCCESSFULLY CREATED");
                    FirebaseUser user = fAuth.getCurrentUser();
                    updateUI(user);

                } else {
                    Log.w(TAG, "Failure", task.getException());
                    Toast.makeText(AC2.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void updateUI(FirebaseUser currentUser){
        String KeyId=mDatabase.push().getKey();
        Intent i=new Intent(AC2.this,AC1.class);
        startActivity(i);
    }


}