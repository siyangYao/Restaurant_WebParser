package brightedge.onsite.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import brightedge.onsite.dao.DAOHelper;
import brightedge.onsite.practice.MenuSearch;
import brightedge.onsite.practice.Parser;
import brightedge.onsite.practice.Response;
import brightedge.onsite.practice.Restaurant;

@Path("/rest")
public class Services {
	
	private Gson gson=new GsonBuilder().create();
	private MenuSearch ms;
	private Response resp;
	
	public Services(){
		ms=MenuSearch.getInstance();
		resp=new Response();
	}
	
	@GET
	@Path("/getrestaurantinfo")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRestaurantInformation(){
		DAOHelper dh=new DAOHelper();
		
		List<Restaurant> list=dh.display();
		
		String res="";
		for(Restaurant r: list){
			res+=gson.toJson(r);
			res+="\n";
			dh.display(r);
		}
		
		return res;
	}
	
	@POST
	@Path("/getmenufromkeyword")
	public String getMenuFromKeyWord(@FormParam("keyword") String keyword){
		String result=ms.getMenu(keyword);
		
		if(result==null) {
			resp.setStatus("not found");
			return gson.toJson(resp);
		}
		
		return result;
	}
	
	@POST
	@Path("/parse")
	public String parse(@FormParam("website") String website){
		
		website=website.trim();
		System.out.println("website: "+website);
		
		if(website.equals("http://www.dinebombaygarden.com")){
			helper1(website);
			resp.setStatus("200");
			return gson.toJson(resp);
		}
		if(website.equals("http://www.allspicerestaurant.com")){
			helper2(website);
			resp.setStatus("200");
			return gson.toJson(resp);
		}
		
		resp.setStatus("can not parse");
		
		return gson.toJson(resp);
	}
	
	//private method
	//parse dine bombay garden
	private static void helper1(String url) {
		Parser parser=new Parser(url);
		parser.parserOne(url+"/menus.html");
	}
		
	//parse all spice restaurant
	private static void helper2(String url){
		Parser parser=new Parser(url);
		parser.parserTwo(url+"/menu.html");

	}
}
