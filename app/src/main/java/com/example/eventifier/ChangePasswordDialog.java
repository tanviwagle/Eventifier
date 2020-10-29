package com.example.eventifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChangePasswordDialog extends AppCompatDialogFragment {

    EditText currentPwd, newPwd, cfnPwd;

    String sap, pwd, pattern, newPass, confirm;
    DatabaseReference reff1;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        sap = this.getArguments().getString("sap_id");
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = (LayoutInflater) getTargetFragment().getLayoutInflater();
        reff1 = FirebaseDatabase.getInstance().getReference().child("Users");
        View view = inflater.inflate(R.layout.change_password_dialog, null);

        currentPwd = view.findViewById(R.id.currentPwd);
        newPwd = view.findViewById(R.id.newPwd);
        cfnPwd = view.findViewById(R.id.cfnPwd);

        currentPwd.requestFocus();

        pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

        currentPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String current = currentPwd.getText().toString();
                reff1.child(sap).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        pwd = snapshot.child("password").getValue().toString();
                        if (!pwd.equals(current)) {
                            currentPwd.setError("Incorrect Password");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!newPwd.getText().toString().matches(pattern))
                {
                    newPwd.requestFocus();
                    newPwd.setError("password must contain \nat least 8 characters\none uppercase letter, lowercase letter, number and symbol".toUpperCase());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cfnPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!cfnPwd.getText().equals(newPass))
                {
                    cfnPwd.requestFocus();
                    cfnPwd.setError("Password should match with new password".toUpperCase());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newPass = newPwd.getText().toString();
                        HashMap hashMap = new HashMap();
                        hashMap.put("password", newPass);
                        reff1.child(sap).updateChildren(hashMap);
                        Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                        dismiss();

                    }
                });

        return builder.create();
    }

}
