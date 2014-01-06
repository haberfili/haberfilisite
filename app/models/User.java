package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import mongo.DBConnector;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.query.Query;

/**
 * User entity managed by Ebean
 */
@Entity("user") 
public class User {

    private static final long serialVersionUID = 1L;

    @Id ObjectId id;
    
    @Constraints.Required
    public String email;
    
    public String name;
    
    @Constraints.Required
    public String password;
    
    /**
     * Authenticate a User.
     * @throws Exception 
     */
    public static User authenticate(String email, String password) throws Exception {
    	DBConnector connector= new DBConnector();
		Datastore ds= connector.getDatasource();
        return ds.find(User.class).filter("email", email).filter("password", password).get();
    }
    
    public static User findByEmail(String email) throws Exception {
    	DBConnector connector= new DBConnector();
		Datastore ds= connector.getDatasource();
        return ds.find(User.class).filter("email", email).get();
    }
    
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }

}

