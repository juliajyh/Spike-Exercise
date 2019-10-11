package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddHouse {

	public static void display(User user, UserData data, EditHouse eh) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(800);
		window.setMinHeight(600);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label dsp = new Label("Description:");
		GridPane.setConstraints(dsp, 0, 0);
		TextField dspInput = new TextField();
		GridPane.setConstraints(dspInput, 1, 0);
		
		Label prc = new Label("Price:");
		GridPane.setConstraints(prc, 0, 1);
		TextField prcInput = new TextField();
		prcInput.setPromptText("A Valid Number");
		GridPane.setConstraints(prcInput, 1, 1);
		
		Label avb = new Label("Availability:");
		GridPane.setConstraints(avb, 0, 2);
		TextField avbInput = new TextField();
		avbInput.setPromptText("ex. 3 Month");
		GridPane.setConstraints(avbInput, 1, 2);
		
		Label prf = new Label("Preferene:");
		GridPane.setConstraints(prf, 0, 3);
		TextField prfInput = new TextField();
		GridPane.setConstraints(prfInput, 1, 3);
		
		Button add = new Button("Add House");
		add.setOnAction(e -> {
			try {
				addHouse(data,eh, user ,prcInput, avbInput, prfInput);
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		GridPane.setConstraints(add, 0, 4);
		
		grid.getChildren().addAll(prc, prcInput, avb, avbInput, prf, prfInput, add);
		
		Scene scene = new Scene(grid, 300, 250);
		window.setScene(scene);
		window.showAndWait();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static void addHouse(UserData data, EditHouse eh, User user, TextField prcInput, TextField avbInput, TextField prfInput) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		// TODO: Add the House to the list.
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
  	          newusr.put("Favorite", user.favos);
  	          JSONArray house = (JSONArray) usr.get("Houses");
  	          //System.out.println(house);
  	          JSONObject oneHouse = new JSONObject();
  	          oneHouse.put("Rent", prcInput.getText());
  	          oneHouse.put("Availability", avbInput.getText());
  	          oneHouse.put("Preference", prfInput.getText());
  	          house.add(oneHouse);
  	          newusr.put("Houses", house);
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
        Object roomObj = new JSONParser().parse(new FileReader("room.json"));
  	    JSONObject jsonroomObj = (JSONObject) roomObj;
  	    JSONArray roomArray = (JSONArray) jsonroomObj.get("roomInfo");
  	    JSONObject oneRoom = new JSONObject();
  	    oneRoom.put("rent", prcInput.getText());
  	    oneRoom.put("availability", avbInput.getText());
  	    oneRoom.put("preference", prfInput.getText());
  	    roomArray.add(oneRoom);
  	  JSONObject allRoom = new JSONObject();
      allRoom.put("roomInfo", roomArray);
        FileWriter writeFile1 = new FileWriter("room.json");
        // write content to json file
        writeFile1.write(allRoom.toJSONString());
        writeFile1.flush();
        writeFile1.close();
        //eh.newHouse(prcInput.getText(), avbInput.getText(), prfInput.getText());
		LoginPage.display(user, data, eh);
	}

}
