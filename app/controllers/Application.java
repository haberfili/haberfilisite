package controllers;

import java.util.List;

import models.News;
import mongo.DBConnector;
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

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() {
		List<News> newsList = null;
		try {
			DBConnector connector= new DBConnector();
			Datastore datasource = connector.getDatasource();
			newsList = datasource.find(News.class).asList();
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
		return ok(index.render(newsList));
	}

}
