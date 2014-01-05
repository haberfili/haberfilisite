package controllers;

import models.News;
import mongo.DBConnector;
import play.data.Form;
import play.data.DynamicForm.Dynamic;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
import play.mvc.*;

import views.html.*;

import com.google.code.morphia.Datastore;

public class NewsController extends Controller {
	public static Result news() {
		return ok(news.render("LoginYour new application is ready."));
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
		return ok(news.render("LoginYour new application is ready."));
	}
}
