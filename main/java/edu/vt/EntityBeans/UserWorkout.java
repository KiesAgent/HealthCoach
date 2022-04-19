package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
The @Entity annotation designates this class as a JPA Entity POJO class
representing the Recipe table in the RecipesDB database.
 */
@Entity

// Name of the database table represented
@Table(name = "UserWorkout")

@NamedQueries({
        @NamedQuery(name = "UserWorkout.findByName", query = "SELECT m FROM UserWorkout m WHERE m.name = :name")
        ,   @NamedQuery(name = "UserWorkout.findAll", query = "SELECT u FROM UserWorkout u")
        ,   @NamedQuery(name = "UserWorkout.findById", query = "SELECT u FROM UserWorkout u WHERE u.id = :id")
        ,   @NamedQuery(name = "UserWorkout.findUserWorkoutsByUserId", query = "SELECT u FROM UserWorkout u WHERE u.userId.id = :userId")
})

public class UserWorkout implements Serializable {
    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Recipe table in the RecipesDB database.
    ========================================================
     */

    /*
    CREATE TABLE UserWorkout
    (
        id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
        name VARCHAR(256) NOT NULL,
        category VARCHAR(100),
        muscle VARCHAR(256),
        equipment VARCHAR(256),
        image_url VARCHAR(256),
        repetition INT NOT NULL,
        sets INT NOT NULL,
        weight INT NOT NULL,
        weight_unit VARCHAR(256) NOT NULL,
        description VARCHAR(2000),
        user_id INT UNSIGNED,
        FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
    );
    */
    private static final long serialVersionUID = 1L;

    // id INT UNSIGNED NOT NULL AUTO_INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    // user_id INT UNSIGNED
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    // Workout Name
    // name VARCHAR(256) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name")
    private String name;

    // Workout Category
    // category VARCHAR(100)
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "category")
    private String category;

    // Workout Muscle(s)
    // muscle VARCHAR(256)
    @Basic()
    @Size(max = 256)
    @Column(name = "muscle")
    private String muscle;

    // Workout Equipment
    // equipment VARCHAR(256)
    @Basic()
    @Size(max = 256)
    @Column(name = "equipment")
    private String equipment;

    // Workout Image URL
    // image_url VARCHAR(256)
    @Basic()
    @Size(max = 256)
    @Column(name = "image_url")
    private String imageURL;

    // Workout Description
    // description VARCHAR(2000)
    @Basic()
    @Size(max = 2000)
    @Column(name = "description")
    private String description;

    /*
    =============================================================
    Class constructors for instantiating a Recipe entity object to
    represent a row in the User table in the RecipesDB database.
    =============================================================
     */
    // Used in PrepareCreate method in RecipeController
    public UserWorkout() {
    }

    // Not used but kept for potential future use
    public UserWorkout(User id) {
        userId = id;
    }

    public UserWorkout(Integer id, User userId, String name, String category, String muscle, String equipment, String imageURL, String description) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.muscle = muscle;
        this.equipment = equipment;
        this.imageURL = imageURL;
        this.description = description;
    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the Recipe table in the RecipesDB database.
    ======================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the Recipe object identified by 'object' is the same as the Recipe object identified by 'id'
     Parameter object = Recipe object identified by 'object'
     Returns True if the Recipe 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserWorkout)) {
            return false;
        }
        UserWorkout other = (UserWorkout) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }

}
