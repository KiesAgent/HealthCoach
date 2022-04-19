/*
 * Created by Sean Fleming on 2021.10.19
 */
package edu.vt.RecipeSearch;

import edu.vt.globals.Methods;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@SessionScoped
@Named("searchedRecipeController")

public class SearchedRecipeController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    // Edamam API Information
    private String edamamAPISearchRecipesBaseUrl = "https://api.edamam.com/search?q=";
    private String edamamAPIAppIDandKey = "&app_id=eefd9c8e&app_key=f05cc35efd1d2e6c98e028c2cf5c4c7c";

    // Provided by the User
    private String searchQuery;
    private String dietLabel;
    private String healthLabel;
    private Integer maxNumberOfRecipes;
    private Integer minCalories;
    private Integer maxCalories;
    private Integer minProtein;
    private Integer maxCarbs;
    private Integer maxFats;

    // Used for Search Processing
    private String edamamApiUrl;

    // Current recipe being searched
    private SearchedRecipe selected;

    // All recipes searched
    private ArrayList<SearchedRecipe> searchedRecipes;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getEdamamAPISearchRecipesBaseUrl() {
        return edamamAPISearchRecipesBaseUrl;
    }

    public void setEdamamAPISearchRecipesBaseUrl(String edamamAPISearchRecipesBaseUrl) {
        this.edamamAPISearchRecipesBaseUrl = edamamAPISearchRecipesBaseUrl;
    }

    public String getEdamamAPIAppIDandKey() {
        return edamamAPIAppIDandKey;
    }

    public void setEdamamAPIAppIDandKey(String edamamAPIAppIDandKey) {
        this.edamamAPIAppIDandKey = edamamAPIAppIDandKey;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getDietLabel() {
        return dietLabel;
    }

    public void setDietLabel(String dietLabel) {
        this.dietLabel = dietLabel;
    }

    public String getHealthLabel() {
        return healthLabel;
    }

    public Integer getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(Integer minCalories) {
        this.minCalories = minCalories;
    }

    public Integer getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(Integer maxCalories) {
        this.maxCalories = maxCalories;
    }

    public void setHealthLabel(String healthLabel) {
        this.healthLabel = healthLabel;
    }

    public Integer getMinProtein() {
        return minProtein;
    }

    public void setMinProtein(Integer minProtein) {
        this.minProtein = minProtein;
    }

    public Integer getMaxCarbs() {
        return maxCarbs;
    }

    public void setMaxCarbs(Integer maxCarbs) {
        this.maxCarbs = maxCarbs;
    }

    public Integer getMaxFats() {
        return maxFats;
    }

    public void setMaxFats(Integer maxFats) {
        this.maxFats = maxFats;
    }

    public Integer getMaxNumberOfRecipes() {
        return maxNumberOfRecipes;
    }

    public void setMaxNumberOfRecipes(Integer maxNumberOfRecipes) {
        this.maxNumberOfRecipes = maxNumberOfRecipes;
    }

    public String getEdamamApiUrl() {
        return edamamApiUrl;
    }

    public void setEdamamApiUrl(String edamamApiUrl) {
        this.edamamApiUrl = edamamApiUrl;
    }

    public SearchedRecipe getSelected() {
        return selected;
    }

    public void setSelected(SearchedRecipe selected) {
        this.selected = selected;
    }

    public ArrayList<SearchedRecipe> getSearchedRecipes() {
        return searchedRecipes;
    }

    public void setSearchedRecipes(ArrayList<SearchedRecipe> searchedRecipes) {
        this.searchedRecipes = searchedRecipes;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public String performBasicSearch() {

        selected = null;

        // This sets the necessary flag to ensure the messages are preserved.
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        searchedRecipes = new ArrayList<>();

        // Spaces in search query must be replaced with "+"
        searchQuery = searchQuery.replaceAll(" ", "+");

        try {
            String edamamApiUrl = edamamAPISearchRecipesBaseUrl + searchQuery + edamamAPIAppIDandKey
                    + "&from=0&to=" + maxNumberOfRecipes + "&diet=" + dietLabel + "&health=" + healthLabel;

            // Obtain the JSON file (String of characters) containing the search results
            // The readUrlContent() method is given below
            String searchResultsJsonData = Methods.readUrlContent(edamamApiUrl);

            // The file returned from the API is in the form of a JSON object
            // Create a new JSON object from the returned file
            JSONObject searchResultsJsonObject = new JSONObject(searchResultsJsonData);

            // Obtain the array of recipe JSON objects from the key "hits"
            JSONArray jsonArrayFoundRecipes = searchResultsJsonObject.getJSONArray("hits");

            int index = 0;

            if (jsonArrayFoundRecipes.length() > index) {

                while (jsonArrayFoundRecipes.length() > index) {
                    // Get the JSONObject at index
                    JSONObject jsonObject = jsonArrayFoundRecipes.getJSONObject(index);

                    JSONObject foundRecipe = jsonObject.getJSONObject("recipe");

                    /*
                     ===================
                     Recipe Name (Label)
                     ===================
                     */
                    String name = foundRecipe.optString("label", "");
                    if (name.equals("")) {
                        // Skip the recipe with no name (label)
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ================
                     Recipe Image URL
                     ================
                     */
                    String imageURL = foundRecipe.optString("image", "");
                    if (imageURL.equals("")) {
                        // Skip the recipe with no image URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     =====================
                     Recipe Publisher Name
                     =====================
                     */
                    String publisherName = foundRecipe.optString("source", "");
                    if (publisherName.equals("")) {
                        publisherName = "Publisher Unknown";
                    }

                    /*
                     ==================
                     Recipe Website URL
                     ==================
                     */
                    String recipeURL = foundRecipe.optString("url", "");
                    if (recipeURL.equals("")) {
                        // Skip the recipe with no website URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ==================
                     Recipe Diet Labels
                     ==================
                     */
                    JSONArray dietLabelsAsArray = foundRecipe.getJSONArray("dietLabels");

                    String dietLabels = "";
                    int dietLabelsArrayLength = dietLabelsAsArray.length();

                    if (dietLabelsArrayLength > 0) {
                        for (int j = 0; j < dietLabelsArrayLength; j++) {
                            String aDietLabel = dietLabelsAsArray.optString(j, "");
                            if (j < dietLabelsArrayLength - 1) {
                                aDietLabel = aDietLabel + ", ";
                            }
                            dietLabels = dietLabels + aDietLabel;
                        }
                    }

                    /*
                     ====================
                     Recipe Health Labels
                     ====================
                     */
                    JSONArray healthLabelsAsArray = foundRecipe.getJSONArray("healthLabels");

                    String healthLabels = "";
                    int healthLabelsArrayLength = healthLabelsAsArray.length();

                    if (healthLabelsArrayLength > 0) {
                        for (int j = 0; j < healthLabelsArrayLength; j++) {
                            String aHealthLabel = healthLabelsAsArray.optString(j, "");
                            if (j < healthLabelsArrayLength - 1) {
                                aHealthLabel = aHealthLabel + ", ";
                            }
                            healthLabels = healthLabels + aHealthLabel;
                        }
                    }

                    /*
                     =======================
                     Recipe Ingredient Lines
                     =======================
                     */
                    JSONArray ingredientLinesAsArray = foundRecipe.getJSONArray("ingredientLines");

                    String ingredientLines = "";
                    int ingredientLinesArrayLength = ingredientLinesAsArray.length();

                    if (ingredientLinesArrayLength > 0) {
                        for (int j = 0; j < ingredientLinesArrayLength; j++) {
                            String anIngredientLine = ingredientLinesAsArray.optString(j, "");
                            if (j < ingredientLinesArrayLength - 1) {
                                anIngredientLine = anIngredientLine + ", ";
                            }
                            ingredientLines = ingredientLines + anIngredientLine;
                        }
                    }

                    /*
                     ===============
                     Recipe Category
                     ===============
                     */
                    JSONArray categoryAsArray = foundRecipe.getJSONArray("dishType");

                    String category = categoryAsArray.optString(0, "");
                    if (category.equals("")) {
                        // Skip the recipe with no website URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ==============
                     Recipe Cuisine
                     ==============
                     */
                    JSONArray cuisineAsArray = foundRecipe.getJSONArray("cuisineType");

                    String cuisine = cuisineAsArray.optString(0, "");
                    if (cuisine.equals("")) {
                        // Skip the recipe with no website URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ======================
                     Recipe Nutrition Facts
                     ======================
                     */
                    JSONObject nutritionObject = foundRecipe.getJSONObject("totalNutrients");

                    /*
                    Servings
                     */
                    Integer servings = foundRecipe.optInt("yield", 0);
                    if (servings == 0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    }
                    String servingsString = servings.toString();

                    /*
                    Calories
                     */
                    Double calories = foundRecipe.optDouble("calories", 0.0);
                    if (calories == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        calories = calories * 100;
                        calories = (double) Math.round(calories);
                        calories = calories / 100;
                    }
                    String caloriesString = calories.toString();

                    /*
                    Total Fat
                     */
                    JSONObject fatObject = nutritionObject.getJSONObject("FAT");
                    Double totalFat = fatObject.optDouble("quantity", 0);
                    if (totalFat == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        totalFat = totalFat * 100;
                        totalFat = (double) Math.round(totalFat);
                        totalFat = totalFat / 100;
                    }
                    String totalFatString = totalFat.toString();

                    /*
                    Saturated Fat
                     */
                    JSONObject satFatObject = nutritionObject.getJSONObject("FASAT");
                    Double totalSatFat = satFatObject.optDouble("quantity", 0);
                    if (totalSatFat == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        totalSatFat = totalSatFat * 100;
                        totalSatFat = (double) Math.round(totalSatFat);
                        totalSatFat = totalSatFat / 100;
                    }
                    String totalSatFatString = totalSatFat.toString();

                    /*
                    Cholesterol
                     */
                    JSONObject cholesterolObject = nutritionObject.getJSONObject("CHOLE");
                    Double cholesterol = cholesterolObject.optDouble("quantity", 0);
                    if (totalFat == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        cholesterol = cholesterol * 100;
                        cholesterol = (double) Math.round(cholesterol);
                        cholesterol = cholesterol / 100;
                    }
                    String cholesterolString = cholesterol.toString();

                    /*
                    Sodium
                     */
                    JSONObject sodiumObject = nutritionObject.getJSONObject("NA");
                    Double sodium = sodiumObject.optDouble("quantity", 0);
                    if (sodium == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        sodium = sodium * 100;
                        sodium = (double) Math.round(sodium);
                        sodium = sodium / 100;
                    }
                    String sodiumString = sodium.toString();

                    /*
                    Total Carbs
                     */
                    JSONObject carbsObject = nutritionObject.getJSONObject("CHOCDF");
                    Double carbs = carbsObject.optDouble("quantity", 0);
                    if (carbs == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        carbs = carbs * 100;
                        carbs = (double) Math.round(carbs);
                        carbs = carbs / 100;
                    }
                    String carbsString = carbs.toString();

                    /*
                    Fiber
                     */
                    JSONObject fiberObject = nutritionObject.getJSONObject("FIBTG");
                    Double fiber = fiberObject.optDouble("quantity", 0);
                    if (fiber == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        fiber = fiber * 100;
                        fiber = (double) Math.round(fiber);
                        fiber = fiber / 100;
                    }
                    String fiberString = fiber.toString();

                    /*
                    Sugars
                     */
                    JSONObject sugarObject = nutritionObject.getJSONObject("SUGAR");
                    Double sugar = sugarObject.optDouble("quantity", 0);
                    if (sugar == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        sugar = sugar * 100;
                        sugar = (double) Math.round(sugar);
                        sugar = sugar / 100;
                    }
                    String sugarString = sugar.toString();

                    /*
                    Protein
                     */
                    JSONObject proteinObject = nutritionObject.getJSONObject("PROCNT");
                    Double protein = proteinObject.optDouble("quantity", 0);
                    if (protein == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        protein = protein * 100;
                        protein = (double) Math.round(protein);
                        protein = protein / 100;
                    }
                    String proteinString = protein.toString();

                    String nutrients = "Servings: " + servingsString + ", Calories: " + caloriesString + ", Total Fat: " + totalFatString + " grams, Saturated Fat: "
                            + totalSatFatString + " grams, Cholesterol: " + cholesterolString + " milligrams, Sodium: " + sodiumString +
                            " milligrams, Total Carbohydrate: " + carbsString + " grams, Dietary Fiber: " + fiberString + " grams, Sugars: "
                            + sugarString + " grams, Protein: " + proteinString + " grams";

                    /*
                     =========================================================
                     Create a new Recipe object dressed up with its Attributes
                     =========================================================
                     */
                    SearchedRecipe recipe = new SearchedRecipe(name, category, cuisine, recipeURL, imageURL,
                            publisherName, recipeURL, ingredientLines, nutrients, dietLabels, healthLabels);

                    // Add the newly created recipe object to the list of searchedRecipes
                    searchedRecipes.add(recipe);
                    index++;
                }
            } else {
                // No recipe found for the search query
                Methods.showMessage("Information", "No Results!", "No recipe found for the search query!");
            }

        } catch (Exception ex) {
            Methods.showMessage("Information", "No Results!", "No recipe found for the search query!");
        }

        searchQuery = "";
        return "/recipeSearch/SearchResults?faces-redirect=true";
    }

    public String performAdvancedSearch() {

        selected = null;

        // This sets the necessary flag to ensure the messages are preserved.
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        searchedRecipes = new ArrayList<>();

        // Spaces in search query must be replaced with "+"
        searchQuery = searchQuery.replaceAll(" ", "+");

        try {
            String edamamApiUrl = edamamAPISearchRecipesBaseUrl + searchQuery + edamamAPIAppIDandKey
                    + "&from=0&to=" + maxNumberOfRecipes + "&diet=" + dietLabel + "&health=" + healthLabel
                    + "&calories=" + minCalories + "-" + maxCalories + "&nutrients%5BCHOCDF%5D=" + maxCarbs
                    + "&nutrients%5BFAT%5D=" + maxFats + "&nutrients%5BPROCNT%5D=" + minProtein + "%2B";

            // Obtain the JSON file (String of characters) containing the search results
            // The readUrlContent() method is given below
            String searchResultsJsonData = Methods.readUrlContent(edamamApiUrl);

            // The file returned from the API is in the form of a JSON object
            // Create a new JSON object from the returned file
            JSONObject searchResultsJsonObject = new JSONObject(searchResultsJsonData);

            // Obtain the array of recipe JSON objects from the key "hits"
            JSONArray jsonArrayFoundRecipes = searchResultsJsonObject.getJSONArray("hits");

            int index = 0;

            if (jsonArrayFoundRecipes.length() > index) {

                while (jsonArrayFoundRecipes.length() > index) {
                    // Get the JSONObject at index
                    JSONObject jsonObject = jsonArrayFoundRecipes.getJSONObject(index);

                    JSONObject foundRecipe = jsonObject.getJSONObject("recipe");

                    /*
                     ===================
                     Recipe Name (Label)
                     ===================
                     */
                    String name = foundRecipe.optString("label", "");
                    if (name.equals("")) {
                        // Skip the recipe with no name (label)
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ================
                     Recipe Image URL
                     ================
                     */
                    String imageURL = foundRecipe.optString("image", "");
                    if (imageURL.equals("")) {
                        // Skip the recipe with no image URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     =====================
                     Recipe Publisher Name
                     =====================
                     */
                    String publisherName = foundRecipe.optString("source", "");
                    if (publisherName.equals("")) {
                        publisherName = "Publisher Unknown";
                    }

                    /*
                     ==================
                     Recipe Website URL
                     ==================
                     */
                    String recipeURL = foundRecipe.optString("url", "");
                    if (recipeURL.equals("")) {
                        // Skip the recipe with no website URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ==================
                     Recipe Diet Labels
                     ==================
                     */
                    JSONArray dietLabelsAsArray = foundRecipe.getJSONArray("dietLabels");

                    String dietLabels = "";
                    int dietLabelsArrayLength = dietLabelsAsArray.length();

                    if (dietLabelsArrayLength > 0) {
                        for (int j = 0; j < dietLabelsArrayLength; j++) {
                            String aDietLabel = dietLabelsAsArray.optString(j, "");
                            if (j < dietLabelsArrayLength - 1) {
                                aDietLabel = aDietLabel + ", ";
                            }
                            dietLabels = dietLabels + aDietLabel;
                        }
                    }

                    /*
                     ====================
                     Recipe Health Labels
                     ====================
                     */
                    JSONArray healthLabelsAsArray = foundRecipe.getJSONArray("healthLabels");

                    String healthLabels = "";
                    int healthLabelsArrayLength = healthLabelsAsArray.length();

                    if (healthLabelsArrayLength > 0) {
                        for (int j = 0; j < healthLabelsArrayLength; j++) {
                            String aHealthLabel = healthLabelsAsArray.optString(j, "");
                            if (j < healthLabelsArrayLength - 1) {
                                aHealthLabel = aHealthLabel + ", ";
                            }
                            healthLabels = healthLabels + aHealthLabel;
                        }
                    }

                    /*
                     =======================
                     Recipe Ingredient Lines
                     =======================
                     */
                    JSONArray ingredientLinesAsArray = foundRecipe.getJSONArray("ingredientLines");

                    String ingredientLines = "";
                    int ingredientLinesArrayLength = ingredientLinesAsArray.length();

                    if (ingredientLinesArrayLength > 0) {
                        for (int j = 0; j < ingredientLinesArrayLength; j++) {
                            String anIngredientLine = ingredientLinesAsArray.optString(j, "");
                            if (j < ingredientLinesArrayLength - 1) {
                                anIngredientLine = anIngredientLine + ", ";
                            }
                            ingredientLines = ingredientLines + anIngredientLine;
                        }
                    }

                    /*
                     ===============
                     Recipe Category
                     ===============
                     */
                    JSONArray categoryAsArray = foundRecipe.getJSONArray("dishType");

                    String category = categoryAsArray.optString(0, "");
                    if (category.equals("")) {
                        // Skip the recipe with no website URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ==============
                     Recipe Cuisine
                     ==============
                     */
                    JSONArray cuisineAsArray = foundRecipe.getJSONArray("cuisineType");

                    String cuisine = cuisineAsArray.optString(0, "");
                    if (cuisine.equals("")) {
                        // Skip the recipe with no website URL
                        index++;
                        continue;   // Jump to the next iteration
                    }

                    /*
                     ======================
                     Recipe Nutrition Facts
                     ======================
                     */
                    JSONObject nutritionObject = foundRecipe.getJSONObject("totalNutrients");

                    /*
                    Servings
                     */
                    Integer servings = foundRecipe.optInt("yield", 0);
                    if (servings == 0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    }
                    String servingsString = servings.toString();

                    /*
                    Calories
                     */
                    Double calories = foundRecipe.optDouble("calories", 0.0);
                    if (calories == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        calories = calories * 100;
                        calories = (double) Math.round(calories);
                        calories = calories / 100;
                    }
                    String caloriesString = calories.toString();

                    /*
                    Total Fat
                     */
                    JSONObject fatObject = nutritionObject.getJSONObject("FAT");
                    Double totalFat = fatObject.optDouble("quantity", 0);
                    if (totalFat == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        totalFat = totalFat * 100;
                        totalFat = (double) Math.round(totalFat);
                        totalFat = totalFat / 100;
                    }
                    String totalFatString = totalFat.toString();

                    /*
                    Saturated Fat
                     */
                    JSONObject satFatObject = nutritionObject.getJSONObject("FASAT");
                    Double totalSatFat = satFatObject.optDouble("quantity", 0);
                    if (totalSatFat == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        totalSatFat = totalSatFat * 100;
                        totalSatFat = (double) Math.round(totalSatFat);
                        totalSatFat = totalSatFat / 100;
                    }
                    String totalSatFatString = totalSatFat.toString();

                    /*
                    Cholesterol
                     */
                    JSONObject cholesterolObject = nutritionObject.getJSONObject("CHOLE");
                    Double cholesterol = cholesterolObject.optDouble("quantity", 0);
                    if (totalFat == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        cholesterol = cholesterol * 100;
                        cholesterol = (double) Math.round(cholesterol);
                        cholesterol = cholesterol / 100;
                    }
                    String cholesterolString = cholesterol.toString();

                    /*
                    Sodium
                     */
                    JSONObject sodiumObject = nutritionObject.getJSONObject("NA");
                    Double sodium = sodiumObject.optDouble("quantity", 0);
                    if (sodium == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        sodium = sodium * 100;
                        sodium = (double) Math.round(sodium);
                        sodium = sodium / 100;
                    }
                    String sodiumString = sodium.toString();

                    /*
                    Total Carbs
                     */
                    JSONObject carbsObject = nutritionObject.getJSONObject("CHOCDF");
                    Double carbs = carbsObject.optDouble("quantity", 0);
                    if (carbs == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        carbs = carbs * 100;
                        carbs = (double) Math.round(carbs);
                        carbs = carbs / 100;
                    }
                    String carbsString = carbs.toString();

                    /*
                    Fiber
                     */
                    JSONObject fiberObject = nutritionObject.getJSONObject("FIBTG");
                    Double fiber = fiberObject.optDouble("quantity", 0);
                    if (fiber == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        fiber = fiber * 100;
                        fiber = (double) Math.round(fiber);
                        fiber = fiber / 100;
                    }
                    String fiberString = fiber.toString();

                    /*
                    Sugars
                     */
                    JSONObject sugarObject = nutritionObject.getJSONObject("SUGAR");
                    Double sugar = sugarObject.optDouble("quantity", 0);
                    if (sugar == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        sugar = sugar * 100;
                        sugar = (double) Math.round(sugar);
                        sugar = sugar / 100;
                    }
                    String sugarString = sugar.toString();

                    /*
                    Protein
                     */
                    JSONObject proteinObject = nutritionObject.getJSONObject("PROCNT");
                    Double protein = proteinObject.optDouble("quantity", 0);
                    if (protein == 0.0) {
                        // Skip the recipe with unknown calories
                        index++;
                        continue;   // Jump to the next iteration
                    } else {
                        /* Round the calories value to 2 decimal places */
                        protein = protein * 100;
                        protein = (double) Math.round(protein);
                        protein = protein / 100;
                    }
                    String proteinString = protein.toString();

                    String nutrients = "Servings: " + servingsString + ", Calories: " + caloriesString + ", Total Fat: " + totalFatString + " grams, Saturated Fat: "
                            + totalSatFatString + " grams, Cholesterol: " + cholesterolString + " milligrams, Sodium: " + sodiumString +
                            " milligrams, Total Carbohydrate: " + carbsString + " grams, Dietary Fiber: " + fiberString + " grams, Sugars: "
                            + sugarString + " grams, Protein: " + proteinString + " grams";

                    /*
                     =========================================================
                     Create a new Recipe object dressed up with its Attributes
                     =========================================================
                     */
                    SearchedRecipe recipe = new SearchedRecipe(name, category, cuisine, recipeURL, imageURL,
                            publisherName, recipeURL, ingredientLines, nutrients, dietLabels, healthLabels);

                    // Add the newly created recipe object to the list of searchedRecipes
                    searchedRecipes.add(recipe);
                    index++;
                }
            } else {
                // No recipe found for the search query
                Methods.showMessage("Information", "No Results!", "No recipe found for the search query!");
            }

        } catch (Exception ex) {
            Methods.showMessage("Information", "No Results!", "No recipe found for the search query!");
        }

        searchQuery = "";
        return "/recipeSearch/SearchResults?faces-redirect=true";
    }

    public void clearSearchFields() {
        searchQuery = "";
        dietLabel = null;
        healthLabel = null;
        maxNumberOfRecipes = null;
    }

}


