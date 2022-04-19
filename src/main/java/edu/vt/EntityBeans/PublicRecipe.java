/*
 * Created by Sean Fleming on 2021.10.19
 */
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
@Table(name = "PublicRecipe")

@NamedQueries({
    @NamedQuery(name = "PublicRecipe.findByName", query = "SELECT m FROM PublicRecipe m WHERE m.name = :name")
})

public class PublicRecipe implements Serializable {
    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Recipe table in the RecipesDB database.

    CREATE TABLE Recipe
    ========================================================
     */
    private static final long serialVersionUID = 1L;

    // id INT UNSIGNED NOT NULL AUTO_INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    // Recipe Name
    // name VARCHAR(32) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;

    // Recipe Category
    // category VARCHAR(32) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "category")
    private String category;

    // Recipe Cuisine
    // cuisine VARCHAR(32) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "cuisine")
    private String cuisine;

    // Recipe Website URL
    // website_url VARCHAR(255) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "website_url")
    private String websiteUrl;

    // Recipe Photo URL
    // photo_url VARCHAR(255) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "photo_url")
    private String photoUrl;

    // Recipe Publisher Name
    // publisher_name VARCHAR(255) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "publisher_name")
    private String publisherName;

    // Recipe Publisher URL
    // publisher_url VARCHAR(255) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "publisher_url")
    private String publisherUrl;

    // Recipe Ingredients
    // ingredients VARCHAR(768) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 768)
    @Column(name = "ingredients")
    private String ingredients;

    // Recipe Nutrients
    // nutrients VARCHAR(512) NOT NULL
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "nutrients")
    private String nutrients;

    /*
    =============================================================
    Class constructors for instantiating a Recipe entity object to
    represent a row in the User table in the RecipesDB database.
    =============================================================
     */
    // Used in PrepareCreate method in RecipeController
    public PublicRecipe() {
    }

    // Not used but kept for potential future use
    public PublicRecipe(Integer id) {
        this.id = id;
    }

    // Not used but kept for potential future use
    public PublicRecipe(Integer id, String name, String category, String cuisine, String websiteUrl, String photoUrl, String publisherName, String publisherUrl, String ingredients, String nutrients) {
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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getCuisine() { return cuisine; }

    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public String getWebsiteUrl() { return websiteUrl; }

    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getPublisherName() { return publisherName; }

    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getPublisherUrl() { return publisherUrl; }

    public void setPublisherUrl(String publisherUrl) { this.publisherUrl = publisherUrl; }

    public String getIngredients() { return ingredients; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getNutrients() { return nutrients; }

    public void setNutrients(String nutrients) { this.nutrients = nutrients; }

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
        if (!(object instanceof PublicRecipe)) {
            return false;
        }
        PublicRecipe other = (PublicRecipe) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }

}
