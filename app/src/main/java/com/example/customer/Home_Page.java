package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity {

    ArrayAdapter<String>  AD_TN;
    ArrayList<String>  AL_TN;

    Spinner sp_tn;

    SharedPreferences.Editor editor;
    SharedPreferences shrd;

    DatabaseReference RE1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initialize();
        RE1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    for(DataSnapshot ds1:ds.getChildren()){
                        for(DataSnapshot ds2:ds1.getChildren()) {
                            AL_TN.add(ds2.getValue(Ticket.class).getTicket_No());
                            sp_tn.setAdapter(AD_TN);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialize() {

        AL_TN = new ArrayList<>();
        AD_TN = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, AL_TN);


        shrd = getSharedPreferences("Customer", MODE_PRIVATE);
        editor = shrd.edit();

        RE1 = FirebaseDatabase.getInstance().getReference("Customer").child(shrd.getString("CID",null));
    }
}