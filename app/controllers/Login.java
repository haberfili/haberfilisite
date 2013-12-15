package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
public class Login extends Controller {

	public static Result login() {
		return ok(login.render("LoginYour new application is ready."));
	}
}
