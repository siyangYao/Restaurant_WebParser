package brightedge.onsite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;


public class MenuDAO extends AbstractDAO implements DAO{
	
	public MenuDAO(){
		super();
	}

	@Override
	public void insert(String table, int id, String menu_name, String menu_price) {
		try {
			ps=(PreparedStatement) con.prepareStatement("insert into "+table+" values (default,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, menu_name);
			ps.setString(3, menu_price);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insert error from menus table");
			e.printStackTrace();
		}
	}
	
	@Override
	public int selectId(String table, String parameter) {
		int id=0;
		try {
			ps=(PreparedStatement) con.prepareStatement("select restaurant_id from "+table+" where restaurant_id=?");
			ps.setInt(1, Integer.parseInt(parameter));
			rs=ps.executeQuery();
			while(rs.next()){
				id=Integer.parseInt(rs.getString("restaurant_id"));
			}
		} catch (SQLException e) {
			System.out.println("select id erro from menu table");
			e.printStackTrace();
		}
		
		return id;
	}
	
	@Override
	public ResultSet selectById(int id, String table) {
		try {
			ps=(PreparedStatement) con.prepareStatement("select * from "+table+" where restaurant_id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("menus table selectbyid error");
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * not used methods
	 * */
	@Override
	public void insert(String table,String name) {
		
	}

	@Override
	public ResultSet selectByTable(String table) {
		// TODO Auto-generated method stub
		return null;
	}
}
