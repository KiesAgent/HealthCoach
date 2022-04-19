/*
 * Created by Sean Fleming on 2021.10.19
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserRecipeFacade extends AbstractFacade<UserRecipe> {
    /*
    ---------------------------------------------------------------------------------------------
    The EntityManager is an API that enables database CRUD (Create Read Update Delete) operations
    and complex database searches. An EntityManager instance is created to manage entities
    that are defined by a persistence unit. The @PersistenceContext annotation below associates
    the entityManager instance with the persistence unitName identified below.
    ---------------------------------------------------------------------------------------------
     */
    @PersistenceContext(unitName = "HealthCoachPU")
    private EntityManager entityManager;

    // Obtain the object reference of the EntityManager instance in charge of
    // managing the entities in the persistence context identified above.
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /*
    This constructor method invokes its parent AbstractFacade's constructor method,
    which in turn initializes its entity class type T and entityClass instance variable.
     */
    public UserRecipeFacade() {
        super(UserRecipe.class);
    }

    /*
    *********************
    *   Other Methods   *
    *********************
     */

    // Returns a list of object references of UserFile objects that belong to
    // the User object whose database Primary Key = primaryKey
    public List<UserRecipe> findUserRecipesByUserPrimaryKey(Integer primaryKey) {
        /*
        The following @NamedQuery definition is given in UserFile entity class file:
        @NamedQuery(name = "UserFile.findUserRecipesByUserId", query = "SELECT u FROM UserRecipe u WHERE u.userId.id = :userId")

        The following statement obtains the results from the named database query.
         */
        return entityManager.createNamedQuery("UserRecipe.findUserRecipesByUserId")
                .setParameter("userId", primaryKey)
                .getResultList();
    }

    // Returns the object reference of the Recipe object whose name is recipe_name
    public UserRecipe findByUserRecipeName(String recipe_name) {
        /*
        The following @NamedQuery definition is given in Recipe.java entity class file:
        @NamedQuery(name = "Recipe.findByName", query = "SELECT c FROM Recipe c WHERE c.name = :name")
         */

        if (entityManager.createNamedQuery("UserRecipe.findByName")
                .setParameter("name", recipe_name)
                .getResultList().isEmpty()) {
            
            // Return null if no Recipe object exists by the name of recipe_name
            return null;
            
        } else {
            
            // Return the Object reference of the Recipe object whose name is recipe_name
            return (UserRecipe) (entityManager.createNamedQuery("UserRecipe.findByName")
                    .setParameter("name", recipe_name)
                    .getSingleResult());
        }
    }
}
