package com.ateam.backend.models.quiz;

public enum QuizGrading {
    POINT_PER_ANSWER("point per answer"),
    POINT_PER_QUESTION("point per question"),
    CUSTOM_POINTS("custom points");

    public final String label;

    QuizGrading(String label){
        this.label = label;
    }

    public static QuizGrading fromLabel(String label){
        for(QuizGrading grading : QuizGrading.values()){
            if(grading.label.equals(label)){
                return grading;
            }
        }
        return null;
    }
}
