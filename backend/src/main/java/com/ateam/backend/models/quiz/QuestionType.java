package com.ateam.backend.models.quiz;

public enum QuestionType {
    SINGLE_CHOICE("single choice"),
    MULTIPLE_CHOICE("multiple choice"),
    FILL_OUT("fill out"),
    PAIR_ASSIGN("pair assign");

    public final String label;

    QuestionType(String label){
        this.label = label;
    }

    public static QuestionType fromLabel(String label){
        for(QuestionType type : QuestionType.values()){
            if(type.label.equals(label)){
                return type;
            }
        }
        return null;
    }
}
