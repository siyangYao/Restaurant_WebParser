package brightedge.onsite.practice;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import brightedge.onsite.dao.DAOHelper;

public class Parser {
	private String url_home;
	private ParserHelper parserHelper;
	private DAOHelper daoHelper;
	
	public Parser(String url_home){
		this.url_home=url_home;
		parserHelper=new ParserHelper();
		daoHelper=new DAOHelper();
	}
	
	//parse dine bombay garden
	public void parserOne(String url_string){
		Document doc=parserHelper.createDocumentByURL(url_string);
		Elements element=doc.getElementsByClass("element");
		int size=element.select("a").size();
		Elements link=element.select("a");
		
		Restaurant r=new Restaurant();
		
		//get name
		parserHelper.parserOneHelper_getName(r,url_home+"/contacts.php");
		daoHelper.insertRestaurant(r);
		
		//get address and phone number
		parserHelper.parserOneHelper_getAddressAndPhoneNumber(r,url_home+"/contacts.php");
		daoHelper.insertContact(r);
		
		//get menu items
		for(int i=0;i<size;i++){
			String ahref=link.get(i).attr("href");
			parserHelper.parserOneHelper_getMenuItem(r,url_home+"/"+ahref);
			daoHelper.insertMenu(r);
		}
	}
	
	//parse all spice restaurant
	public void parserTwo(String url_string){
		Restaurant r=new Restaurant();
		
		//get name
		r.setName("All Spice");
		daoHelper.insertRestaurant(r);
		
		//get address and phone
		parserHelper.parserTwoHelper_getAddressAndPhoneNumber(r, url_home+"/contact.html");
		daoHelper.insertContact(r);
		
		//get menu items
		parserHelper.parserTwoHelper_getMenuItem(r, url_string);
		daoHelper.insertMenu(r);
	}
}
