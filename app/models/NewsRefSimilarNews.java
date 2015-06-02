package models;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

@Entity("news")
public class NewsRefSimilarNews  {
	 @Id public ObjectId id;
	 public String title,detail,link,image,source,detailMore,category;
	 @Reference(lazy = true)  
	 public List<NewsRefSimilarNews> similarNews = new ArrayList<NewsRefSimilarNews>();
	 public long createDate;
 

}