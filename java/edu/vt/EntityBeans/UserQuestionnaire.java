/*
  - Created by Kevin Thai-Khanh Vu on 2021.11.30
  - Copyright © 2021 Kevin Thai-Khanh Vu. All rights reserved.
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// The @Entity annotation designates this class as a JPA Entity class representing the UserQuestionnaire table in the BevqDB database.
@Entity

// Name of the database table represented
@Table(name = "UserQuestionnaire")

@NamedQueries({
    @NamedQuery(name = "UserQuestionnaire.findAll", query = "SELECT u FROM UserQuestionnaire u")
    , @NamedQuery(name = "UserQuestionnaire.findById", query = "SELECT u FROM UserQuestionnaire u WHERE u.id = :id")
    , @NamedQuery(name = "UserQuestionnaire.findByDateEntered", query = "SELECT u FROM UserQuestionnaire u WHERE u.dateEntered = :dateEntered")
    , @NamedQuery(name = "UserQuestionnaire.findQuestionnairesByUserPrimaryKey", query = "SELECT u FROM UserQuestionnaire u WHERE u.userId.id = :primaryKey")
})
/* 
 The findQuestionnairesByUserPrimaryKey query is added. The others are auto generated. 
 userId     = object reference of the User object (See below: private User userId;)
 userId.id  = User object's database Primary Key to be set
 primaryKey = User object's database Primary Key given
 */

public class UserQuestionnaire implements Serializable {
    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the UserQuestionnaire table in the BevqDB database.

    CREATE TABLE UserQuestionnaire
    (
        id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
        date_entered DATE NOT NULL,
        questionnaire MEDIUMTEXT NOT NULL,
        user_id INT UNSIGNED,
        FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
    );
    ========================================================
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "date_entered")
    @Temporal(TemporalType.DATE)
    private Date dateEntered;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "questionnaire")
    private String questionnaire;

    // user_id INT UNSIGNED
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    /*
    =========================================================================
    Class constructors for instantiating a UserQuestionnaire entity object to
    represent a row in the UserQuestionnaire table in the BevqDB database.
    =========================================================================
     */
    public UserQuestionnaire() {
    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the UserQuestionnaire table in the BevqDB database.
    ======================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
     Checks if the UserQuestionnaire object identified by 'object' is the
     same as the UserQuestionnaire object identified by 'id'
     Parameter object = UserQuestionnaire object identified by 'object'
     Returns True if the UserQuestionnaire 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserQuestionnaire)) {
            return false;
        }
        UserQuestionnaire other = (UserQuestionnaire) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }

}
