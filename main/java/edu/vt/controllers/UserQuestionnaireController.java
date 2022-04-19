/*
  - Created by Kevin Thai-Khanh Vu on 2021.11.30
  - Copyright © 2021 Kevin Thai-Khanh Vu. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserQuestionnaire;
import edu.vt.FacadeBeans.UserQuestionnaireFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Map;

import edu.vt.pojo.Question;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

@Named("userQuestionnaireController")
@SessionScoped
public class UserQuestionnaireController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    @EJB
    private UserQuestionnaireFacade UserQuestionnaireFacade;

    // 'items' is a List containing the object references of UserQuestionnaire objects
    private List<UserQuestionnaire> items = null;

    // 'selected' contains the object reference of the selected UserQuestionnaire object
    private UserQuestionnaire selected;

    // 'questions' is a List containing the object references of Category objects
    private List<Question> questions = null;

    // 'listOfQuestionnaires' is a List containing all Questionnaires in the database
    private List<UserQuestionnaire> listOfQuestionnaires = null;

    private String answerToSecurityQuestion;

    public List<UserQuestionnaire> getListOfQuestionnaires() {
        listOfQuestionnaires = UserQuestionnaireFacade.findAll();
        return listOfQuestionnaires;
    }
    public void setListOfUsers(List<UserQuestionnaire> listOfQuestionnaires) {
        this.listOfQuestionnaires = listOfQuestionnaires;
    }

    /*
    ***************************************************************
    Return the List of UserQuestionnaires Created by the Signed-In User
    ***************************************************************
     */
    public List<UserQuestionnaire> getItems() {
        if (items == null) {
            /*
            user_id (database primary key) was put into the SessionMap in the
            initializeSessionMap() method in LoginManager upon user's sign in.
             */
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            int userPrimaryKey = (int) sessionMap.get("user_id");

            items = UserQuestionnaireFacade.findUserQuestionnairesByUserPrimaryKey(userPrimaryKey);
        }
        return items;
    }

    public void setItems(List<UserQuestionnaire> items) {
        this.items = items;
    }

    public UserQuestionnaire getSelected() {
        return selected;
    }

    public void setSelected(UserQuestionnaire selected) {
        questions = null;    // Invalidate list of Question items to trigger re-query.
        this.selected = selected;
    }

    public List<Question> getQuestions() {
        if (questions == null) {

            questions = new ArrayList<>();

            String UserQuestionnaire = selected.getQuestionnaire();
            JSONArray jsonArray = new JSONArray(UserQuestionnaire);

            /*
            ======================================================================
            The jsonArray obtained from the 'UserQuestionnaire' contains JSON objects.
            Each JSON object has the following KEY-VALUE pairings:
            [
                {
                    "questionNumber":"1",
                    "questionTitle":"How many days do you want to exercise?",
                    "questionAnswer":"7",
                },
                :
                :
            ]
            ======================================================================
             */
            jsonArray.forEach(object -> {
                // Typecast the object as JSONObject
                JSONObject jsonObject = (JSONObject) object;

                Integer questionNumber = jsonObject.getInt("questionNumber");
                String questionTitle = jsonObject.getString("questionTitle");
                String questionAnswer = jsonObject.getString("questionAnswer");

                // Create a Category object using the attributes (Key-Value pairs) of the jsonObject
                Question Questionnaires = new Question(questionNumber, questionTitle, questionAnswer);

                // Add newly created Category object to the ArrayList
                questions.add(Questionnaires);
            });
        }
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getAnswerToSecurityQuestion() {
        return answerToSecurityQuestion;
    }

    public void setAnswerToSecurityQuestion(String answerToSecurityQuestion) {
        this.answerToSecurityQuestion = answerToSecurityQuestion;
    }

    /*
    ================
    Instance Methods
    ================

    *****************************************************
    Process the Submitted Answer to the Security Question
    *****************************************************
     */
    public void securityAnswerSubmit() {
        /*
        'user', the object reference of the signed-in user, was put into the SessionMap
        in the initializeSessionMap() method in LoginManager upon user's sign in.
         */
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        String actualSecurityAnswer = signedInUser.getSecurityAnswer();
        String enteredSecurityAnswer = getAnswerToSecurityQuestion();

        if (actualSecurityAnswer.equals(enteredSecurityAnswer)) {
            // Answer to the security question is correct. Delete the selected UserQuestionnaire.
            deleteUserQuestionnaire();

        } else {
            Methods.showMessage("Error",
                    "Answer to the Security Question is Incorrect!", "");
        }
    }

    /*
    *************************************
    Prepare to Create a New UserQuestionnaire
    *************************************
     */
    public void prepareCreate() {
        /*
        Instantiate a new UserQuestionnaire object and store its object reference into instance
        variable 'selected'. The UserQuestionnaire class is defined in UserQuestionnaire.java
         */
        selected = new UserQuestionnaire();
    }

    /*
    ******************************************
    Create a New UserQuestionnaire in the Database
    ******************************************
     */
    public void create() {
        Methods.preserveMessages();

        persist(PersistAction.CREATE,"Thank You! Your Questionnaire was Successfully Saved in the Database!");
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /*
    ***************************************************
    Delete the Selected UserQuestionnaire from the Database
    ***************************************************
     */
    public void deleteUserQuestionnaire() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        Integer primaryKey = signedInUser.getId();

        List<UserQuestionnaire> questionnaireList = UserQuestionnaireFacade.findUserQuestionnairesByUserPrimaryKey(primaryKey);
        if (!questionnaireList.isEmpty()) {

            // Obtain the object reference of the first Photo object in the list
            UserQuestionnaire questionnaire = questionnaireList.get(0);
            selected = null;
            items = null;
        }
        Methods.preserveMessages();
        /*
        Show the message "The UserQuestionnaire was Successfully Deleted from the Database!"
        given in the Bundle.properties file under the UserQuestionnaireDeleted keyword.
        
        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.DELETE,"The UserQuestionnaire was Successfully Deleted from the Database!\n");
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /*
     ****************************************************************************
     *   Perform CREATE, EDIT (UPDATE), and DELETE Operations in the Database   *
     ****************************************************************************
     */
    /**
     * @param persistAction refers to CREATE, UPDATE (Edit) or DELETE action
     * @param successMessage displayed to inform the user about the result
     */
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    /*
                     -------------------------------------------------
                     Perform CREATE or EDIT operation in the database.
                     -------------------------------------------------
                     The edit(selected) method performs the SAVE (STORE) operation of the "selected"
                     object in the database regardless of whether the object is a newly
                     created object (CREATE) or an edited (updated) object (EDIT or UPDATE).
                    
                     UserQuestionnaireFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    UserQuestionnaireFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove method performs the DELETE operation in the database.
                    
                     UserQuestionnaireFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    UserQuestionnaireFacade.remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);

            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex,"A persistence error occurred.");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex,"A persistence error occurred.");
            }
        }
    }

    /*
    ***************************************************
    Type Converter Methods for PrimeFaces Data Exporter 
    Function to Export Data into PDF, Excel, and CSV
    ***************************************************
    
    PrimeFaces p:dataExporter requires the exported values to be of String type.
    These methods are called in UserQuestionnaireDetails.xhtml.
     */
    public String convertIntToString(Integer value) {
        return Integer.toString(value);
    }

}
