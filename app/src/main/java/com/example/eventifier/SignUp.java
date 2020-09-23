package com.example.eventifier;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignUp extends AppCompatActivity {
    Toolbar toolbar;
    EditText sap, pass, name, cnfPass, email;
    ImageButton signup;
    AutoCompleteTextView stream;
    Button login;

    DatabaseReference reff;

    String[] strYear = {"MCA 1st year", "MCA 2nd year", "MCA 3rd year", "BTech 1st year", "BTech 2nd year", "BTech 3rd year", "BTech 4th year"};

    Users u;

    long id = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* Initializations */
        sap = findViewById(R.id.sap);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        cnfPass = findViewById(R.id.cfnPass);
        name = findViewById(R.id.name);
        stream = findViewById(R.id.stream);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        toolbar =  findViewById(R.id.toolbar);

        u = new Users();

        stream.setTextColor(Color.BLACK);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        /* Toolbar back button */
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /* Populating autotextview with streams */
        final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.hint_completion_layout, R.id.tvHintCompletion, strYear);
        stream.setThreshold(1);
        stream.setAdapter(adapter);

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                /* Retrieve text from edittext*/

                String n = name.getText().toString();
                String em = email.getText().toString();
                String pwd = pass.getText().toString();
                String cnfPwd = cnfPass.getText().toString();
                String stYear = stream.getText().toString();

                boolean result =  !TextUtils.isEmpty(em) && android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches();

                if (!TextUtils.isEmpty(sap.getText().toString()) && !TextUtils.isEmpty(n) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(cnfPwd) && !TextUtils.isEmpty(stYear)) {
                    Long sap_id = Long.parseLong(sap.getText().toString());
                    if ("true".equals("" + result)) {
                        if (pwd.equals(cnfPwd)) {

                            /* Store in database */
                            u.setName(n);
                            u.setEmail(em);
                            u.setPassword(pwd);
                            u.setSap(sap_id);
                            u.setStream(stYear);
                            reff.child("user " + id).setValue(u);
                            startActivity(new Intent(getApplicationContext(), Login.class));

                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Password and confirm password does not match", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Enter correct email", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(SignUp.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });


    }
}