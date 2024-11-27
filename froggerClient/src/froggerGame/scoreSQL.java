package froggerGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class scoreSQL {
	
	public void createDB() {
		
		System.out.println(" create db triggered");
		
		//declare a connection and sql statement to execute
		Connection conn = null;
				
		try {
					
			//load db driver
			Class.forName("org.sqlite.JDBC");
			System.out.print(" Driver Loaded");
					
			//create connection string and connect to database
			String dbURL = "jdbc:sqlite:scoreDB.db";
			conn = DriverManager.getConnection(dbURL);
					
			if (conn != null) {
						
				System.out.println(" Connected to score DB");
						
				//create table
		        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS SCORECARD " +
		        		"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
		        		" NAME TEXT NOT NULL, " +
		        		" SCORE INT NOT NULL) ";

		        try (PreparedStatement pstmtCreateTable = conn.prepareStatement(sqlCreateTable)) {
			        pstmtCreateTable.executeUpdate();
			        System.out.println(" Table Successfully Created");
		        }
		                
				//insert data
		        String sqlInsert = "INSERT INTO SCORECARD (NAME, SCORE) VALUES (?,?)";
		        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

			        //execute calls
			        pstmtInsert.setString(1, "Trevor");
			        pstmtInsert.setInt(2, 0);
			        pstmtInsert.executeUpdate();

			        System.out.println(" Insertion succesful");
		        }		
			}
		              
			//close connection 
			conn.close();	
					
		} catch (Exception e) {
					
			e.printStackTrace();
					
		}
	
	}
	
	
	public void addScore() {
		
		System.out.println(" add score triggered");
		
		//get current score before update
		int score = this.getScore();
		
		Connection conn = null;
		
		try {
					
			//load db driver
			Class.forName("org.sqlite.JDBC");
			System.out.print(" Driver Loaded");
					
			//create connection string and connect to database
			String dbURL = "jdbc:sqlite:scoreDB.db";
			conn = DriverManager.getConnection(dbURL);
					
			if (conn != null) {
						
				//update data
				String sqlSelect = "SELECT SCORE FROM SCORECARD";
                String sqlUpdate = "UPDATE SCORECARD SET SCORE = ? WHERE ID = 1";
                //update score record
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                	pstmtUpdate.setInt(1, (score + 50));
                	pstmtUpdate.executeUpdate();
                }
                //print score to console (debugging)
                try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                    ResultSet rs = pstmtSelect.executeQuery();
                    System.out.println(" Score: " + rs.getInt("SCORE") );
                    rs.close();
                }

						
			}
		              
			//close connection 
			conn.close();	
					
		} catch (Exception e) {
					
			e.printStackTrace();
					
		}
		
	}
	
	public void minusScore() {
		
		System.out.println(" minus score triggered");
		
		//get current score before update
		int score = this.getScore();
		
		Connection conn = null;
		
		try {
					
			//load db driver
			Class.forName("org.sqlite.JDBC");
			System.out.print(" Driver Loaded");
					
			//create connection string and connect to database
			String dbURL = "jdbc:sqlite:scoreDB.db";
			conn = DriverManager.getConnection(dbURL);
					
			if (conn != null) {
						
				String sqlSelect = "SELECT SCORE FROM SCORECARD WHERE ID = 1";
                String sqlUpdate = "UPDATE SCORECARD SET SCORE = ? WHERE ID = 1";
                //update score record
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                	pstmtUpdate.setInt(1, (score - 50) );
                	pstmtUpdate.executeUpdate();
                }
                
                //print score to console (debugging)
                try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                    ResultSet rs = pstmtSelect.executeQuery();    
                    System.out.println(" Score: " + rs.getInt("SCORE") );
                    rs.close();
                }
		
			}
		              
			//close connection 
			conn.close();	
					
		} catch (Exception e) {
					
			e.printStackTrace();
					
		}
		
	}
	
	public int getScore() {
		
		int score = 0;
		
		System.out.println(" get score triggered");
		
		Connection conn = null;
		
		try {
					
			//load db driver
			Class.forName("org.sqlite.JDBC");
			System.out.print(" Driver Loaded");
					
			//create connection string and connect to database
			String dbURL = "jdbc:sqlite:scoreDB.db";
			conn = DriverManager.getConnection(dbURL);
					
			if (conn != null) {
						
				String sqlSelect = "SELECT SCORE FROM SCORECARD WHERE ID = 1";
                
                try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                    ResultSet rs = pstmtSelect.executeQuery();                
                    score = rs.getInt("SCORE");	
                    rs.close();
                }

						
			}
			
			//close connection 
			conn.close();	
					
		} catch (Exception e) {
					
			e.printStackTrace();
					
		}
		
		return score;
		
	}
	
}
