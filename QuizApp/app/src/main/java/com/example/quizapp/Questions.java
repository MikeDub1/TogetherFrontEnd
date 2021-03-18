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

    private String mChoices[][]={
            {"Mercury", "Venus", "Mars", "Saturn"},
            {"Jupiter", "Venus", "Earth", "Neptune"},
            {"Mercury", "Venus", "Mars", "test"},
            {"Mercury", "Venus", "Mars", "Saturn"},
            {"Jupiter", "Venus", "Earth", "Neptune"},
            {"Mercury", "Venus", "Mars", "test"},
            {"Mercury", "Venus", "Mars", "Saturn"},
            {"Jupiter", "Venus", "Earth", "Neptune"},
            {"Mercury", "Venus", "Mars", "test"},
    };

    private String mCorrectAnswers[] = {"Mercury", "Jupiter", "Mercury","Mercury", "Jupiter", "Mercury","Mercury", "Jupiter", "Mercury"};

    public String getQuestion(int a){
        String question  = mQuestions[a];
        return question;
    }

    public String getChoice1(int a){
        String choices = mChoices[a][0];
        return choices;
    }

    public String getChoice2(int a){
        String choices = mChoices[a][1];
        return choices;
    }

    public String getChoice3(int a){
        String choices = mChoices[a][2];
        return choices;
    }

    public String getChoice4(int a){
        String choices = mChoices[a][3];
        return choices;
    }

    public String getCorrectAnswer(int a){
        String answer = mCorrectAnswers[a];
        return answer;
    }
}
