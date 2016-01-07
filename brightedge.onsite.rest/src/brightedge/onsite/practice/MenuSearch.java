package brightedge.onsite.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuSearch {
	
	private static MenuSearch instance=null;
	private static HashMap<String, List<MenuNode>> hm=null;
	
	private MenuSearch(){
		hm=new HashMap<String,List<MenuNode>>();
	}
	
	public static MenuSearch getInstance(){
		if(instance==null) instance=new MenuSearch();
		return instance;
	}
	
	public void addMenu(String menu, String restaurantName){
		String[] words=menu.split(" ");
		MenuNode menuNode=new MenuNode(menu,restaurantName);
		for(String word:words){
			word=word.toLowerCase();
			if(!hm.containsKey(word)){
				hm.put(word,new ArrayList<MenuNode>());
			}
			hm.get(word).add(menuNode);
		}
	}
	
	public String getMenu(String keyword){
		if(!hm.containsKey(keyword)) return null;
		StringBuilder sb=new StringBuilder();
		for(MenuNode node:hm.get(keyword)){
			sb.append(node.getMenu()+" from "+node.getRestaurantName());
			sb.append("\n");
		}
		return sb.toString();
	}
}
