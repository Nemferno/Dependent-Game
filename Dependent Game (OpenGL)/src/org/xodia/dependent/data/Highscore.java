package org.xodia.dependent.data;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Loads the highscores!
// If it cannot locate any 1st, 2nd, or 3rd, it is replaced by "N/A - 0"
public class Highscore {

	private static Highscore instance;
	
	public static final int FIRST_PLACE = 1, SECOND_PLACE = 2, THIRD_PLACE = 3;
	
	private ArrayList<String> names;
	private ArrayList<Integer> scores;
	
	private Highscore(){
		names = new ArrayList<String>(3);
		scores = new ArrayList<Integer>(3);
	}
	
	public void updateScores() throws Exception{
		InputStream input = new URL("http://s464830726.onlinehome.us/xodia/arcade/dependent/select.php")
			.openConnection().getInputStream();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("entry");

		// Clear Names & Scores for the new data to be put in
		names.clear();
		scores.clear();
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
				
				names.add(eElement.getElementsByTagName("name").item(0).getTextContent());
				scores.add(Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()));
			}
		}
	}
	
	// NOTE: NAME CANNOT HAVE SPACES!
	public void inputScore(String name, int score) throws Exception{
		URL url = new URL(getURL(name, score));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Dependent Game");
		
		int responseCode = con.getResponseCode();
		
		if(responseCode != 200)
			throw new Exception("Response Code is unauthorized!");
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(con.getInputStream());
		
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("entry");

		// Clear Names & Scores for the new data to be put in
		names.clear();
		scores.clear();
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
				
				names.add(eElement.getElementsByTagName("name").item(0).getTextContent());
				scores.add(Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()));
			}
		}
	}
	
	private String getURL(String name, int score){
		String URL = "http://s464830726.onlinehome.us/xodia/arcade/dependent/" + 
				"insert_highscore.php?name=" + name + "&score=" +
				score + "&datesubmitted=" + new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		return URL;
	}
	
	public int getScore(int PLACE){
		switch(PLACE){
		case FIRST_PLACE:
			
			if(scores.size() >= 1)
				return scores.get(0);
			
			break;
		case SECOND_PLACE:
			
			if(scores.size() >= 2)
				return scores.get(1);
			
			break;
		case THIRD_PLACE:
			
			if(scores.size() >= 3)
				return scores.get(2);
			
			break;
		}
		
		return 0;
	}
	
	public String getName(int PLACE){
		switch(PLACE){
		case FIRST_PLACE:
			
			if(names.size() >= 1)
				return names.get(0);
			
			break;
		case SECOND_PLACE:
			
			if(names.size() >= 2)
				return names.get(1);
			
			break;
		case THIRD_PLACE:
			
			if(names.size() >= 3)
				return names.get(2);
			
			break;
		}
		
		return null;
	}
	
	public static Highscore getInstance(){
		if(instance == null){
			instance = new Highscore();
		}
		
		return instance;
	}
	
}
