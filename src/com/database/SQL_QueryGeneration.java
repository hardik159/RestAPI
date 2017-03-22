package com.database;

import java.util.ArrayList;

public class SQL_QueryGeneration {


	public String SQLGenerate(String table_name, String parameter_value){
		 
		String newLine = System.getProperty("line.separator");
		String sql_query="";
		
		sql_query+="select * from xpd."+table_name;
		String temp[]=parameter_value.split(",");
		String where=" where ";
		String orderby="";
		ArrayList<String> ar = new ArrayList<String>();
		ArrayList<String> wherevalues = new ArrayList<String>();
	
		for(int j=0;j<temp.length;j++){
			
			String[] orderarray=temp[j].split(" ");
			
			// for orderby
			
			if(orderarray[0].equals("order") && orderarray[1].equals("by")){
				
				for(int i=2;i<orderarray.length;i++){
					
					ar.add(orderarray[i]);
					
					
				if((i+1)>(orderarray.length-1)){
					ar.add("break");
					}	
				}	
			}
			
			else{
				
				wherevalues.add(temp[j]);
				
				
			}
			
			
		}
		int a=0;
		while(a<wherevalues.size()){
			where+=wherevalues.get(a);
			if((a+1)<wherevalues.size()){
				where+=" and ";
			}
			a++;
		}
		sql_query+=(newLine);
		sql_query+=(where);
		
		if(!ar.isEmpty()){
			orderby+=" order by ";
			for(int i=0;i<ar.size()-1;i++){
				
				if(ar.get(i).equals("break")){
					orderby+=",";
					
					
				}
				else{
					orderby+=ar.get(i);
					orderby+=" ";
				}
			}
			sql_query+=(orderby);
		}
		
		
	 
	
	
	 
	 
	 
	 
	return sql_query;
	}


}
