package com.Main;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.database.Database_function;

@Path("/")
public class Post {

	public static String db_name ="";
	
	@POST
	@Path("users/hardik")
	@Produces("text/plain")
	
	public String Test(@FormParam("database_name") String database_name, @FormParam("table_name") String table_name ){
		
		db_name=database_name;
		String organization_name="hardik";
		Integer output=Database_function.InsertRecords(table_name,organization_name);
		
		if(output==200){
			return "Inserted Successfully";
		}
		else
		{
			return "Error in insertion";
		}
		
		
		
	}
}
