package com.example.quizapp;

public class Questions {

    public String mQuestions[] = {
            "Which is the first planet in the solar system?",
            "Which is the second planet in the solar system?",
            "Which is the third planet in the solar system?",
            "Which is the fourth planet in the solar system?",
            "Which is the fifth planet in the solar system?",
            "Which is the sixth planet in the solar system?",
            "Which is the seventh planet in the solar system?",
            "Which is the eighth planet in the solar system?",
            "Which is the ninth planet in the solar system?"
    };

    public String responses[];

     public String getQuestion(int a)
     {
        String question  = mQuestions[a];
        return question;
    }

    Questions()
    {
         responses = new String[mQuestions.length];
         for(int i = 0; i < mQuestions.length; i++)
         {
             responses[i] = "";
         }
    }
}
