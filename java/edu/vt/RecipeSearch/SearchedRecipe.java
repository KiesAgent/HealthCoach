/*
 * Created by Sean Fleming on 2021.10.19
 */
package edu.vt.RecipeSearch;

public class SearchedRecipe {
    /*
    ===========================================================
    Instance variables representing the attributes of a recipe.
    ===========================================================
     */
    private Integer id;
    private String name;
    private String category;
    private String cuisine;
    private String websiteUrl;
    private String photoUrl;
    private String publisherName;
    private String publisherUrl;
    private String ingredients;
    private String nutrients;
    private String dietLabels;
    private String healthLabels;

    /*
    ====================================================
    Class constructor for instantiating a SearchedRecipe
    object to represent a particular recipe.
    ====================================================
     */
    public SearchedRecipe(Integer id, String name, String category, String cuisine, String websiteUrl,
                          String photoUrl, String publisherName, String publisherUrl, String ingredients, String nutrients, String dietLabels, String healthLabels) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.cuisine = cuisine;
        this.websiteUrl = websiteUrl;
        this.photoUrl = photoUrl;
        this.publisherName = publisherName;
        this.publisherUrl = publisherUrl;
        this.ingredients = ingredients;
        this.nutrients = nutrients;
        this.dietLabels = dietLabels;
        this.healthLabels = healthLabels;
    }

    public SearchedRecipe(String name, String category, String cuisine, String websiteUrl,
                          String photoUrl, String publisherName, String publisherUrl, String ingredients, String nutrients, String dietLabels, String healthLabels) {
        this.name = name;
        this.category = category;
        this.cuisine = cuisine;
        this.websiteUrl = websiteUrl;
        this.photoUrl = photoUrl;
        this.publisherName = publisherName;
        this.publisherUrl = publisherUrl;
        this.ingredients = ingredients;
        this.nutrients = nutrients;
        this.dietLabels = dietLabels;
        this.healthLabels = healthLabels;
    }

    /*
    =========================================================
    Getter and Setter methods for the attributes of a recipe.
    =========================================================
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getNutrients() {
        return nutrients;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public String getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(String dietLabels) {
        this.dietLabels = dietLabels;
    }

    public String getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(String healthLabels) {
        this.healthLabels = healthLabels;
    }
}
