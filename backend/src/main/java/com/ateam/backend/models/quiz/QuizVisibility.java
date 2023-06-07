package com.ateam.backend.models.quiz;

public enum QuizVisibility{
    PUBLIC("public"),
    PRIVATE("private");

    public final String label;

    QuizVisibility(String label){
        this.label = label;
    }

    public static QuizVisibility fromLabel(String label){
        for(QuizVisibility visibility : QuizVisibility.values()){
            if(visibility.label.equals(label)){
                return visibility;
            }
        }
        return null;
    }
}

