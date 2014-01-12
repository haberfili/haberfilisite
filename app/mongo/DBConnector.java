package mongo;

import java.net.UnknownHostException;

import play.Play;
import play.Plugin;
import models.News;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class DBConnector {

	public Datastore getDatasource() {
		Mongo mongo=null;
		Datastore ds = null;
		Morphia morphia;
		String host=Play.application().configuration().getString("mongo.host");
		Integer port = Play.application().configuration().getInt("mongo.port");
		String database=Play.application().configuration().getString("mongo.database");
		String username=Play.application().configuration().getString("mongo.username");
		String password=Play.application().configuration().getString("mongo.password");
		try {
			mongo = new MongoClient(new ServerAddress(host, port));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		morphia = new Morphia();
//		morphia.map(News.class);
		if(username == null){
			ds = morphia.createDatastore(mongo, database);
		}
		else{
			ds = morphia.createDatastore(mongo, database,username,password.toCharArray());			
		}
		return ds;
	}

}

