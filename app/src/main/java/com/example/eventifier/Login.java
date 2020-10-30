package com.example.eventifier;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Snapshot;

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

import okhttp3.internal.cache.DiskLruCache;

public class Login extends AppCompatActivity {

    Toolbar toolbar;
    EditText sap_id,password;
    ImageButton signin;
    Button signup;
    CheckBox committee, admin;

    String key;
    DatabaseReference reff;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        Log.d("LoginCalled","Called");
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

        committee = findViewById(R.id.committee);
        committee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(committee.isChecked()) {
                    admin.setEnabled(false);
                }
                else
                {
                    admin.setEnabled(true);
                }
            }
        });
        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin.isChecked()) {
                    committee.setEnabled(false);
                }
                else{
                    committee.setEnabled(true);
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!TextUtils.isEmpty(sap_id.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {

                    String sap = sap_id.getText().toString();
                    String pass = password.getText().toString();
                    if(committee.isChecked()){
                        committeeLogin(sap, pass);
                    }
                    else if(admin.isChecked()){
                        adminLogin(sap, pass);
                    }
                    else {
                        dataValidate(sap, pass);
                    }
                } else {
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
        Log.d("DataValidate","DataValidate");
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if (p.equals(snapshot.child("password").getValue().toString())){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("sap_id",s);
                        Log.d("sap_id",s);
                        Log.d("pass",p);
                        startActivity(i);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();

                    }
                    else{
                        password.setText("");
                        password.setError("Incorrect password");
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

    public void committeeLogin(final String s, final String p){
        Log.d("Committee","DataValidate");
        reff = FirebaseDatabase.getInstance().getReference().child("committees");
        reff.orderByChild("committeeId").equalTo(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot child: snapshot.getChildren()){
                        key = child.getRef().getKey();
                        Log.d("Key", key);
                    }


                    if (p.equals(snapshot.child(key).child("password").getValue().toString())){
                        Intent i = new Intent(getApplicationContext(), MainActivity1.class);
                        i.putExtra("committeeId",s);
                        Log.d("sap_id",s);
                        Log.d("pass",p);
                        startActivity(i);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();

                    }
                    else{
                        password.setText("");
                        password.setError("Incorrect password");
                    }
                }
                else{
                    sap_id.setText("");
                    password.setText("");
                    Toast.makeText(Login.this, "Committee does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void adminLogin(final String s, final String p){
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.orderByChild("committeeId").equalTo(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if (p.equals(snapshot.child("password").getValue().toString())){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("sap_id",s);
                        Log.d("sap_id",s);
                        Log.d("pass",p);
                        startActivity(i);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();

                    }
                    else{
                        password.setText("");
                        password.setError("Incorrect password");
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