package ehub_benefits;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.opencsv.CSVWriter;

public class database {


	public static Connection db_conn() throws SQLException, ClassNotFoundException {
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Class.forName("oracle.jdbc.OracleDriver");
		Connection DbConn = DriverManager.getConnection("jdbc:oracle:thin:@va10dx10v1-scan1:1525/EHPRDD2D","ad23382","M#Y1rxGA");
		
		System.out.println("Oracle Connection Success");
		
		return DbConn;
	}
	
	public static ResultSet sql_exec(Connection Conn, String Sql, String Tdata) throws SQLException, IOException {
		String OpFile = "src/test/resources/sqlOp.txt";
		ResultSet rs;
		if (Sql.contains("?")) { //IF preparedStatement-----------------------------------------
			Statement  stmt = Conn.createStatement();
			int c = 0;
			System.out.println("Test Data passed: " + Tdata);
			Tdata = Tdata.replaceAll("\\s","");
			Tdata = Tdata.replaceAll(",", "','");
			Tdata = "'" + Tdata + "'";
			System.out.println("Test Data after editing: " + Tdata);
			Sql = Sql.replaceAll("\\?",Tdata);
			rs   = stmt.executeQuery(Sql);
			System.out.println("Prep Stmt Executed: " + Sql);
              
			CSVWriter writer = new CSVWriter(new FileWriter(OpFile+1), '\t');
			Boolean includeHeaders = true;
			writer.writeAll(rs, includeHeaders);
			writer.close();
			c++;
		}
		else { //ELSE IF NOT preparedStatement--------------------------------------------------
			Statement stmt = Conn.createStatement();
			rs   = stmt.executeQuery(Sql);
		    System.out.println("Stmt Executed: " + Sql);
        
		    /*while (rs.next()) {
        		System.out.println(rs.getString(1));
        	}*/
        
		    CSVWriter writer = new CSVWriter(new FileWriter(OpFile+2), '\t');
		    Boolean includeHeaders = true;
        
		    writer.writeAll(rs, includeHeaders);
		    writer.close();
		}
    
		return rs;
	}
	
}
