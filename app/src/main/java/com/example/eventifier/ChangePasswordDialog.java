package com.example.eventifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
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

    String sap;
    DatabaseReference reff1;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        sap = this.getArguments().getString("sap_id");
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = (LayoutInflater) getTargetFragment().getLayoutInflater();
        reff1 = FirebaseDatabase.getInstance().getReference().child("Users");
        View view = inflater.inflate(R.layout.change_password_dialog, null);
        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String current = currentPwd.getText().toString();
                        reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                // do some stuff once
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                            });
                        /*if(current.equals(reff1.child(sap).child(sap).child("password"))){
                            Toast.makeText(getContext(), "Yessssss", Toast.LENGTH_SHORT).show();
                        }
                        else{
                        String newPass = newPwd.getText().toString();
                        String confirm = cfnPwd.getText().toString();
                        HashMap hashMap = new HashMap();
                        hashMap.put("password",newPass);
                        reff1.child(sap).updateChildren(hashMap);
                        dismiss();}*/
                    }
                });

        currentPwd = view.findViewById(R.id.currentPwd);
        newPwd = view.findViewById(R.id.newPwd);
        cfnPwd = view.findViewById(R.id.cfnPwd);

        return builder.create();
    }

}
