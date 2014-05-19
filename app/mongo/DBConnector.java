package mongo;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.management.JMException;

import play.Play;
import play.Plugin;
import models.News;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.tools.ConnectionPoolStat;

public class DBConnector {
	
	private static Datastore ds;
	public  static Datastore getDatasource()  {
		if (ds == null) {
		
		Mongo mongo=null;
		Morphia morphia;
		String host=Play.application().configuration().getString("mongo.host");
		Integer port = Play.application().configuration().getInt("mongo.port");
		String database=Play.application().configuration().getString("mongo.database");
		String username=Play.application().configuration().getString("mongo.username");
		String password=Play.application().configuration().getString("mongo.password");
		try {
			MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
			builder.connectionsPerHost(10);
			MongoClientOptions options = builder.build();
			mongo = new MongoClient(new ServerAddress(host, port),options);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		morphia = new Morphia();
		if(username == null){
			ds = morphia.createDatastore(mongo, database);
		}
		else{
			ds = morphia.createDatastore(mongo, database,username,password.toCharArray());			 
		}
		}
		return ds;
	}

}

