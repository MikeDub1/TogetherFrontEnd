package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    Button next, prev;
    TextView question, qNum;
    EditText answer;

private Questions questions = new Questions();
private int currQuestion = 0;
private int numQuestions = questions.mQuestions.length;
Random rand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currQuestion != 0) {
                    questions.responses[currQuestion] = answer.getText().toString();
                    updateQuestion(--currQuestion);
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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder.setMessage("Quiz Complete!").setCancelable(false).setPositiveButton("Retake Quiz",
            new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                }
            }).setNegativeButton("EXIT",
            new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }
        );
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}