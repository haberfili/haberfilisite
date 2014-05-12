package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.News;
import models.User;
import mongo.DBConnector;
import play.Routes;
import play.data.Form;
import play.i18n.Lang;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import com.google.code.morphia.Datastore;

public class Language extends Controller {

	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "error";
	public static final String USER_ROLE = "user";
	
	public static Result en() throws Exception{
		changeLang("en");
		List<News> newsList = null;
		try {
			Datastore datasource = DBConnector.getDatasource();
			newsList = datasource.find(News.class).asList();
		} catch (Exception e) {
			throw e;
		}
		return ok(index.render(newsList));
	}
	public static Result tr() throws Exception{
		changeLang("tr");
		List<News> newsList = null;
		try {
			Datastore datasource = DBConnector.getDatasource();
			newsList = datasource.find(News.class).asList();
		} catch (Exception e) {
			throw e;
		}
		return ok(index.render(newsList));
	}
}