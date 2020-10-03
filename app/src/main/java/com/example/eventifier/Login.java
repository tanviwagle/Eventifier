package com.example.eventifier;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Toolbar toolbar;
    EditText sap_id,password;
    ImageButton signin;
    Button signup;

    DatabaseReference reff;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sap_id = findViewById(R.id.sap);
        password = findViewById(R.id.password);
        signin =findViewById(R.id.signin);
        signup =  findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(sap_id.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {

                    String sap = sap_id.getText().toString();
                    String pass = password.getText().toString();
                    dataValidate(sap, pass);
                }
                else{
                    Toast.makeText(Login.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }

    public void dataValidate(final String s, final String p){
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if (p.equals(snapshot.child("password").getValue().toString())){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("sap_id",s);
                        Log.d("sap_id",s);
                        startActivity(i);
                    }
                    else{
                        password.setText("");
                        Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    sap_id.setText("");
                    password.setText("");
                    Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}