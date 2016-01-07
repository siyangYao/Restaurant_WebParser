package brightedge.onsite.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Restaurant {
	
	private List<String> addressList;
	private HashMap<String,String> phoneNumbers;
	private String name;
	private List<Menu> menuList;
	private int id=0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Restaurant(){
		menuList=new ArrayList<Menu>();
		addressList=new ArrayList<String>();
		phoneNumbers=new HashMap<String,String>();
	}
	
	public HashMap<String, String> getPhoneNumbers() {
		return phoneNumbers;
	}

	/*public void setPhoneNumbers(HashMap<String, String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}*/

	public void addPhoneNumber(String address, String phoneNumber){
		phoneNumbers.put(address.trim(),phoneNumber.trim());
	}
	
	public List<String> getAddressList() {
		return addressList;
	}

	/*public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}*/
	
	public void addAddress(String address){
		addressList.add(address.trim());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name.trim();
	}
	
	public List<Menu> getMenuList() {
		return menuList;
	}
	
	/*public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}*/
	
	public void addMenu(Menu menu){
		menuList.add(menu);
	}
}
