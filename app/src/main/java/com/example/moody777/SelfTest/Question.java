package com.example.moody777.SelfTest;

public class Question {
    private int questionID;
    private String question;
    //private String description;

    private int imagePath;

    public Question() {
    }

    public Question(int questionID, String question, int imagePath) {
        this.questionID = questionID;
        this.imagePath = imagePath;
        this.question = question;
        // this.description =description;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getQuestion() {
        return question;
    }

    public int getImagePath() {
        return imagePath;
    }

    public int getQuestionID() {
        return questionID;
    }


}

