package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import mongo.DBConnector;

import org.bson.types.ObjectId;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.annotation.EnumValue;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.query.Query;

@Entity("tokenAction")
public class TokenAction {

	public enum Type {
		@EnumValue("EV")
		EMAIL_VERIFICATION,

		@EnumValue("PR")
		PASSWORD_RESET
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Verification time frame (until the user clicks on the link in the email)
	 * in seconds
	 * Defaults to one week
	 */
	private final static long VERIFICATION_TIME = 7 * 24 * 3600;

	public @Id ObjectId id;

	public String token;

	public User targetUser;

	public Type type;

	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date created;

	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date expires;

//	public static final Finder<Long, TokenAction> find = new Finder<Long, TokenAction>(
//			Long.class, TokenAction.class);

	public static TokenAction findByToken(final String token, final Type type) {
		
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		return datasource.find(TokenAction.class).field("token").equal( token)
				.field("type").equal(type).get();
	}

	public static void deleteByUser(final User u, final Type type) {
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		Query<TokenAction> equal = datasource.find(TokenAction.class).field("targetUser.id").equal(u.id)
		.field("type").equal(type);
		datasource.delete(equal);
	}

	public boolean isValid() {
		return this.expires.after(new Date());
	}

	public static TokenAction create(final Type type, final String token,
			final User targetUser) {
		final TokenAction ua = new TokenAction();
		ua.targetUser = targetUser;
		ua.token = token;
		ua.type = type;
		final Date created = new Date();
		ua.created = created;
		ua.expires = new Date(created.getTime() + VERIFICATION_TIME * 1000);
		ua.save();
		return ua;
	}
	private void save()  {
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		datasource.save(this);
	}
}
