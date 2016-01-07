package brightedge.onsite.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class AbstractDAO {
	
	public Connection con;
	public Statement s;
	public PreparedStatement ps;
	public ResultSet rs;
	
	public AbstractDAO(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/brightedge_onsite_practice?user=root");
			s=(Statement) con.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println("can not load mysql driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("can not create connection with brightedge_onsite_practice database");
			e.printStackTrace();
		}
	}
}
