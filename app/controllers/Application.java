package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import models.News;
import models.NewsRefSimilarNews;
import models.User;
import mongo.DBConnector;
import mongo.DBConnectorLucene;

import org.bson.types.ObjectId;

import play.Routes;
import play.api.mvc.RequestHeader;
import play.cache.Cache;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.Session;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;
import views.html.index;
import views.html.login;
import views.html.onenews;
import views.html.similarNews;
import views.html.recentNews;
import views.html.profile;
import views.html.restricted;
import views.html.signup;
import views.html.whatis;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import com.google.code.morphia.Datastore;

public class Application extends Controller {

	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "error";
	public static final String USER_ROLE = "user";
	
	public static Result index() throws Exception{
//		changeLang("de");
		List<News> newsList = null;
		try {
			
			final User localUser = getLocalUser(session());
			Datastore datasource = DBConnector.getDatasource();
			if(localUser==null || localUser.sources==null || localUser.sources.size()==0|| localUser.sources.size()==3){
				newsList = getRecentNewsList();
			}else{
				newsList = datasource.find(News.class).filter("source in", localUser.sources).order("- createDate").filter("category", null).limit(50).asList();
			}
		} catch (Exception e) {
			throw e;
		}
		return ok(index.render(newsList,0,null));
	}
	private static List<News> getRecentNewsList() {
		Datastore datasource = DBConnector.getDatasource();
		List<News> newsList;
		if(Cache.get("newsList")==null){
			System.out.println("not found in cache");
			newsList = datasource.find(News.class).order("- createDate").filter("category", null).limit(50).asList();
			Cache.set("newsList", newsList,120);
		}
		newsList=(List<News>) Cache.get("newsList");
		return newsList;
	}
	public static Result indexPaginated(int page,String category) throws Exception{
//		changeLang("de");
		List<News> newsList = null;
		try {
			
			final User localUser = getLocalUser(session());
			Datastore datasource = DBConnector.getDatasource();
			if(localUser==null || localUser.sources==null || localUser.sources.size()==0|| localUser.sources.size()==3){
				newsList = datasource.find(News.class).order("- createDate").filter("category", category).offset((page+1)*50).limit(50).asList(); 
			}else{
				newsList = datasource.find(News.class).filter("source in", localUser.sources).order("- createDate").filter("category", category).offset((page+1)*50).limit(50).asList();
			}
		} catch (Exception e) {
			throw e;
		}
		return ok(index.render(newsList,page+1,category));
	}
	public static Result indexPaginatedAll(int page) throws Exception{
//		changeLang("de");
		List<News> newsList = null;
		try {
			
			final User localUser = getLocalUser(session());
			Datastore datasource = DBConnector.getDatasource();
			if(localUser==null || localUser.sources==null || localUser.sources.size()==0|| localUser.sources.size()==3){
				newsList = datasource.find(News.class).order("- createDate").filter("category", null).offset((page+1)*50).limit(50).asList(); 
			}else{
				newsList = datasource.find(News.class).filter("source in", localUser.sources).order("- createDate").filter("category", null).offset((page+1)*50).limit(50).asList();
			}
		} catch (Exception e) {
			throw e;
		}
		return ok(index.render(newsList,page+1,null));
	}
	
	public static Result ajaxCall(String req) throws Exception{
		Datastore datasource = DBConnectorLucene.getDatasource();
		News news =datasource.get(News.class, new ObjectId(req));
		System.out.println(news.similarNews.size());
		return ok(similarNews.render(news));
	}
	
	public static Result getRecentNews() throws Exception{
		List<News> newsList=new ArrayList<News>();
		List<News> allNewsList = getRecentNewsList();
		Random  random= new Random();
		while(newsList.size()<5){
			int randomNewsId=random.nextInt(allNewsList.size()-1);
			newsList.add(allNewsList.get(randomNewsId));
		}
		return ok(recentNews.render(newsList));
	}
	
