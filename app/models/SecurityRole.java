/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import mongo.DBConnector;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import be.objectify.deadbolt.core.models.Role;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity("securityRole")
public class SecurityRole implements Role {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public @Id ObjectId id;

	public String roleName;

	

	@Override
	public String getName()  {
		return roleName;
	}

	public static SecurityRole findByRoleName(String roleName) {
		System.out.println("!!!roleName is : "+roleName);
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		return datasource.find(SecurityRole.class).field("roleName").equal(roleName).get();
	}
	
	public void save(){
		DBConnector connector= new DBConnector();
		Datastore datasource = connector.getDatasource();
		datasource.save(this);
	}
}
