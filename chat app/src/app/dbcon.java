package app;

import java.sql.*;

public class dbcon{
    public static void main (String [] args) {
        Connection c= null;
        Statement stmt= null;


        try {
            Class.forName("org.sqlite.JDBC");
            c= DriverManager.getConnection("jdbc:sqlite:chatapp.db");
            System.out.println("Opened");

            stmt = c.createStatement();
            String sql2 = "CREATE TABLE MESS "+
                    "(message     TEXT NOT NULL  )";
            String sql = "CREATE TABLE user (" +
            		"            		name	VARCHAR(50) NOT NULL," + 
            		"            		contact	CHAR(10) NOT NULL UNIQUE," + 
            		"            		mail	VARCHAR(30) NOT NULL," + 
            		"            		password	VARCHAR(30) ,"+
            		"                   PRIMARY KEY(contact))";


            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
           
            stmt.close();
            c.close();

            
            
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
