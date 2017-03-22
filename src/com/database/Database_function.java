package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Database_function{
	
	// First Declare Connection Parameters
	static Connection conn;
	static PreparedStatement pst;
	
	// Declare Data Storing Parameters
	static ResultSetMetaData records_Meta;
	static ResultSet rs_database_records;
	static String[] column_names=new String[150];
	
	/* This class is used to fetch records. Name of the table is 
	passed as a parameter and a JSON Output of all table elements is returned in
	the form of String.
	*/
	public static String Select_Record(String table_name){
		
		
		JSONObject database_elements = new JSONObject(); // Stores database elements
		JSONObject database_metadata= new JSONObject();  // Stores rowcount and respective error messages
		
		
		JSONArray database_elements_JSONarray=new JSONArray(); // Stores database elements	
		JSONArray final_JSONarray = new JSONArray(); // Stores data from both JSON Objects
		
		try{
			
			// Setup Connection to database
			conn = ConnectionProvider.getCon();
			Class.forName("org.postgresql.Driver");
			pst=conn.prepareStatement("select * from xpd."+table_name+";");
			rs_database_records = pst.executeQuery();
				
			// Fetch Column Names of respective database 
			
			records_Meta = rs_database_records.getMetaData();
			int columnCnt = records_Meta.getColumnCount();
		    for(int i=1;i<=columnCnt;i++) {
		       column_names[i-1]=records_Meta.getColumnName(i).toUpperCase();
		      }
		    
		    int count=0; // Used to calculate row count 
			
		    // Traverse Resultset 
		    while(rs_database_records.next()){
				
		    	  /* Stores database elements in JSON Object with key as column names and 
		    	     and values as database elements.
		    	  */
		    	
				  for(int i=1;i<=columnCnt;i++) {
					  database_elements.put(column_names[i-1],rs_database_records.getObject(i));
		          }
		          database_elements_JSONarray.add(database_elements);
				  count++;				
			}
			
			// Stores Metadata
		    database_metadata.put("Error", "False");
			database_metadata.put("Error Message", null);
			database_metadata.put("Rowcount", count);
			
			// Combines both Metadata and Database Elements
			final_JSONarray.add(database_metadata);
			final_JSONarray.add(database_elements_JSONarray);
			
			
			// Close Database Connection
			rs_database_records.close();
			pst.close();
			conn.close();
			
		}
		catch(Exception e){
		
			// Used to catch any SQL errors
			
			String error_message=e.toString();
			database_metadata.put("Error", "True");
			final_JSONarray.add(database_metadata);
			database_metadata.put("Error Message",error_message);
			final_JSONarray.add(database_metadata);
			return final_JSONarray.toString();
		}
		
		
		return final_JSONarray.toString(); // Returns the final JSON_Output to XPD_Main.java
	}

	public static String Select_Record1(String table_name, MultivaluedMap<String, String> parameters) {
		// TODO Auto-generated method stub
		
		SQL_QueryGeneration sql=new SQL_QueryGeneration();
		
		JSONObject database_elements = new JSONObject(); // Stores database elements
		JSONObject database_metadata= new JSONObject();  // Stores rowcount and respective error messages
		
		ArrayList<String> database_elements_JSONarray = new ArrayList<String>();
		ArrayList<String> final_JSONarray = new ArrayList<String>();
		
		//JSONArray database_elements_JSONarray=new JSONArray(); // Stores database elements	
		//JSONArray final_JSONarray = new JSONArray(); // Stores data from both JSON Objects
		String resultset="";
		String main_sql="";
		String newLine = System.getProperty("line.separator");
		try{
			
			// Setup Connection to database
			conn = ConnectionProvider.getCon();
			Class.forName("org.postgresql.Driver");
		  
			
			Iterator<String> it = parameters.keySet().iterator();

			String key=(String)it.next();      
		    if(key.equals("filter")){
				   
			  main_sql=sql.SQLGenerate(table_name, parameters.getFirst(key));
		      main_sql=main_sql.concat(";");
				    	
		    }
				   
		    pst=conn.prepareStatement(main_sql);
			rs_database_records = pst.executeQuery();
			
				
			// Fetch Column Names of respective database 
			
			records_Meta = rs_database_records.getMetaData();
			int columnCnt = records_Meta.getColumnCount();
		    for(int i=1;i<=columnCnt;i++) {
		       column_names[i-1]=records_Meta.getColumnName(i).toUpperCase();
		      }
		    
		    int count=0; // Used to calculate row count 
		   
		    // Traverse Resultset 
		    while(rs_database_records.next()){
				
		   	 

		   	 for(int i=1;i<=columnCnt;i++) {
		   		    
					  database_elements.put(column_names[i-1],rs_database_records.getObject(i));
					 
		          }
		        //  database_elements_JSONarray.add(count, database_elements);
		   	database_elements_JSONarray.add(database_elements.toString());
		       		         
				  count++;				
			}
		  
			
			// Stores Metadata
		    database_metadata.put("Error", "False");
			database_metadata.put("Error Message", null);
			database_metadata.put("Rowcount", count);
			
			// Combines both Metadata and Database Elements
			final_JSONarray.add(database_metadata.toString());
			final_JSONarray.add(database_elements_JSONarray.toString());
			
			
			// Close Database Connection
			rs_database_records.close();
			pst.close();
			conn.close();
			
			
		}
		catch(Exception e){
		
			// Used to catch any SQL errors
			
			String error_message=e.toString();
			database_metadata.put("Error", "True");
			final_JSONarray.add(database_metadata.toString());
			database_metadata.put("Error Message",error_message);
			final_JSONarray.add(database_metadata.toString());
			
			return final_JSONarray.toString();
		}
		//return resultset;
		//return main_sql;
		return final_JSONarray.toString(); // Returns the final JSON_Output to XPD_Main.java
		
	}
	
	public static int InsertRecords(String table_name, String parameter){
		
		try{
			
			// Setup Connection to database
			conn = ConnectionProvider.getCon();
			Class.forName("org.postgresql.Driver");
			pst=conn.prepareStatement("Insert into  xpd."+table_name+" (organizationname) VALUES ('"+parameter+"');");
		
			pst.execute();
			
			
			pst.close();
			conn.close();
			
		}
		catch(Exception e){
			return 500;
		}
		return 200;
	}
}
