/*
  - Created by Kevin Thai-Khanh Vu on 2021.11.30
  - Copyright © 2021 Kevin Thai-Khanh Vu. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserQuestionnaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserQuestionnaireFacade extends AbstractFacade<UserQuestionnaire> {
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
    public UserQuestionnaireFacade() {
        super(UserQuestionnaire.class);
    }

    /*
     *********************
     *   Other Methods   *
     *********************
     */


    /**
     * Find all questionnaires that belong to a User whose database primary key is dbPrimaryKey
     *
     * @param dbPrimaryKey is the Primary Key of the user entity in the database
     * @return a list of object references of userQuestionnaires that belong to the user whose database Primary Key = dbPrimaryKey
     */
    public List<UserQuestionnaire> findUserQuestionnairesByUserPrimaryKey(Integer dbPrimaryKey) {
        /*
        The following @NamedQuery is defined in UserQuestionnaire.java entity class file:
        @NamedQuery(name = "UserQuestionnaire.findQuestionnairesByUserPrimaryKey", 
            query = "SELECT u FROM UserQuestionnaire u WHERE u.userId.id = :primaryKey")
        
        The following statement obtaines the results from the named database query.
         */
        return entityManager.createNamedQuery("UserQuestionnaire.findQuestionnairesByUserPrimaryKey")
                .setParameter("primaryKey", dbPrimaryKey)
                .getResultList();
    }

}
