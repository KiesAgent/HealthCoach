package edu.vt.controllers;

import edu.vt.EntityBeans.PublicWorkout;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserRecipe;
import edu.vt.EntityBeans.UserWorkout;
import edu.vt.FacadeBeans.PublicWorkoutFacade;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
---------------------------------------------------------------------------
The @Named (javax.inject.Named) annotation indicates that the objects
instantiated from this class will be managed by the Contexts and Dependency
Injection (CDI) container. The name "recipeController" is used within
Expression Language (EL) expressions in JSF (XHTML) facelets pages to
access the properties and invoke methods of this class.
---------------------------------------------------------------------------
 */
@Named("publicWorkoutController")

/*
The @SessionScoped annotation preserves the values of the RecipeController
object's instance variables across multiple HTTP request-response cycles
as long as the user's established HTTP session is alive.
 */
@SessionScoped

/*
-----------------------------------------------------------------------------
Marking the RecipeController class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized.

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer,
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
-----------------------------------------------------------------------------
 */
public class PublicWorkoutController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
    */

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    RecipeFacade bean into the instance variable 'recipeFacade' after it is instantiated at runtime.
     */
    @EJB
    private PublicWorkoutFacade publicWorkoutFacade;

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    RecipeFacade bean into the instance variable 'recipeFacade' after it is instantiated at runtime.
     */
    @EJB
    private UserWorkoutFacade userWorkoutFacade;

    /*
    The @Inject annotation directs the CDI Container Manager to inject (store) the object reference of the
    CDI container-managed UserVideoController bean into the instance variable 'userVideoController' after it is instantiated at runtime.
     */
    @Inject
    private UserWorkoutController userWorkoutController;


    // List of object references of Recipe objects
    private List<PublicWorkout> listOfPublicWorkouts = null;

    // selected = object reference of a selected Recipe object
    private PublicWorkout selected;

    // Flag indicating if recipe data changed or not
    private Boolean recipeDataChanged;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public PublicWorkoutFacade getPublicWorkoutFacade() {
        return publicWorkoutFacade;
    }

    public void setPublicWorkoutFacade(PublicWorkoutFacade publicWorkoutFacade) {
        this.publicWorkoutFacade = publicWorkoutFacade;
    }

    public List<PublicWorkout> getListOfPublicWorkouts() {
        if (listOfPublicWorkouts == null) {
            listOfPublicWorkouts = publicWorkoutFacade.findAll();
        }
        return listOfPublicWorkouts;
    }

    public PublicWorkout getSelected() {
        return selected;
    }

    public void setSelected(PublicWorkout selected) {
        this.selected = selected;
    }

    public Boolean getRecipeDataChanged() { return recipeDataChanged; }

    public void setRecipeDataChanged(Boolean recipeDataChanged) { this.recipeDataChanged = recipeDataChanged; }

    /*
    ================
    Instance Methods
    ================
     */

    /*
     ***************************************
     *   Unselect Selected Recipe Object   *
     ***************************************
     */
    public void unselect() {
        selected = null;
    }

    /*
     *************************************
     *   Cancel and Display PublicWorkoutList.xhtml   *
     *************************************
     */
    public String cancel() {
        // Unselect previously selected recipe object if any
        selected = null;
        return "/publicRecipes/List?faces-redirect=true";
    }

    /*
     ***************************************
     *   Prepare to Create a New Recipe   *
     ***************************************
     */
    public void prepareCreate() {
        /*
        Instantiate a new Recipe object and store its object reference into
        instance variable 'selected'. The Recipe class is defined in Recipe.java
         */
        selected = new PublicWorkout();
    }

    /*
     ********************************************
     *   CREATE a New Recipe in the Database   *
     ********************************************
     */
    public void create() {
        Methods.preserveMessages();

        /*
        An enum is a special Java type used to define a group of constants.
        The constants CREATE, DELETE and UPDATE are defined as follows in JsfUtil.java

                public enum PersistAction {
                    CREATE,
                    DELETE,
                    UPDATE
                }
         */

        /*
         The object reference of the recipe to be created is stored in the instance variable 'selected'
         See the persist method below.
         */
        persist(PersistAction.CREATE, "Recipe was Successfully Created!");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;            // Remove selection
            listOfPublicWorkouts = null;       // Invalidate listOfRecipes to trigger re-query.
            recipeDataChanged = true;
        }
    }

    /*
     ***************************************
     *   SHARE a Workout in the Database   *
     ***************************************
     */
    public void share() {
        Methods.preserveMessages();
        // Obtain the object reference of the signed-in User object
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        if (sessionMap.get("username") == null) {
            Methods.showMessage("Information","Unable to Share!","To share a workout, a user must have signed in!");
        }
        else {
            /*
            Instantiate a new UserWorkout object and store its attributes as
            the attributes from the selected PublicVideo
            */
            UserWorkout workoutBeingShared = new UserWorkout();

            workoutBeingShared.setName(selected.getName());
            workoutBeingShared.setDescription(selected.getDescription());
            workoutBeingShared.setEquipment(selected.getEquipment());
            workoutBeingShared.setMuscle(selected.getMuscle());
            workoutBeingShared.setCategory(selected.getCategory());
            workoutBeingShared.setUserId(signedInUser);
            workoutBeingShared.setImageURL(selected.getImageURL());

            userWorkoutFacade.edit(workoutBeingShared);
            userWorkoutController.refreshFileList();
            JsfUtil.addSuccessMessage("User Workout was successfully created.");
        }
    }

    /*
     **********************************************
     *   UPDATE Selected Recipe in the Database   *
     **********************************************
     */
    public void update() {
        Methods.preserveMessages();
        /*
         The object reference of the recipe to be updated is stored in the instance variable 'selected'
         See the persist method below.
         */
        persist(PersistAction.UPDATE, "Workout was Successfully Updated!");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The UPDATE operation is successfully performed.
            selected = null;            // Remove selection
            listOfPublicWorkouts = null;       // Invalidate listOfRecipes to trigger re-query.
            recipeDataChanged = true;
        }
    }

    /*
     *************************************************
     *   DELETE Selected Recipe from the Database   *
     *************************************************
     */
    public void destroy() {
        Methods.preserveMessages();
        /*
         The object reference of the recipe to be deleted is stored in the instance variable 'selected'
         See the persist method below.
         */
        persist(PersistAction.DELETE, "Recipe was Successfully Deleted!");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;            // Remove selection
            listOfPublicWorkouts = null;       // Invalidate listOfRecipes to trigger re-query.
            recipeDataChanged = true;
        }
    }

    /*
     **********************************************************************************************
     *   Perform CREATE, UPDATE (EDIT), and DELETE (DESTROY, REMOVE) Operations in the Database   *
     **********************************************************************************************
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

                     RecipeFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    publicWorkoutFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     RecipeFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    publicWorkoutFacade.remove(selected);
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
                    JsfUtil.addErrorMessage(ex, "A persistence error occurred!");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "A persistence error occurred");
            }
        }
    }
}
