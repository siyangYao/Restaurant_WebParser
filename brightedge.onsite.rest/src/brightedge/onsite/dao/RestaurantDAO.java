package brightedge.onsite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class RestaurantDAO extends AbstractDAO implements DAO{
	public RestaurantDAO(){
		super();
	}
	
	//insert restaurant table
	public void insert(String table,String name) {
		try {
			ps=(PreparedStatement) con.prepareStatement("insert into "+table+" values (default,?)");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insert error from restaurant table");
			e.printStackTrace();
		}
	}

	@Override
	public int selectId(String table, String name) {
		int id=0;
		try {
			ps=(PreparedStatement) con.prepareStatement("select id from "+table+" where name=?");
			ps.setString(1, name);
			rs=ps.executeQuery();
			while(rs.next()){
				id=Integer.parseInt(rs.getString("id"));
			}
		} catch (SQLException e) {
			System.out.println("select id erro from restaurant table");
			e.printStackTrace();
		}
		
		return id;
	}
	

	@Override
	public ResultSet selectByTable(String table) {
		try {
			rs=s.executeQuery("select * from "+table);
		} catch (SQLException e) {
			System.out.println("select restaurant table error");
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ResultSet selectById(int id,String table) {
		try {
			ps=(PreparedStatement) con.prepareStatement("select * from "+table+" where id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("restaurant table selectbyid error");
			e.printStackTrace();
		}
		return rs;
	}
	
	/*
	 * not used methods
	*/
	@Override
	public void insert(String table, int id, String s1, String s2) {
		
	}
}
