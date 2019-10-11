package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class FavoList {

	static ObservableList<String> favorites = FXCollections.observableArrayList();

		// TODO Auto-generated method stub
        // TODO Display all your favorite houses.
				@SuppressWarnings("unchecked")
			public static void display( User user) throws FileNotFoundException, IOException, ParseException {
					
					//System.out.println(candidates);
					List<String> favoroom = (ObservableList<String>) getFavo(user);
					//System.out.println(display);
					
					ObservableList<String> rooms =
					        FXCollections.observableArrayList(favoroom);
					    ListView<String> des = new ListView<>(rooms);
					des.setMaxWidth(1200);
					Stage window = new Stage();
					window.setTitle("Your Favorite Houses");
					
					VBox box = new VBox();
					box.getChildren().addAll(des);
					
					Scene scene = new Scene(box);
					window.setScene(scene);
					window.showAndWait();
				}
				
				@SuppressWarnings("unchecked")
				private static void roomEdit(CellEditEvent<String, String> e) {
					// TODO Auto-generated method stub
					TableColumn.CellEditEvent<String, String> editEvent;
					editEvent = (TableColumn.CellEditEvent<String, String>) e;
					String s = editEvent.getRowValue();
					s = editEvent.getNewValue();
				}
				
				private static ObservableList<String> getFavo(User user) throws FileNotFoundException, IOException, ParseException{
					// TODO Auto-generated method stub
					ObservableList<String> allRoom = FXCollections.observableArrayList();;
					Object obj = new JSONParser().parse(new FileReader("user.json"));
				    JSONObject jsonObj = (JSONObject) obj;
				    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
				    for(Object usr: userArray) {
				    	JSONObject oneUser = (JSONObject) usr;
				    	//System.out.println(oneUser);
				    	if(oneUser.get("UserName").equals(user.usname)) {
				    	JSONArray roomArray = (JSONArray) oneUser.get("Favorite");
				    	//System.out.println(roomArray);
				    	for(Object room: roomArray) {
				    		allRoom.add(room.toString());
				    	}
				    	}
				    }
				    //System.out.println(allRoom.get(0));
					return allRoom;
				}
	}

