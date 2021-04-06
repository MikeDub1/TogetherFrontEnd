package com.example.aisparkdevspring2021.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aisparkdevspring2021.ChooseLoginRegistrationActivity;
import com.example.aisparkdevspring2021.R;
import com.example.aisparkdevspring2021.MainActivity;
import com.example.aisparkdevspring2021.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class QuizActivity extends AppCompatActivity {

    Button next, prev;
    TextView question, qNum;
    EditText answer;

    private Questions questions = new Questions();
    private int currQuestion = 0;
    private int numQuestions = questions.mQuestions.length;
    Random rand;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        rand = new Random();
        answer = (EditText) findViewById(R.id.userIn);
        next = (Button) findViewById(R.id.next);
        prev = (Button) findViewById(R.id.prev);
        question = (TextView) findViewById(R.id.Question);
        qNum = (TextView) findViewById(R.id.qNum);

        updateQuestion(currQuestion);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currQuestion >= (numQuestions-1))
                    gameOver();
                else {
                    questions.responses[currQuestion] = answer.getText().toString();
                    updateQuestion(++currQuestion);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;
                if (user != null) {
                    intent = new Intent(QuizActivity.this, MainActivity.class);
                    //Put params for clusters and compatible personality types here!
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currQuestion != 0) {
                    questions.responses[currQuestion] = answer.getText().toString();
                    updateQuestion(--currQuestion);
                }
                else{
                    Intent back = new Intent(QuizActivity.this, ChooseLoginRegistrationActivity.class);
                    startActivity(back);
                    finish();
                    return;
                }
            }
        });

    }

    private void updateQuestion(int num){
        question.setText(questions.getQuestion(num));
        qNum.setText("Question: " + String.valueOf(currQuestion+1));
        answer.setText(questions.responses[num]);
    }

    private void gameOver(){

        questions.concat(questions.responses);

        //HTTP request code


        Intent myIntent = getIntent();

        String email = myIntent.getStringExtra("email");
        String password = myIntent.getStringExtra("password");
        String name = myIntent.getStringExtra("name");
        int selectID = myIntent.getIntExtra("selectID", 1);
        String radioButtonText = myIntent.getStringExtra("button").toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(QuizActivity.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(QuizActivity.this, "An error has occured in the signUp process", Toast.LENGTH_SHORT).show();
                    goToRegistrationActivity(1);
                    return;
                }else {
                    String userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                    Map userInfo = new HashMap<>();
                    userInfo.put("name", name);
                    userInfo.put("sex", radioButtonText);
                    userInfo.put("profileImageUrl", "default");
                    currentUserDb.updateChildren(userInfo);
                    goToRegistrationActivity(0);

                }
            }
        });

        Intent main = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(main);
        finish();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    private void goToRegistrationActivity(int error)
    {
        Intent returnToChoose = new Intent(QuizActivity.this, ChooseLoginRegistrationActivity.class);
        returnToChoose.putExtra("error", error);
        startActivity(returnToChoose);
        finish();
    }
}