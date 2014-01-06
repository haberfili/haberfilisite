package controllers;

import java.util.List;

import models.News;
import models.User;
import mongo.DBConnector;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import forms.Login;

import static play.data.Form.*;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() throws Exception{
		List<News> newsList = null;
		try {
			DBConnector connector= new DBConnector();
			Datastore datasource = connector.getDatasource();
			newsList = datasource.find(News.class).asList();
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
		System.out.println("sessionemail is:"+session("email"));
		User user = User.findByEmail(session("email"));
		return ok(index.render(newsList,user));
	}
}
