package forms;

import com.google.code.morphia.Datastore;

import models.News;
import mongo.DBConnector;
import play.data.DynamicForm.Dynamic;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
import play.mvc.*;

import views.html.*;

public  class Login {

    public String email;
    public String password;

}

