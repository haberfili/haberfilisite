package controllers;

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

public class Login extends Controller {

	public static Result login() {
		return ok(login.render("LoginYour new application is ready."));
	}

	public static Result save() {
		try {
			Form<Dynamic> bindFromRequest = Form.form().bindFromRequest();
			String title = bindFromRequest.get().getData().get("title").toString();
			String detail = bindFromRequest.get().getData().get("detail").toString();
			String link = bindFromRequest.get().getData().get("link").toString();
			DBConnector connector = new DBConnector();
			Datastore datasource = connector.getDatasource();
			News news = new News();
			news.title=title;
			news.detail=detail;
			news.link=link;
			datasource.save(news);
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
		return ok(login.render("LoginYour new application is ready."));
	}
}
