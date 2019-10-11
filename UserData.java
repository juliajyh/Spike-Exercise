package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserData {
	 List<User> users;
	
	UserData(){
		users = new ArrayList<User>();
	}

	public void add(String name, String pwd) {
		// TODO Auto-generated method stub
		users.add(new User(name, pwd));
	}

	public  User get(String name) {
		// TODO Auto-generated method stub
		for(User usr : users) {
			if(usr.usname.equals(name))
				return usr;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void updatePwd(String name, String newPwd) throws IOException, ParseException {
		// TODO Auto-generated method stub
        //get(name).pwd = newPwd;
        List list = new ArrayList();
        Object obj = new JSONParser().parse(new FileReader("user.json"));
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONArray olduserArray = (JSONArray) jsonObj.get("userInfo");
        JSONArray newuserArray = new JSONArray();
        for (int i = 0; i < olduserArray.size(); i++) {
          JSONObject usr = (JSONObject) olduserArray.get(i);
          // put questions in one json object
          if(usr.get("UserName").equals(name)) {
        	  JSONObject newusr = new JSONObject();
        	  JSONArray house = new JSONArray();
	          JSONArray favo = new JSONArray();
	          newusr.put("Houses", house);
	          newusr.put("Favorites", favo);
        	  newusr.put("UserName", name);
	          newusr.put("Password", newPwd);
	          newuserArray.add(newusr);
          }
          else
        	  newuserArray.add(usr);
          // get choice array and put in json object
        }
        JSONObject allUsers = new JSONObject();
        allUsers.put("userInfo", newuserArray);

          FileWriter writeFile = new FileWriter("user.json");
          // write content to json file
          writeFile.write(allUsers.toJSONString());
          writeFile.flush();
          writeFile.close();
	}
	
	@SuppressWarnings("unchecked")
	public void parseJson()
			  throws FileNotFoundException, IOException, ParseException{
		    Object obj = new JSONParser().parse(new FileReader("user.json"));
		    JSONObject jsonObj = (JSONObject) obj;
		    JSONArray userss = (JSONArray) jsonObj.get("userInfo");

		    // get question array content
		    for (Object user: userss) {
		      JSONObject roomObj = (JSONObject) user;
		      users.add(new User((String)roomObj.get("UserName"), (String) roomObj.get("Password")));
		    }
		    }

}
