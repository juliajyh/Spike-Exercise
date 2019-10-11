package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class EditHouse {
	
	 ObservableList<Room> houses ;
		
		private static TableView<Room> table;
		
		EditHouse(){
			houses = FXCollections.observableArrayList();
		}
		
		@SuppressWarnings("unchecked")
		public static void display(UserData data, User user) throws FileNotFoundException, IOException, ParseException {
			Stage window = new Stage();
			window.setTitle("All Houses You Have Added");
			table = new TableView<>(); 
			
			TableColumn<Room, String> rentCol = new TableColumn<>("Rent");
			rentCol.setMinWidth(200);
			rentCol.setCellValueFactory(new PropertyValueFactory<>("rent"));
			
			TableColumn<Room, String> availCol = new TableColumn<>("Availability");
			availCol.setMinWidth(100);
			availCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
			
			TableColumn<Room, String> prefCol = new TableColumn<>("Preference");
			prefCol.setMinWidth(100);
			prefCol.setCellValueFactory(new PropertyValueFactory<>("pref"));
			
			table.setEditable(true);
			rentCol.setOnEditCommit(e -> {
				try {
					rentEdit(e, user);
				} catch (IOException | ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			});
			availCol.setOnEditCommit(e -> {
				try {
					availEdit(e, user);
				} catch (IOException | ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});
			prefCol.setOnEditCommit(e -> {
				try {
					prefEdit(e, user);
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			rentCol.setCellFactory(TextFieldTableCell.forTableColumn());
			availCol.setCellFactory(TextFieldTableCell.forTableColumn());
			prefCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
			
			//System.out.println(houses.get(0).rent);
			//System.out.println(houses.get(1).rent);
			 

			ObservableList<Room> list = (ObservableList<Room>) getRoom(user);
			table.setItems(list);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			table.getColumns().addAll(rentCol, availCol, prefCol);
			
			VBox box = new VBox();
			box.getChildren().addAll(table);
			
			Scene scene = new Scene(box);
			window.setScene(scene);
			window.showAndWait();
		}
	
		private static ObservableList<Room> getRoom(User user) throws FileNotFoundException, IOException, ParseException {
			// TODO Auto-generated method stub
			ObservableList<Room> allRoom = FXCollections.observableArrayList();;
			Object obj = new JSONParser().parse(new FileReader("user.json"));
		    JSONObject jsonObj = (JSONObject) obj;
		    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
		    for(Object usr: userArray) {
		    	JSONObject oneUser = (JSONObject) usr;
		    	//System.out.println(oneUser);
		    	if(oneUser.get("UserName").equals(user.usname)) {
		    	JSONArray roomArray = (JSONArray) oneUser.get("Houses");
		    	//System.out.println(roomArray);
		    	for(Object room: roomArray) {
		    		JSONObject oneRoom = (JSONObject) room;
		    		allRoom.add(new Room((String)oneRoom.get("Rent"),
		    				(String)oneRoom.get("Availability"), (String)oneRoom.get("Preference")));
		    	}
		    	}
		    }
			return allRoom;
		}

		@SuppressWarnings("unchecked")
		public static void rentEdit(Event e, User user) throws IOException, ParseException {
			TableColumn.CellEditEvent<Room, String> editEvent;
			editEvent = (TableColumn.CellEditEvent<Room, String>) e;
			Room s = editEvent.getRowValue();
			s.setRent(editEvent.getNewValue(),user);
			//update(s, user);
		}
		
		@SuppressWarnings("unchecked")
		public static void availEdit(Event e, User user) throws IOException, ParseException {
			TableColumn.CellEditEvent<Room, String> editEvent;
			editEvent = (TableColumn.CellEditEvent<Room, String>) e;
			Room s = editEvent.getRowValue();
			s.setAvail(editEvent.getNewValue(),user);
			//update(s, user);
		}
		
		@SuppressWarnings("unchecked")
		public static void prefEdit(Event e, User user) throws IOException, ParseException {
			TableColumn.CellEditEvent<Room, String> editEvent;
			editEvent = (TableColumn.CellEditEvent<Room, String>) e;
			Room s = editEvent.getRowValue();
			s.setPref(editEvent.getNewValue(),user);
			//update(s, user);
		}
/*
		@SuppressWarnings("unchecked")
		private static void update(Room s, User user) {
			// TODO Auto-generated method stub
			List list = new ArrayList();
	        Object obj = new JSONParser().parse(new FileReader("user.json"));
		    JSONObject jsonObj = (JSONObject) obj;
		    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
	        for (int i = 0; i < userArray.size(); i++) {
	          JSONObject usr = (JSONObject) userArray.get(i);
	          // put questions in one json object
	          if(usr.get("UserName").equals(user.usname)) {
	        	  JSONArray houseArray = (JSONArray) usr.get("Houses");
	        	  for(Object house: houseArray) {
			    		JSONObject oneHouse = (JSONObject) house;
			    		allRoom.add(new Room((String)oneRoom.get("Rent"),
			    				(String)oneRoom.get("Availability"), (String)oneRoom.get("Preference")));
			    	}
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
		} */
		
		
		
		
}
