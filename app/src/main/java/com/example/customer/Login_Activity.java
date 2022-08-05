package com.example.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences shrd;

    String Email, PASSWORD, Con_Pass, USER, CARDID, BAL, ADD;

    boolean cond = false, PW = false;

    TextInputEditText mail, pass, c_pass, s_pw;

    FrameLayout fm1, fm2, fm3;
    Button btn_LG_vf;

    User user;

    DatabaseReference RE1;

    @Override
    protected void onStart() {
        super.onStart();
        if (shrd.contains("CID")) {
            startActivity(new Intent(Login_Activity.this, Home_Page.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initialize();

        btn_LG_vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fm1.getVisibility() == View.VISIBLE) {
                    RE1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.getValue(User.class).getEmail().equalsIgnoreCase(mail.getText().toString())) {
                                    cond = true;
                                    Email = ds.getValue(User.class).getEmail();
                                    USER = ds.getValue(User.class).getfName() + " " + ds.getValue(User.class).getlName();
                                    BAL = ds.getValue(User.class).getBalance();
                                    ADD = ds.getValue(User.class).getAddress();
                                    CARDID = ds.getValue(User.class).getCardID();
                                    if (ds.getValue(User.class).getPassword() != null) {
                                        PW = true;
                                    }
                                    break;
                                } else {
                                    cond = false;
                                }
                            }
                            if (cond) {
                                if (!PW) {
                                    fm1.setVisibility(View.GONE);
                                    fm2.setVisibility(View.VISIBLE);
                                    btn_LG_vf.setText("Sign UP");
                                } else {
                                    fm1.setVisibility(View.GONE);
                                    fm3.setVisibility(View.VISIBLE);
                                    btn_LG_vf.setText("Sign IN");
                                }
                            } else {
                                Toast.makeText(Login_Activity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (fm2.getVisibility() == View.VISIBLE) {
                    PASSWORD = pass.getText().toString();
                    Con_Pass = c_pass.getText().toString();
                    if (!PASSWORD.equals(CARDID)) {
                        if (PASSWORD.equals(Con_Pass)) {
                            DatabaseReference RE2 = FirebaseDatabase.getInstance().getReference("Customer").child(CARDID);
                            user.setPassword(PASSWORD);
                            RE2.child("password").setValue(user.getPassword()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        fm2.setVisibility(View.GONE);
                                        fm3.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(Login_Activity.this, "card id and Password can't be same", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DatabaseReference RE3 = RE1.getRef();
                    RE3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.getValue(User.class).getPassword().equalsIgnoreCase(s_pw.getText().toString())) {
                                    cond = true;
                                    break;
                                } else {
                                    cond = false;
                                    Toast.makeText(Login_Activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (cond) {
                                editor.clear();
                                editor.apply();
                                editor.putString("CID", CARDID);
                                editor.apply();

                                startActivity(new Intent(Login_Activity.this, Home_Page.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void initialize() {

        shrd = getSharedPreferences("Customer", MODE_PRIVATE);
        editor = shrd.edit();

        mail = findViewById(R.id.L_email1);
        pass = findViewById(R.id.S_L_password1);
        c_pass = findViewById(R.id.S_L_Con_password1);
        s_pw = findViewById(R.id.L_password1);

        user = new User();

        fm1 = findViewById(R.id.FM1);
        fm2 = findViewById(R.id.FM2);
        fm3 = findViewById(R.id.FM3);

        btn_LG_vf = findViewById(R.id.Btn_VF_LG);

        RE1 = FirebaseDatabase.getInstance().getReference("Customer");
    }
}