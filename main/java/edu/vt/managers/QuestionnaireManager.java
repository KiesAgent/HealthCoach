/*
  - Created by Kevin Thai-Khanh Vu on 2021.11.30
  - Copyright © 2021 Kevin Thai-Khanh Vu. All rights reserved.
 */
package edu.vt.managers;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import edu.vt.EntityBeans.User;
import edu.vt.controllers.UserQuestionnaireController;
import edu.vt.globals.Methods;
import edu.vt.pojo.Question;
import org.primefaces.shaded.json.JSONArray;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named(value = "questionnaireManager")
public class QuestionnaireManager implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */

    @Inject
    private UserQuestionnaireController UserQuestionnaireController;

    // 'Questions' is a List containing the object references of Category objects
    private List<Question> questions = null;

    private List<String> allergies = null;

    private List<String> muscles = null;

    private Integer question1Number;
    private Integer question2Number;
    private Integer question3Number;
    private Integer question4Number;
    private Integer question5Number;
    private Integer question6Number;
    private Integer question7Number;
    private Integer question8Number;

    private String question1Title;
    private String question2Title;
    private String question3Title;
    private String question4Title;
    private String question5Title;
    private String question6Title;
    private String question7Title;
    private String question8Title;

    private String question1Answer;
    private String question2Answer;
    private String question3Answer;
    private String question4Answer;
    private String question5Answer;
    private String question6Answer;
    private String question7Answer;
    private String question8Answer;

    /*
    =================================================================
    Getter and Setter Methods for the Properties (Instance Variables)
    =================================================================
     */

    /*
     Exercises for question 3
     */


    public UserQuestionnaireController getUserQuestionnaireController() {
        return UserQuestionnaireController;
    }

    public void setUserQuestionnaireController(UserQuestionnaireController UserQuestionnaireController) {
        this.UserQuestionnaireController = UserQuestionnaireController;
    }

    public List<String> getAllergies() {
        allergies = new ArrayList<String>();
        allergies.add("Dairy-Free");
        allergies.add("Egg-Free");
        allergies.add("Fat-Free");
        allergies.add("Fish-Free");
        allergies.add("Gluten-Free");
        allergies.add("Low-Sugar");
        allergies.add("Paleo");
        allergies.add("Peanut-Free");
        allergies.add(" Shellfish-Free");
        allergies.add("Soy-Free");
        allergies.add("Tree-Nut-Free");
        allergies.add("Vegan");
        allergies.add("Vegetarian");
        allergies.add("Wheat-Free");
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getMuscles() {
        muscles = new ArrayList<String>();
        muscles.add("Anterior deltoid");
        muscles.add("Biceps brachii");
        muscles.add("Biceps femoris");
        muscles.add("Brachialis");
        muscles.add("Gastrocnemius");
        muscles.add("Gluteus maximus");
        muscles.add("Latissimus dorsi");
        muscles.add("Obliquus externus abdominis");
        muscles.add("Pectoralis major");
        muscles.add("Quadriceps femoris");
        muscles.add("Rectus abdominis");
        muscles.add("Serratus anterior");
        muscles.add("Soleus");
        muscles.add("Trapezius");
        muscles.add("Triceps brachii");
        return muscles;
    }

    public void setMuscles(List<String> muscles) {
        this.muscles = muscles;
    }

    public Integer getQuestion1Number() {
        return question1Number;
    }

    public void setQuestion1Number(Integer question1Number) {
        this.question1Number = question1Number;
    }

    public Integer getQuestion2Number() {
        return question2Number;
    }

    public void setQuestion2Number(Integer question2Number) {
        this.question2Number = question2Number;
    }

    public Integer getQuestion3Number() {
        return question3Number;
    }

    public void setQuestion3Number(Integer question3Number) {
        this.question3Number = question3Number;
    }

    public Integer getQuestion4Number() {
        return question4Number;
    }

    public void setQuestion4Number(Integer question4Number) {
        this.question4Number = question4Number;
    }

    public Integer getQuestion5Number() {
        return question5Number;
    }

    public void setQuestion5Number(Integer question5Number) {
        this.question5Number = question5Number;
    }

    public Integer getQuestion6Number() {
        return question6Number;
    }

    public void setQuestion6Number(Integer question6Number) {
        this.question6Number = question6Number;
    }

    public Integer getQuestion7Number() {
        return question7Number;
    }

    public void setQuestion7Number(Integer question7Number) {
        this.question7Number = question7Number;
    }

    public Integer getQuestion8Number() {
        return question8Number;
    }

    public void setQuestion8Number(Integer question8Number) {
        this.question8Number = question8Number;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getQuestion1Title() {
        return question1Title;
    }

    public void setQuestion1Title(String question1Title) {
        this.question1Title = question1Title;
    }

    public String getQuestion2Title() {
        return question2Title;
    }

    public void setQuestion2Title(String question2Title) {
        this.question2Title = question2Title;
    }

    public String getQuestion3Title() {
        return question3Title;
    }

    public void setQuestion3Title(String question3Title) {
        this.question3Title = question3Title;
    }

    public String getQuestion4Title() {
        return question4Title;
    }

    public void setQuestion4Title(String question4Title) {
        this.question4Title = question4Title;
    }

    public String getQuestion5Title() {
        return question5Title;
    }

    public void setQuestion5Title(String question5Title) {
        this.question5Title = question5Title;
    }

    public String getQuestion6Title() {
        return question6Title;
    }

    public void setQuestion6Title(String question6Title) {
        this.question6Title = question6Title;
    }

    public String getQuestion7Title() {
        return question7Title;
    }

    public void setQuestion7Title(String question7Title) {
        this.question7Title = question7Title;
    }

    public String getQuestion8Title() {
        return question8Title;
    }

    public void setQuestion8Title(String question8Title) {
        this.question8Title = question8Title;
    }

    public String getQuestion1Answer() {
        return question1Answer;
    }

    public void setQuestion1Answer(String question1Answer) {
        this.question1Answer = question1Answer;
    }

    public String getQuestion2Answer() {
        return question2Answer;
    }

    public void setQuestion2Answer(String question2Answer) {
        this.question2Answer = question2Answer;
    }

    public String getQuestion3Answer() {
        return question3Answer;
    }

    public void setQuestion3Answer(String question3Answer) {
        this.question3Answer = question3Answer;
    }

    public String getQuestion4Answer() {
        return question4Answer;
    }

    public void setQuestion4Answer(String question4Answer) {
        this.question4Answer = question4Answer;
    }

    public String getQuestion5Answer() {
        return question5Answer;
    }

    public void setQuestion5Answer(String question5Answer) {
        this.question5Answer = question5Answer;
    }

    public String getQuestion6Answer() {
        return question6Answer;
    }

    public void setQuestion6Answer(String question6Answer) {
        this.question6Answer = question6Answer;
    }

    public String getQuestion7Answer() {
        return question7Answer;
    }

    public void setQuestion7Answer(String question7Answer) {
        this.question7Answer = question7Answer;
    }

    public String getQuestion8Answer() {
        return question8Answer;
    }

    public void setQuestion8Answer(String question8Answer) {
        this.question8Answer = question8Answer;
    }

    /*
    ================
    Instance Methods
    ================

    ***********************************
    Process the Submitted Questionnaire
    ***********************************
     */

    public String titleOfQuestion(Integer i) {

        if (i == 1) return "How tall are you? (ft, in)";
        if (i == 2) return "How much do you weigh? (In Pounds lb)";
        if (i == 3) return "What allergies or health preferences do you have?";
        if (i == 4) return "How many times do you exercise per week?";
        if (i == 5) return "What type of diet are you on?";
        if (i == 6) return "What are your muscle group preferences?";
        if (i == 7) return "What is your weight goal? (In Pounds lb)";
        if (i == 8) return "What do you do most often to exercise?";

        return "";

    }
    public String processQuestionnaire() {
        UserQuestionnaireController.deleteUserQuestionnaire();

        questions = new ArrayList<>();

        Integer questionNumber;
        String questionTitle;
        String questionAnswer;

        /*
         ******************
         *   Question 1   *
         ******************
         */

        question1Number = 1;
        question1Title = titleOfQuestion(1);
        Question question1 = new Question(question1Number, question1Title, question1Answer);
        questions.add(question1);

        /*
         ******************
         *   Question 2   *
         ******************
         */

        question2Number = 2;
        question2Title = titleOfQuestion(2);
        Question question2 = new Question(question2Number, question2Title, question2Answer);
        questions.add(question2);

        /*
         ******************
         *   Question 3   *
         ******************
         */

        question3Number = 3;
        question3Title = titleOfQuestion(3);
        Question question3 = new Question(question3Number, question3Title, question3Answer);
        questions.add(question3);

        /*
         ******************
         *   Question 4   *
         ******************
         */

        question4Number = 4;
        question4Title = titleOfQuestion(4);
        Question question4 = new Question(question4Number, question4Title, question4Answer);
        questions.add(question4);

        /*
         ******************
         *   Question 5   *
         ******************
         */

        question5Number = 5;
        question5Title = titleOfQuestion(5);
        Question question5 = new Question(question5Number, question5Title, question5Answer);
        questions.add(question5);

        /*
         ******************
         *   Question 6   *
         ******************
         */

        question6Number = 6;
        question6Title = titleOfQuestion(6);
        Question question6 = new Question(question6Number, question6Title, question6Answer);
        questions.add(question6);

        /*
         ******************
         *   Question 7   *
         ******************
         */

        question7Number = 7;
        question7Title = titleOfQuestion(7);
        Question question7 = new Question(question7Number, question7Title, question7Answer);
        questions.add(question7);

        /*
         ******************
         *   Question 8   *
         ******************
         */

        question8Number = 8;
        question8Title = titleOfQuestion(8);
        Question question8 = new Question(question8Number, question8Title, question8Answer);
        questions.add(question8);

        //--------------------------------------
        // Create a new UserQuestionnaire object
        //--------------------------------------
        UserQuestionnaireController.prepareCreate();

        //-----------------------
        // Set id attribute value
        //-----------------------
        /*
        The database Primary Key id value is generated and set at the time of UserQuestionnaire object creation.
         */
        //--------------------------------
        // Set dateEntered attribute value
        //--------------------------------
        LocalDate localDate = LocalDate.now();
        Date currentDate = java.sql.Date.valueOf(localDate);

        // Set Date in the format of YYYY-MM-DD
        UserQuestionnaireController.getSelected().setDateEntered(currentDate);

        // Creates a new JSONArray from questions
        JSONArray jasonArray = new JSONArray(questions);

        // Convert the JSON array into a String
        String questionnaire = jasonArray.toString();

        // Set the questionnaire attribute value
        UserQuestionnaireController.getSelected().setQuestionnaire(questionnaire);

        //---------------------------
        // Set userId attribute value
        //---------------------------
        // Obtain the object reference of the signed-in user
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        UserQuestionnaireController.getSelected().setUserId(signedInUser);

        //----------------------------------------------------------
        // Store the newly created UserQuestionnaire in the database
        //----------------------------------------------------------
        UserQuestionnaireController.create();

        //-----------------------------------------------------------
        // Clear the questionnaire content without displaying message
        //-----------------------------------------------------------
        clearQuestionnaire(false);

        return "/index?faces-redirect=true";
    }


    /*
    ************************************************
    Clear All of the Selections in the questionnaire
    ************************************************
    */
    public void clearQuestionnaire(Boolean displayMessage) {
        question1Answer = null;
        question2Answer = null;
        question3Answer = null;
        question4Answer = null;
        question5Answer = null;
        question6Answer = null;
        question7Answer = null;
        question8Answer = null;
        JSONArray jasonArray = new JSONArray(questions);

        if (displayMessage) {
            Methods.showMessage("Information", "Cleared!",
                    "All Selections are Reset!");
        }
    }


    /*
    *******************************************
    Pre-process the PDF File to be in Landscape
    Orientation on Letter Size Paper
    *******************************************
     */
    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.LETTER.rotate());
        pdf.open();
    }

    /*
    *****************************************
    Warn the User for 5 Minutes of Inactivity
    *****************************************
     */
    public void onIdle() {
        Methods.showMessage("Warning", "No User Action for the Last 5 Minutes!",
                "Do You Need More Time?");
    }

    /*
    ***************************************************
    Welcome Back the User After 5 Minutes of Inactivity
    ***************************************************
     */
    public void onActive() {
        Methods.showMessage("Warning", "Welcome Back!",
                "Please Continue Filling Out Your Questionanire!");
    }
}
