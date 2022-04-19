package edu.vt.WorkoutSearch;

public class SearchedWorkout {
    /*
    ============================================================
    Instance variables representing the attributes of a workout.
    ============================================================
     */
    private String name;
    private String category;
    private String muscles;
    private String equipments;
    private String image_url;
    private String description;

    /*
    =====================================================
    Class constructor for instantiating a SearchedWorkout
    object to represent a particular workout.
    =====================================================
     */
    public SearchedWorkout(String name, String category, String muscles, String equipments,
                           String image_url, String description) {
        this.name = name;
        this.category = category;
        this.muscles = muscles;
        this.equipments = equipments;
        this.image_url = image_url;
        this.description = description;
    }

    /*
    =========================================================
    Getter and Setter methods for the attributes of a recipe.
    =========================================================
     */

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

    public String getMuscles() {
        return muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
