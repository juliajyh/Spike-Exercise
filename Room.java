package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Room {
	public String rent;
	public String avail;
	public String pref;
	Room(){
		rent = "";
		avail = "";
		pref = "";
	}
	Room (String rent, String avail, String pref){
		this.rent = rent;
		this.avail = avail;
		this.pref = pref;
	}
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void setRent(String newValue, User user) throws IOException, ParseException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
        Object obj = new JSONParser().parse(new FileReader("user.json"));
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
        for (int i = 0; i < userArray.size(); i++) {
          JSONObject usr = (JSONObject) userArray.get(i);
          // put questions in one json object
          if(usr.get("UserName").equals(user.usname)) {
        	  userArray.remove(usr);
        	  JSONArray houseArray = (JSONArray) usr.get("Houses");
        	  JSONObject deleteHouse = new JSONObject();
        	  JSONObject addHouse = new JSONObject();
        	  for(Object house: houseArray) {
		    		JSONObject oneHouse = (JSONObject) house;
		    		if(oneHouse.get("Rent").equals(rent) && oneHouse.get("Preference").equals(pref)) {
		    			deleteHouse = oneHouse;
		    			JSONObject newhs = new JSONObject();
		          	  newhs.put("Rent", newValue);
		  	          newhs.put("Availability", avail);
		  	          newhs.put("Preference", pref);
		  	          addHouse = newhs;
		    		}
		    	}
        	  houseArray.remove(deleteHouse);
        	  houseArray.add(addHouse);
        	  usr.put("Houses", houseArray);
        	  userArray.add(usr);
          }
          // get choice array and put in json object
        }
        JSONObject allUsers = new JSONObject();
        allUsers.put("userInfo", userArray);

          FileWriter writeFile = new FileWriter("user.json");
          // write content to json file
          writeFile.write(allUsers.toJSONString());
          writeFile.flush();
          writeFile.close();
	}
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void setAvail(String newValue, User user) throws IOException, ParseException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
        Object obj = new JSONParser().parse(new FileReader("user.json"));
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
        for (int i = 0; i < userArray.size(); i++) {
          JSONObject usr = (JSONObject) userArray.get(i);
          // put questions in one json object
          if(usr.get("UserName").equals(user.usname)) {
        	  userArray.remove(usr);
        	  JSONArray houseArray = (JSONArray) usr.get("Houses");
        	  JSONObject deleteHouse = new JSONObject();
        	  JSONObject addHouse = new JSONObject();
        	  for(Object house: houseArray) {
		    		JSONObject oneHouse = (JSONObject) house;
		    		if(oneHouse.get("Rent").equals(rent) && oneHouse.get("Preference").equals(pref)) {
		    			deleteHouse = oneHouse;
		    			JSONObject newhs = new JSONObject();
		          	  newhs.put("Rent", rent);
		  	          newhs.put("Availability", newValue);
		  	          newhs.put("Preference", pref);
		  	          addHouse = newhs;
		    		}
		    	}
        	  houseArray.remove(deleteHouse);
        	  houseArray.add(addHouse);
        	  usr.put("Houses", houseArray);
        	  userArray.add(usr);
          }
          // get choice array and put in json object
        }
        JSONObject allUsers = new JSONObject();
        allUsers.put("userInfo", userArray);

          FileWriter writeFile = new FileWriter("user.json");
          // write content to json file
          writeFile.write(allUsers.toJSONString());
          writeFile.flush();
          writeFile.close();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setPref(String newValue, User user) throws IOException, ParseException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
        Object obj = new JSONParser().parse(new FileReader("user.json"));
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
        for (int i = 0; i < userArray.size(); i++) {
          JSONObject usr = (JSONObject) userArray.get(i);
          // put questions in one json object
          if(usr.get("UserName").equals(user.usname)) {
        	  userArray.remove(usr);
        	  JSONArray houseArray = (JSONArray) usr.get("Houses");
        	  JSONObject deleteHouse = new JSONObject();
        	  JSONObject addHouse = new JSONObject();
        	  for(Object house: houseArray) {
		    		JSONObject oneHouse = (JSONObject) house;
		    		if(oneHouse.get("Rent").equals(rent) && oneHouse.get("Availability").equals(avail)) {
		    			deleteHouse = oneHouse;
		    			JSONObject newhs = new JSONObject();
		          	  newhs.put("Rent", rent);
		  	          newhs.put("Availability", avail);
		  	          newhs.put("Preference", newValue);
		  	          addHouse = newhs;
		    		}
		    	}
        	  houseArray.remove(deleteHouse);
        	  houseArray.add(addHouse);
        	  usr.put("Houses", houseArray);
        	  userArray.add(usr);
          }
          // get choice array and put in json object
        }
        JSONObject allUsers = new JSONObject();
        allUsers.put("userInfo", userArray);

          FileWriter writeFile = new FileWriter("user.json");
          // write content to json file
          writeFile.write(allUsers.toJSONString());
          writeFile.flush();
          writeFile.close();
	}
	public String getRent() {
		return this.rent;
	}
	public String getAvail() {
		return this.avail;
	}
	public String getPref() {
		return this.pref;
	}
}
