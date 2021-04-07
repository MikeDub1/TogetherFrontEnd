package com.example.aisparkdevspring2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aisparkdevspring2021.quizapp.QuizActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateBioActivity extends AppCompatActivity {

    protected EditText biography_box;
    protected Button matchMeUp;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bio);
        biography_box = (EditText) findViewById(R.id.biography);

        matchMeUp = (Button) findViewById(R.id.match_me_up);

        matchMeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String biography = biography_box.getText().toString();
                System.out.println("BIOGRAPHY: " + biography);


                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");
                String name = getIntent().getStringExtra("name");
                String radioButtonText = getIntent().getStringExtra("button");
                String responses = getIntent().getStringExtra("answers");

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateBioActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(CreateBioActivity.this, "An error has occured in the signUp process", Toast.LENGTH_SHORT).show();
                            Intent returnToMain = new Intent(CreateBioActivity.this, MainActivity.class);
                            return;
                        } else {
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            Map userInfo = new HashMap<>();
                            userInfo.put("name", name);
                            userInfo.put("sex", radioButtonText);
                            userInfo.put("profileImageUrl", "default");
                            userInfo.put("bio", biography);

                            cluster(biography);
                            getPersonalityTypes(responses);

                            currentUserDb.updateChildren(userInfo);
                        }
                    }
                });
            }
        });

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;
                if (user != null) {
                    Intent main = new Intent(CreateBioActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                    return;
                }
            }
        };

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    private void cluster(String biography) {
    }

    private void getPersonalityTypes(String responses) {

    }


}