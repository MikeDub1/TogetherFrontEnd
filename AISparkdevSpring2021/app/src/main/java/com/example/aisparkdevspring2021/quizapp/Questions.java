package com.example.aisparkdevspring2021.quizapp;

public class Questions {

    public String mQuestions[] = {
            "What are your interests?",
            "What do you do for work?",
            "Do you consider yourself a shy person or out going person?",
            "What is your star sign?",
            "How do you normally spent your weekend?",
            "What are your favorite places to hangout?",
            "Are you physically active and if you do what kinds?",
            "Do you like to play games and if you do what kinds?",
            "What is the most recent place you visited?"
    };

    public String responses[];
    public String concatResponses = "";

    public void concat(String[] x)
    {
        for(int i = 0; i < x.length; i++) {
            concatResponses = concatResponses + x[i];
        }
    }

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

    public String getConcatResponses() {return concatResponses;}
}
