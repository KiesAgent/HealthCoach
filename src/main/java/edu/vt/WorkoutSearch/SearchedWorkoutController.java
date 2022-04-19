package edu.vt.WorkoutSearch;

import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONException;
import org.primefaces.shaded.json.JSONObject;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@SessionScoped
@Named("searchedWorkoutController")
public class SearchedWorkoutController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    // Provided by the User
    private Integer searchField_Equipment;
    private Integer searchField_Category;
    private Integer searchField_Muscle;
    private Integer searchField_MaxNumberOfWorkouts;

    // ArrayList of obtained SearchedWorkout
    private ArrayList<SearchedWorkout> listOfSearchedWorkouts;

    private SearchedWorkout selected;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public Integer getSearchField_Equipment() {
        return searchField_Equipment;
    }

    public void setSearchField_Equipment(Integer searchField_Equipment) {
        this.searchField_Equipment = searchField_Equipment;
    }

    public Integer getSearchField_Category() {
        return searchField_Category;
    }

    public void setSearchField_Category(Integer searchField_Category) {
        this.searchField_Category = searchField_Category;
    }

    public Integer getSearchField_Muscle() {
        return searchField_Muscle;
    }

    public void setSearchField_Muscle(Integer searchField_Muscle) {
        this.searchField_Muscle = searchField_Muscle;
    }

    public Integer getSearchField_MaxNumberOfWorkouts() {
        return searchField_MaxNumberOfWorkouts;
    }

    public void setSearchField_MaxNumberOfWorkouts(Integer searchField_MaxNumberOfWorkouts) {
        this.searchField_MaxNumberOfWorkouts = searchField_MaxNumberOfWorkouts;
    }

    public ArrayList<SearchedWorkout> getListOfSearchedWorkouts() {
        return listOfSearchedWorkouts;
    }

    public void setListOfSearchedWorkouts(ArrayList<SearchedWorkout> listOfSearchedWorkouts) {
        this.listOfSearchedWorkouts = listOfSearchedWorkouts;
    }

    public SearchedWorkout getSelected() {
        return selected;
    }

    public void setSelected(SearchedWorkout selected) {
        this.selected = selected;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public String performSearch() throws Exception {

        selected = null;
        listOfSearchedWorkouts = new ArrayList<>();

        /*
        Redirecting to show a JSF page involves more than one subsequent requests and
        the messages would die from one request to another if not kept in the Flash scope.
        Since we will redirect to show the search Results page, we invoke preserveMessages().
         */
        Methods.preserveMessages();

        /*
        JSON uses the following notation:
        { }    represents a JavaScript object as a Dictionary with Key:Value pairs
        [ ]    represents Array
        [{ }]  represents an Array of JavaScript objects (dictionaries)
          :    separates Key from the Value
         */
        try {
            
            // Base Search Query URL
            String wgerApiUrl = Constants.WGER_SEARCH_API_BASE_URL;
            
            // If any of these options are selected, the Search Query URL will be modified
            if (searchField_Category != 1000) {
                wgerApiUrl += Constants.WGER_CATEGORY + searchField_Category;
            }

            if (searchField_Equipment != 1000) {
                wgerApiUrl += Constants.WGER_EQUIPMENT + searchField_Equipment;
            }

            if (searchField_Muscle != 1000) {
                wgerApiUrl += Constants.WGER_MUSCLE + searchField_Muscle;
            }

            if (searchField_MaxNumberOfWorkouts != 1000) {
                wgerApiUrl += Constants.WGER_LIMIT + searchField_MaxNumberOfWorkouts;
            }
            
            // Obtain the JSON file from the searchApiUrl
            String searchResultsJsonData = Methods.readUrlContent(wgerApiUrl);

            /*
            https://wger.de/api/v2/exercise/?language=2&format=json&equipment=1&muscles=14&category=10&limit=5 returns the following JSON data:
            [
                {


                    count:	                    1
                    next:	                    null
                    previous:	                null
                    results:
                        0:
                            id:	                343
                            uuid:	            "1b9dc5bc-790b-4e21-a55d-f8b3115e94c5"
                            name:	            "Barbell Ab Rollout"
                            exercise_base:	    41
                            status:	            "2"
                            description:	    "<p>Place a barbell on the floor at your feet.</p>\n<p>Bending at the waist, grip the barbell with a shoulder with overhand grip.</p>\n<p>With a slow controlled motion, roll the bar out so that your back is straight.</p>\n<p>Roll back up raising your hips and butt as you return to the starting position.</p>"
                            creation_date:	    "2015-07-27"
                            category:	        10
                            muscles:
                                0:	            14
                            muscles_secondary:  []
                            equipment:
                                0:	            1
                            language:	        2
                            license:	        2
                            license_author:	    "sevae"
                            variations:	        []
                }
            ]
           */

            // The file returned from the API is in the form of a JSON object
            // Create a new JSON object from the returned file
            JSONObject searchResultsJsonObject = new JSONObject(searchResultsJsonData);

            // Obtain the array of workout JSON objects from the key "results"
            JSONArray jsonArrayFoundWorkouts = searchResultsJsonObject.getJSONArray("results");

            int index = 0;

            if (jsonArrayFoundWorkouts.length() > index) {

                while (jsonArrayFoundWorkouts.length() > index) {

                    // Get the JSONObject at index
                    JSONObject foundWorkout = jsonArrayFoundWorkouts.getJSONObject(index);

                    /*
                     ===================
                     Workout Name (Name)
                     ===================
                     */
                    String name = foundWorkout.optString("name", "");
                    if (name.equals("")) {
                        // Skip the workout with no name (name)
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                    ================
                    Workout Category
                    ================
                    */
                    String tempCategory = foundWorkout.optString("category");

                    int convert = Integer.parseInt(tempCategory);
                    String category = Constants.categories[convert - 8];
                    //String category = categoryObject.optString("name");
                    if (category.equals("")) {
                        // Skip the workout with no category
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ===============
                     Workout Muscles
                     ===============
                     */
                    JSONArray workoutMusclesAsArray = foundWorkout.getJSONArray("muscles");
                    String muscles = "";
                    if (workoutMusclesAsArray.isEmpty()) {
                        // Do nothing
                    }
                    else {
                        int workoutMusclesArrayLength = workoutMusclesAsArray.length();

                        if (workoutMusclesArrayLength > 0) {
                            for (int j = 0; j < workoutMusclesArrayLength; j++) {
                                String aMuscle = workoutMusclesAsArray.optString(j, "");
                                if (aMuscle.equals("")) {
                                    // Do nothing
                                }
                                else {
                                    int convert2 = Integer.parseInt(aMuscle);
                                    muscles += Constants.muscles[convert2 - 1];
                                    if (j < workoutMusclesArrayLength - 1) {
                                        muscles += ", ";
                                    }
                                }
                            }
                        }
                    }

                    /*
                     ==================
                     Workout Equipments
                     ==================
                     */
                    JSONArray equipmentsAsArray = foundWorkout.getJSONArray("equipment");
                    String equipments = "";
                    if (equipmentsAsArray.isEmpty()) {
                        // Do nothing
                    }
                    else {
                        int equipmentsArrayLength = equipmentsAsArray.length();

                        if (equipmentsArrayLength > 0) {
                            for (int j = 0; j < equipmentsArrayLength; j++) {
                                String anEquipment = equipmentsAsArray.optString(j, "");
                                if (anEquipment.equals("")) {
                                    // Do nothing
                                }
                                else {
                                    int convert2 = Integer.parseInt(anEquipment);
                                    equipments += Constants.equipments[convert2 - 1];
                                    if (j < equipmentsArrayLength - 1) {
                                        equipments += equipments + ", ";
                                    }
                                }
                            }
                        }
                    }

                    /*
                    =================
                    Workout Image_URL
                    =================
                    */
                    String image_url = "";

                    // Queries a new temporary search because this API is a "work-in-progress"
                    String tempSearchQuery = "https://wger.de/api/v2/exercise/search/?format=json&term=" + name;

                    // Replaces all empty spaces with "+"
                    tempSearchQuery = tempSearchQuery.replaceAll(" ", "+");

                    // Obtains the JSON file from tempSearchQuery
                    String tempSearchResultsJsonData = Methods.readUrlContent(tempSearchQuery);

                    // The file returned from the API is in the form of a JSON object
                    // Creates a new JSON object from the returned file
                    JSONObject tempSearchResultsJsonObject = new JSONObject(tempSearchResultsJsonData);

                    // Obtains the array of workout JSON objects from the key "suggestions"
                    JSONArray jsonArrayTempWorkouts = tempSearchResultsJsonObject.getJSONArray("suggestions");
                    
                    int tempIndex = 0;
                    if (jsonArrayTempWorkouts.length() > tempIndex) {
                        while (jsonArrayTempWorkouts.length() > tempIndex) {
                            // Gets the JSONObject at index
                            JSONObject foundTempWorkout = jsonArrayFoundWorkouts.getJSONObject(tempIndex);

                            // Gets the "value" or name of the temporary workout
                            String value = foundTempWorkout.optString("value","");

                            // If the name of the temporary workout matches with the name of the main workout
                            if (value.equals(name)) {
                                // Gets the JSONObject from "data"
                                JSONObject dataObject = foundTempWorkout.getJSONObject("data");

                                // Extracts the image_url associated with the temporary workout
                                String temp_imageURL = dataObject.optString("image",null);

                                // If the image_url is null
                                if (temp_imageURL == null) {
                                    // Immediately terminates the loop
                                    break;
                                }
                                else {
                                    image_url = "https://wger.de" + temp_imageURL;
                                }
                            }

                            // Increments tempIndex
                            tempIndex++;
                        }
                    }

                    // After all that and still nothing
                    if (image_url.equals("")) {
                        image_url = "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/muscular-build-athlete-having-cross-training-in-a-royalty-free-image-1618930811.?resize=980:*";
                    }

                    /*
                     ===================
                     Workout Description
                     ===================
                     */
                    String description = foundWorkout.optString("description", "");

                    /*
                     ==========================================================
                     Create a new Workout object dressed up with its Attributes
                     ==========================================================
                     */
                    SearchedWorkout workout = new SearchedWorkout(name, category, muscles,
                            equipments, image_url, description);

                    // Add the newly created workout object to the list of SearchedWorkouts
                    listOfSearchedWorkouts.add(workout);
                    index++;
                }
            } else {
                // No workout found for the search query
                Methods.showMessage("Information", "No Results!", "No workout found for the search query!");
            }

        } catch (JSONException ex) {
            Methods.showMessage("Information", "Exception!", "An Exception Occurred!");
            ex.printStackTrace();
        }

        searchField_Category = null;
        searchField_Muscle = null;
        searchField_Equipment = null;
        searchField_MaxNumberOfWorkouts = null;
        return "/workoutSearch/WorkoutSearchResults?faces-redirect=true";
    }

    public void clearSearchFields() {
        searchField_Category = null;
        searchField_Muscle = null;
        searchField_Equipment = null;
        searchField_MaxNumberOfWorkouts = null;
    }
}
