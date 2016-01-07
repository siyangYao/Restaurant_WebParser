package brightedge.onsite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import brightedge.onsite.practice.Menu;
import brightedge.onsite.practice.Restaurant;


public class DAOHelper extends AbstractDAO{
	
	DAO restaurantDAO;
	DAO menuDAO;
	DAO contactDAO;
	private String restaurantTable="restaurants";
	private String menuTable="menus";
	private String contactTable="contacts";
	
	public DAOHelper(){
		restaurantDAO=new RestaurantDAO();
		menuDAO=new MenuDAO();
		contactDAO=new ContactDAO();
	}
	public void insertRestaurant(Restaurant r){
		int id=restaurantDAO.selectId(restaurantTable, r.getName());
		
		if(id==0){
			restaurantDAO.insert(restaurantTable, r.getName());
			id=restaurantDAO.selectId(restaurantTable, r.getName());
			r.setId(id);
		}else{
			r.setId(id);
		}
	}
	public void insertContact(Restaurant r){
		int id=contactDAO.selectId(contactTable, Integer.toString(r.getId()));
		
		if(id==0){
			id=r.getId();
			List<String> addressList=r.getAddressList();
			HashMap<String, String> phoneList=r.getPhoneNumbers();
			for(String address:addressList){
				String phone=phoneList.get(address);
				contactDAO.insert(contactTable,id, address, phone);
			}
		}
	}
	public void insertMenu(Restaurant r){
		int id=menuDAO.selectId(menuTable, Integer.toString(r.getId()));
		if(id==0){
			id=r.getId();
			List<Menu> menuList=r.getMenuList();
			for(Menu menu:menuList){
				String name=menu.getMenuItem();
				String price=menu.getPrice();
				menuDAO.insert(menuTable, id, name, price);
			}
		}
	}
	public List<Restaurant> display(){
		List<Restaurant> list=new ArrayList<Restaurant>();
		rs=restaurantDAO.selectByTable(restaurantTable);
		try {
			while(rs.next()){
				Restaurant r=new Restaurant();
				list.add(r);
				int id=Integer.parseInt(rs.getString("id"));
				String name=rs.getString("name");
				r.setId(id);
				r.setName(name);
				
				ResultSet rs_menu=menuDAO.selectById(id, menuTable);
				while(rs_menu.next()){
					Menu menu=new Menu();
					menu.setMenuItem(rs_menu.getString("menu_name"));
					menu.setPrice(rs_menu.getString("menu_price"));
					r.addMenu(menu);
				}
				
				ResultSet rs_contact=contactDAO.selectById(id, contactTable);
				while(rs_contact.next()){
					String address=rs_contact.getString("address");
					String phone=rs_contact.getString("phone");
					r.addAddress(address);
					r.addPhoneNumber(address, phone);
				}
			}
		} catch (SQLException e) {
			System.out.println("sql display error");
			e.printStackTrace();
		}
		
		/*ParserHelper ph=new ParserHelper();
		for(Restaurant r:list){
			display(r);
		}*/
		return list;
	}
	
	public void display(Restaurant r){
		System.out.println("restaurant name: "+r.getName());
		
		List<String> addressList=r.getAddressList();
		for(String address:addressList){
			System.out.println("address: "+address+"  phone numbers: "+r.getPhoneNumbers().get(address));
		}
		
		List<Menu> menuList=r.getMenuList();
		for(Menu menu:menuList){
			System.out.println(menu.getMenuItem()+"  "+menu.getPrice());
		}
		System.out.println("*****************************************");
	}	
}
