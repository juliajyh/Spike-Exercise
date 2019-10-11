package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class ResultPage {

	public static void display(TextField minInput, TextField maxInput, TextField avbInput, TextField prfInput, User user) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		// TODO display all the houses according to preference.
		Stage window = new Stage();
		int min = Integer.parseInt(minInput.getText());
		int max = Integer.parseInt(maxInput.getText());
		int avb = Integer.parseInt(avbInput.getText());
		List<Room> candidates = selectCand(min, max, avb, prfInput.getText());
		//System.out.println(candidates);
		List<String> display = getResult(candidates);
		//System.out.println(display);
		Button addfavo = new Button("Add to Favorite List");
		
		ObservableList<String> items =
		        FXCollections.observableArrayList(display);
		    ListView<String> roomdes = new ListView<>(items);
		    roomdes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		    roomdes.setMaxWidth(600);
		    Label corrmsg = new Label();
			corrmsg.setFont(Font.font(18));
		    addfavo.setOnAction(e->{
		    List<String> sltRoom =
		            new ArrayList<String>(roomdes.getSelectionModel().getSelectedItems());
		    try {
				write(user, sltRoom, corrmsg);
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		    });
		    FavoList favo = new FavoList();
		    //favo.insert((roomdes.getSelectionModel().getSelectedItems()).toString());
		    /*if(user != null)
		         FavoList.display(user); */
		    // Add a button to continue adding to favorites.
	   
	   VBox root = new VBox();
		    root.getChildren().addAll(roomdes, addfavo, corrmsg);
		    Scene scene = new Scene(root, 700, 700);
		    window.setScene(scene);
		    window.show();		    
	}
	
	  @SuppressWarnings("unchecked")
	private static void write(User user, List<String> rooms, Label corrmsg) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		  if(user != null) {
		  Object obj = new JSONParser().parse(new FileReader("user.json"));
		    JSONObject jsonObj = (JSONObject) obj;
		    JSONArray olduserArray = (JSONArray) jsonObj.get("userInfo");
	        JSONArray newuserArray = new JSONArray();
	        for (int i = 0; i < olduserArray.size(); i++) {
	            JSONObject usr = (JSONObject) olduserArray.get(i);
	            // put questions in one json object
	            if(usr.get("UserName").equals(user.usname)) {
	          	  JSONObject newusr = new JSONObject();
	          	  newusr.put("UserName", user.usname);
	  	          newusr.put("Password", user.pwd);
	  	          newusr.put("Houses", user.rooms);
	  	          JSONArray house = (JSONArray) usr.get("Favorite");
	  	          for(String rm:rooms) {
	  	          house.add(rm);
	  	          }
	  	          newusr.put("Favorite", house);
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
	          writeFile.close(); }
		  else
		  {
			  corrmsg.setText("Please Login First");
		  }
	}

	private static List<String> getResult(List<Room> candidates) {
		// TODO Auto-generated method stub
		  List<String> str = new ArrayList<String>();
		  for(Room cand : candidates) {
			  str.add("Rent:" + cand.rent + "   " + 
					  "Availability:" + cand.avail + "   " + "Preference:" + cand.pref);
		  }
		return str;
	}

	private static List<Room> selectCand(int min, int max, int avb, String string) 
			  throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		List<Room> wholeList = parseJson("room.json"); 
		List<Room> selectedList = new ArrayList<Room>();
		//System.out.println(wholeList.get(0).rent);
		for(Room room:wholeList) {
			//System.out.println(room.rent);
			int rent = Integer.parseInt(room.rent);
			int avail = Integer.parseInt(room.avail);
			if(min <= rent && max >= rent && avb == avail && string.equals(room.pref)) {
				selectedList.add(room);
			}
		}
		return selectedList;
	}

	public static List<Room> parseJson(String filename)
			  throws FileNotFoundException, IOException, ParseException{
		    Object obj = new JSONParser().parse(new FileReader(filename));
		    JSONObject jsonObj = (JSONObject) obj;
		    JSONArray rooms = (JSONArray) jsonObj.get("roomInfo");
		    List<Room> Rooms = new ArrayList<Room>();
		    // get question array content
		    for (Object room : rooms) {
		      JSONObject roomObj = (JSONObject) room;
		      Rooms.add(new Room((String)(roomObj.get("rent")),
		    		     (String) roomObj.get("availability"),(String) roomObj.get("preference")));
		    }
		    return Rooms;
		  }


}
