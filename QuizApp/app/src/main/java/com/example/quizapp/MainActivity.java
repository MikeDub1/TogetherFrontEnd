package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4;
    TextView question, score;

private Questions questions = new Questions();
private int currQuestion = 0;
private String answer;
private int mscore = 0;
private int numQuestions = questions.mQuestions.length;

Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rand = new Random();

        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        question = (TextView) findViewById(R.id.Question);
        score = (TextView) findViewById(R.id.textView);

        updateQuestion(currQuestion);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer1.getText()==answer){
                    mscore++;
                }
                else{
                    mscore--;
                }
                updateQuestion(++currQuestion);
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer2.getText()==answer){
                    mscore++;
                }
                else{
                    mscore--;
                }
                updateQuestion(++currQuestion);
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer3.getText()==answer){
                    mscore++;
                }
                else{
                    mscore--;
                }
                updateQuestion(++currQuestion);
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()==answer){
                    mscore++;
                }
                else{
                    mscore--;
                }
                updateQuestion(++currQuestion);
            }
        });



    }

    private void updateQuestion(int num){
        question.setText(questions.getQuestion(num));
        answer1.setText(questions.getChoice1(num));
        answer2.setText(questions.getChoice2(num));
        answer3.setText(questions.getChoice3(num));
        answer4.setText(questions.getChoice4(num));

        answer = questions.getCorrectAnswer(num);
        score.setText("Score" + mscore);
    }

    private void gameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Game Over!").setCancelable(false).setPositiveButton("Retake Quiz",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).setNegativeButton("EXIT",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }
}