	public static Result viewNews(String newsId) throws Exception{
		News news = null;
		
		try {
			Datastore datasource = DBConnector.getDatasource();
			try{
			news =datasource.get(News.class, new ObjectId(newsId));
			}catch(RuntimeException e){
				e.printStackTrace();
			}
			if(news==null){
				return redirect(routes.Application.index());
			}
			if(news.detail!=null && news.detail.indexOf("...")!=-1){
				news.detail=news.detail.replace("...", "");
			}
			
		} catch (Exception e) {
			throw e;
		}
		return ok(onenews.render(news));
	}

	public static User getLocalUser(final Session session) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
		final User localUser = User.findByAuthUserIdentity(currentAuthUser);
		return localUser;
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result restricted() {
		final User localUser = getLocalUser(session());
		return ok(restricted.render(localUser));
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result profile() {
		final User localUser = getLocalUser(session());
		String radikalcomtr="checked";
		String ntvmsnbccom="checked";
		String hurriyetcomtr="checked";
		if(localUser.sources!=null && localUser.sources.size()>0){
			if(!localUser.sources.contains("ntvmsnbc.com")){
				ntvmsnbccom="notChecked";
			}
			if(!localUser.sources.contains("hurriyet.com.tr")){
				hurriyetcomtr="notChecked";
			}
			if(!localUser.sources.contains("radikal.com.tr")){
				radikalcomtr="notChecked";
			}
		}
		return ok(profile.render(localUser,ntvmsnbccom,radikalcomtr,hurriyetcomtr)); 
	} 
	
	@Restrict(@Group(Application.USER_ROLE))
	public static Result saveProfile() {
		
		 final Map<String, String[]> values = request().body().asFormUrlEncoded();
		 User localUser = getLocalUser(session());
		 String ntvmsnbccom = null;
		 if(values.get("ntvmsnbc.com")!=null){
			 ntvmsnbccom =values.get("ntvmsnbc.com")[0];
		 }
		 String hurriyetcomtr=null;
		 if(values.get("hurriyet.com.tr")!=null){
			 hurriyetcomtr =values.get("hurriyet.com.tr")[0];
		 }
		 String radikalcomtr = null;
		 if(values.get("radikal.com.tr")!=null){
			 radikalcomtr =values.get("radikal.com.tr")[0];
		 }
		 if(ntvmsnbccom!=null || radikalcomtr!=null|| hurriyetcomtr!=null){
			 List<String> sources=new ArrayList<String>();
			 if(ntvmsnbccom!=null){
				 sources.add("ntvmsnbc.com");
			 }
			 if(hurriyetcomtr!=null){
				 sources.add("hurriyet.com.tr");
			 }
			 if(radikalcomtr!=null){
				 sources.add("radikal.com.tr");
			 }
			 if(sources.size()!=0){
				localUser.sources=sources;
				Datastore datasource = DBConnector.getDatasource();
				datasource.save(localUser);
			 }
		 }
		return redirect(routes.Application.profile());
	}

	public static Result login() {
		return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
	}

	public static Result doLogin() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(login.render(filledForm));
		} else {
			// Everything was filled
			return UsernamePasswordAuthProvider.handleLogin(ctx());
		}
	}

	public static Result signup() {
		return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.Signup.forgotPassword()))
				.as("text/javascript");
	}

	public static Result doSignup() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(signup.render(filledForm));
		} else {
			// Everything was filled
			// do something with your part of the form before handling the user
			// signup
			return UsernamePasswordAuthProvider.handleSignup(ctx());
		}
	} 

	public static String formatTimestamp(final long t) {
		return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
	}
	public static Result whatis() throws Exception{
		return ok(whatis.render()); 
	}
	public static Result getCategoryNews(String category) throws Exception{
//		changeLang("de");
		List<News> newsList = null;
		try {
			
			final User localUser = getLocalUser(session());
			Datastore datasource = DBConnector.getDatasource();
			if(localUser==null || localUser.sources==null || localUser.sources.size()==0|| localUser.sources.size()==3){
				newsList = datasource.find(News.class).order("- createDate").filter("category", category).limit(50).asList(); 
			}else{
				newsList = datasource.find(News.class).filter("source in", localUser.sources).order("- createDate").filter("category", category).limit(50).asList();
			}
		} catch (Exception e) {
			throw e;
		}
		return ok(index.render(newsList,0,category));
	}
}