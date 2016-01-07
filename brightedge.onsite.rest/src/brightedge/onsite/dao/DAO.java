package brightedge.onsite.dao;

import java.sql.ResultSet;

public interface DAO {
	public void insert(String table,String s1);
	public void insert(String table, int id,String s1,String s2);
	public int selectId(String table,String parameter);
	public ResultSet selectByTable(String table);
	public ResultSet selectById(int id, String table);
}
