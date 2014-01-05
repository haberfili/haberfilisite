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

public class LoginController extends Controller {
	public static Form<Login> loginForm= form(Login.class);
	
	public static Result login() {
		return ok(login.render(loginForm,null));
	}
	
	public static Result authenticate() throws Exception{
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors() || !validate(loginForm.get().email,loginForm.get().password) ) {
            return badRequest(login.render(loginForm,null));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.index()
            );
        }
    }
	 public static boolean  validate(String email, String password) throws Exception {
         return !(User.authenticate(email, password) == null);
     }
}
