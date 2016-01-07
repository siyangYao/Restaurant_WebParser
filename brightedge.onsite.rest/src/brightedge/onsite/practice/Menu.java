package brightedge.onsite.practice;

public class Menu {
	private String menuItem;
	private String price;
	
	public String getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem.trim();
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = "$"+price.trim();
	}
}
