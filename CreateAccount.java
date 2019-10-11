package application;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateAccount {

	@SuppressWarnings("unchecked")
public static void display(String title, UserData data) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);
		window.setMinHeight(300);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label name = new Label("Username:");
		GridPane.setConstraints(name, 0, 0);
		TextField nameInput = new TextField();
		GridPane.setConstraints(nameInput, 1, 0);
		
		Label password = new Label("Password:");
		GridPane.setConstraints(password, 0, 1);
		TextField passInput = new TextField();
		passInput.setPromptText("Password");
		GridPane.setConstraints(passInput, 1, 1);
		
		Label cfmpwd = new Label("Confirm Password:");
		GridPane.setConstraints(cfmpwd, 0, 2);
		TextField cfmpwdInput = new TextField();
		cfmpwdInput.setPromptText("Password");
		GridPane.setConstraints(cfmpwdInput, 1, 2);
		
		Label corrmsg = new Label();
		corrmsg.setFont(Font.font(18));
	    GridPane.setConstraints(corrmsg, 0, 5);
	    
		Button sgup = new Button("Sign Up");
		GridPane.setConstraints(sgup, 0, 3);
		sgup.setOnAction(e ->
		{
			//System.out.println(passInput.toString());
			//System.out.println(cfmpwdInput.toString());
			if(checkMatch(passInput, cfmpwdInput)) {
				try {
				data.add(nameInput.getText(), passInput.getText());
				//System.out.println("Match");
				
				Object obj = new JSONParser().parse(new FileReader("user.json"));
			    JSONObject jsonObj = (JSONObject) obj;
			    JSONArray userArray = (JSONArray) jsonObj.get("userInfo");
			          JSONObject oneUser = new JSONObject();
			          // put questions in one json object
			          oneUser.put("UserName", nameInput.getText());
			          oneUser.put("Password", passInput.getText());
			          JSONArray house = new JSONArray();
			          JSONArray favo = new JSONArray();
			          oneUser.put("Houses", house);
			          oneUser.put("Favorites", favo);
			          // get choice array and put in json object
			          userArray.add(oneUser);
			        
			        JSONObject allUsers = new JSONObject();
			        allUsers.put("userInfo", userArray);

			          FileWriter writeFile = new FileWriter("user.json");
			          // write content to json file
			          writeFile.write(allUsers.toJSONString());
			          writeFile.flush();
			          writeFile.close();
			        
			    SignIn.display(data);
				}catch(Exception e1) {
					System.out.println("Cannot Add User Info");
				}
			}
			else {
				//System.out.println("No Match");
				corrmsg.setText("Try Again");
			}
		});
		
				
		
		grid.getChildren().addAll(name, nameInput, password, passInput, cfmpwd, cfmpwdInput, sgup, corrmsg);
		
		Scene scene = new Scene(grid, 300, 250);
		window.setScene(scene);
		window.showAndWait();
	}

	private static boolean checkMatch(TextField passInput, TextField cfmpwdInput) {
		// TODO Auto-generated method stub
        if(passInput.getText().equals(cfmpwdInput.getText()))
        	return true;
        else
        	return false;
	}

}
