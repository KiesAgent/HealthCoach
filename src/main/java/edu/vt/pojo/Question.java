/*
  - Created by Kevin Thai-Khanh Vu on 2021.11.30
  - Copyright © 2021 Kevin Thai-Khanh Vu. All rights reserved.
 */
package edu.vt.pojo;

// This class provides a Plain Old Java Object (POJO) representing a HealthSurvey survey question
public class Question {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    Integer questionNumber;
    String questionTitle;
    String questionAnswer;

    /*
    ==================
    Constructor Method
    ==================
     */
    public Question(Integer questionNumber, String questionTitle, String questionAnswer) {
        this.questionNumber = questionNumber;
        this.questionTitle = questionTitle;
        this.questionAnswer = questionAnswer;
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

}
