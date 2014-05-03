package models;



import mongo.DBConnector;

import org.bson.types.ObjectId;

import com.feth.play.module.pa.user.AuthUser;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity("linkedAccount")
public class LinkedAccount {

	/**
	 * 
	 */

	public @Id ObjectId id;

	public User user;

	public String providerUserId;
	public String providerKey;

//	public static final Finder<Long, LinkedAccount> find = new Finder<Long, LinkedAccount>(
//			Long.class, LinkedAccount.class);

	public static LinkedAccount findByProviderKey(final User user, String key) {
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		return datasource.find(LinkedAccount.class).field("user.email").equal(user.email).field("providerKey").equal( key).get();
	}

	public static LinkedAccount create(final AuthUser authUser) {
		final LinkedAccount ret = new LinkedAccount();
		ret.update(authUser);
		return ret;
	}
	
	public void update(final AuthUser authUser) {
		this.providerKey = authUser.getProvider();
		this.providerUserId = authUser.getId();
	}

	public static LinkedAccount create(final LinkedAccount acc) {
		final LinkedAccount ret = new LinkedAccount();
		ret.providerKey = acc.providerKey;
		ret.providerUserId = acc.providerUserId;

		return ret;
	}
	public void save()  {
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		datasource.save(this);
	}
}