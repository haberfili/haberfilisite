package controllers;

import models.News;
import models.User;
import mongo.DBConnector;
import play.data.Form;
import play.data.DynamicForm.Dynamic;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
import play.mvc.*;

import views.html.*;

import com.google.code.morphia.Datastore;

import forms.Login;
import static play.data.Form.form;

public class SignupController extends Controller {
	
	public static Result signup() {
		return ok(signup.render("Your new application is ready."));
	}

	public static Result save() {
		try {
			Form<Dynamic> bindFromRequest = Form.form().bindFromRequest();
			String email = bindFromRequest.get().getData().get("email").toString();
			String password=bindFromRequest.get().getData().get("password").toString();
			System.out.println("printing: "+email);
			System.out.println("printing: "+password);
			User user = new User();
			user.email=email;
			user.password=password;
			DBConnector dbConnector= new DBConnector();
			Datastore ds = dbConnector.getDatasource();
			ds.save(user);
			
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
		return ok(signup.render("Your new application is ready."));
	}
}
