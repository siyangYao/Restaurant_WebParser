package brightedge.onsite.practice;

public class MenuNode {
	
	private String menu;
	private String restaurantName;
	
	public MenuNode(String menu,String restaurantName){
		this.menu=menu;
		this.restaurantName=restaurantName;
	}
	
	public String getMenu() {
		return menu;
	}
	
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	
}
