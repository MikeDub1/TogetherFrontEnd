package com.example.aisparkdevspring2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.aisparkdevspring2021.quizapp.QuizActivity;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private Button mRegister;
    private EditText mEmail, mPassword, mName;


    private RadioGroup mRadioGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);




        mRegister = findViewById(R.id.register);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mName = findViewById(R.id.name);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        mRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               final String email = mEmail.getText().toString();
               final String password = mPassword.getText().toString();
               final String name = mName.getText().toString();
               int selectID = mRadioGroup.getCheckedRadioButtonId();
               final RadioButton radioButton =  (RadioButton) findViewById(selectID);
                if(radioButton.getText() == null){
                   return;
               }


                Intent quizIntent = new Intent(RegistrationActivity.this, QuizActivity.class);
                quizIntent.putExtra("email", email);
                quizIntent.putExtra("password", password);
                quizIntent.putExtra("name", name);
                quizIntent.putExtra("selectID", selectID);
                quizIntent.putExtra("button", radioButton.getText());

                startActivity(quizIntent);

                if(getIntent().getIntExtra("error", 0) == 0) {
                    Intent main = new Intent(RegistrationActivity.this, MainActivity.class);
                    finish();
                    return;
                }
            }
        });
    }


}