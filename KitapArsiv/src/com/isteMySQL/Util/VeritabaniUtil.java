package com.isteMySQL.Util;
import java.sql.*;

public class VeritabaniUtil {
 static Connection conn=null;
 
 public static Connection Baglan() {
	 try {
		 conn=DriverManager.getConnection("jdbc:mysql://localhost/kitaparsiv","root","");
		 return conn;
	} catch (Exception e) {
		System.out.println(e.getMessage().toString());
		return null;
	}
 }
	
	
}
