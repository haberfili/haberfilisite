package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity("news")
public class News  {
	 @Id ObjectId id;
	 public String title,detail,link,image;
	 public long createDate;
 

}