package brightedge.onsite.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParserHelper {
	
	private MenuSearch ms;
	
	public ParserHelper(){
		ms=MenuSearch.getInstance();
	}
	
	public URL createURL(String url_string){
		URL url=null;
		
		try {
			url=new URL(url_string);
		} catch (MalformedURLException e) {
			System.out.println("incorrect url");
			e.printStackTrace();
		}
		
		return url;
	}
	
	public Document createDocumentByURL(String urlstr){
		URL url=createURL(urlstr);
		
		Document doc=null;
		try {
			doc=Jsoup.connect(url.toString()).get();
		} catch (IOException e) {
			System.out.println("incorrrect document");
			e.printStackTrace();
		}
		
		return doc;
	}
	
	
	
	/*
	 * parserOne helper
	 * */
	public void parserOneHelper_getName(Restaurant r, String url){
		Document doc=createDocumentByURL(url);
		Elements elements=doc.getElementsByClass("right-data");
		String name_address_phone=elements.select("p").first().text();
		r.setName(name_address_phone.substring(0, 14));
	}
		
	public void parserOneHelper_getAddressAndPhoneNumber(Restaurant r, String url){
		Document doc=createDocumentByURL(url);
		Elements elements=doc.getElementsByClass("right-data");
		int size=elements.select("p").size();
		
		for(int i=0;i<size;i++){
			String s=elements.select("p").get(i).text();
			int len=s.length();
			String address=s.substring(15,len-14);
			String phoneNumber=s.substring(len-14);
			r.addAddress(address);
			r.addPhoneNumber(address, phoneNumber);
		}
	}
	
	public void parserOneHelper_getMenuItem(Restaurant r, String url){
		Document doc=createDocumentByURL(url);
		Elements elements=doc.getElementsByClass("left-data");
		int size=elements.select("h3").size();
		
		for(int i=0;i<size;i++){
			String s=elements.select("h3").get(i).text();
			int index=0;
			for(;index<s.length();index++){
				if(s.charAt(index)=='.') break;
			}
				
			String menuItem=s.substring(0, index);
			String price=s.substring(s.length()-5);
			Menu menu=new Menu();
			menu.setMenuItem(menuItem);
			menu.setPrice(price);
			r.addMenu(menu);
			ms.addMenu(menuItem, r.getName());
		}
	}
	
	
	/*
	 * parserTwo helper
	 * */
	public void parserTwoHelper_getAddressAndPhoneNumber(Restaurant r, String url){
		Document doc=createDocumentByURL(url);
		Elements elements=doc.getElementsByClass("footer_wrapper");
		int size=elements.select("div[class]").size();
		
		for(int i=0;i<size;i++){
			String s=elements.select("div[class]").get(i).text();
			int index=0;
			while(s.charAt(index)!='|'){
				index++;
			}
			String address=s.substring(0,index);
			r.addAddress(address);
			
			for(;index<s.length()-6;index++){
				if(s.substring(index,index+5).equals("phone")){
					r.addPhoneNumber(address, s.substring(index+6,index+21));
				}
			}
		}
		
	}
	
	public void parserTwoHelper_getMenuItem(Restaurant r,String url){
		Document doc=createDocumentByURL(url);
		Elements elements=doc.getElementsByClass("menu_col");
		
		//get script url
		String regex0="src=\"(.*?)\"";
		Pattern pat = Pattern.compile(regex0, Pattern.DOTALL | Pattern.UNIX_LINES);
		Matcher m = pat.matcher(elements.select("script").toString());
		
		String url_script="";
		while(m.find()){
			String s=m.group(0);
			url_script=s.substring(5, s.length()-1);
		}
		
		//use regular expression to parse html
		String content=getURLContent(url_script);
		String regex1="locu-menu-item-name(.*?)</div>(.*?)locu-menu-item-price(.*?)</div>";
		pat=Pattern.compile(regex1, Pattern.DOTALL | Pattern.UNIX_LINES);
		m = pat.matcher(content);
		while(m.find()){
			String s=m.group();
			Menu menu=new Menu();
			int left=0;
			for(int i=left;i<s.length();i++){
				if(s.charAt(left)!='>') left++;
				if(s.charAt(i)=='<'&&i!=left){
					menu.setMenuItem(s.substring(left+1,i));
					break;
				}
			}
			for(;left<s.length()-5;left++){
				if(s.substring(left,left+5).equals("price")){
					break;
				}
			}
			for(int i=left;i<s.length();i++){
				if(s.charAt(left)!='>') left++;
				if(s.charAt(i)=='<'&&i!=left){
					menu.setPrice("$"+s.substring(left+1,i));
					break;
				}
			}
			r.addMenu(menu);
			ms.addMenu(menu.getMenuItem(), r.getName());
		}
	}
	
	private String getURLContent(String url_string){
		
		URL url=createURL(url_string);
		URLConnection con = null;	
		
		// try to get a connection
		try{
			con = url.openConnection();
		}catch (IOException ioe){
			System.err.println("Could not open connect to URL: " + url);
		}
		
		// try to get the page's encoding
		String encoding = con.getContentEncoding();		
		if (encoding == null){
			encoding = "US-ASCII";
		}
		
		// try to read the page into a reader
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), encoding));
		}catch (UnsupportedEncodingException uee){
			System.err.println("Bad encoding when trying to read from URL: " + url_string);
		}catch (IOException ioe){
			System.err.println("Could not read from URL: " + url_string);
		}
		
		// put the content from the reader into a string
		StringBuilder sb = new StringBuilder(); // empirically this a bit larger than what is on a results page to be safer
		try{
			String line;
			while ((line = br.readLine()) != null){
				sb.append(line);
			}
		}catch (IOException ioe){
			System.err.println("Could not read a line from URL: " + url_string);
		}finally{
			// cleanup
			try{
				br.close();
			}catch (IOException ioe){
				System.err.println("Couldn't clenaup buffered read.");
			}
		}
		
		return sb.toString();

	}
}
