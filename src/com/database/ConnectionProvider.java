package com.database;
import com.Main.*;
import java.sql.*;

public class ConnectionProvider extends Post implements Provider{
	static Connection con = null;
	static String url2="";
	
	public static Connection getCon(){
		try{
			
			url2=url+Post.db_name;
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url2,uname,pass);
		}catch(Exception ex){
			System.out.println(ex);
		}
		return con;
	}
}