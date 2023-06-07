package com.ateam.backend.models.quiz;

public enum QuizStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    public final String label;

    QuizStatus(String label){
        this.label = label;
    }

    public static QuizStatus fromLabel(String label){
        for(QuizStatus status : QuizStatus.values()){
            if(status.label.equals(label)){
                return status;
            }
        }
        return null;
    }
}
