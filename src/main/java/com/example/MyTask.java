package com.example;

import java.util.*;
import java.util.logging.*;
import java.sql.*;

public class MyTask {
	private static double[] ranNumbers;
	private static int NUM_VALUES = 100000;
	public static void main(String[] args) throws Exception {
		//testGenRandomNumbers();
		//#1
		genRandomNumbers(NUM_VALUES);
		//#2, #3, #4
		createTable();
		//#5
	}
	public static void genRandomNumbers(int n) {
		//generate n number of random numbers 
		ranNumbers  = new double[n];
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			ranNumbers[i] = ran.nextGaussian();
		}
	}
	public static void testGenRandomNumbers() {
		genRandomNumbers(10);
		for (int i = 0; i < 10; i++) {
			System.out.println(ranNumbers[i]);
		}
	}
	public static void createTable() throws Exception{
		String CreateSQLQuery = "CREATE TABLE NUMBERS(id int auto_increment primary key, "
                + "                                value DOUBLE)";
		String url = "jdbc:h2:mem:";
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement prepStatement;
        Statement statement = connection.createStatement();
        try {
        	//Set auto commit to false  
        	connection.setAutoCommit(false);
        	statement.execute(CreateSQLQuery);
            prepStatement = connection.prepareStatement("INSERT INTO NUMBERS (value) VALUES (?)");
            for (int i = 0; i < NUM_VALUES; i++) {
            	prepStatement.setDouble(1, ranNumbers[i]);
            	prepStatement.executeUpdate();
            }    
            connection.commit();
            prepStatement.close();
        } 
        catch (SQLException ex) {
        	ex.printStackTrace();
        }
        //#3 Statement to execute: SELECT stddev (VALUE) / SQRT(100000)  as STDDEV FROM NUMBERS
        String Query = "INSERT INTO NUMBERS (value) VALUES " + 
        			   "(SELECT stddev (VALUE) / SQRT(100000) as NUM FROM NUMBERS)";
        String insert = "INSERT INTO NUMBERS (sample_stddev)" + 
        				"VALUES (NUM)";
        statement.executeUpdate(Query);
        //#4
        ResultSet rs = statement.executeQuery("SELECT * from NUMBERS");
        for(int i = 0; i <= NUM_VALUES; i++) {rs.next();}
        System.out.print("#4: " + rs.getString(2));
        //org.h2.tools.Server.startWebServer(connection);
	}
}