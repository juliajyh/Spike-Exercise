package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SignIn {

	public static void display(UserData data) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(500);
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
		
		Label corrmsg = new Label();
		corrmsg.setFont(Font.font(18));
	    GridPane.setConstraints(corrmsg, 0, 4);
		
	    EditHouse eh = new EditHouse();
		
		Button login = new Button("Login");
		login.setOnAction(e -> {
			try {
				data.parseJson();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(data.get(nameInput.getText())!=null)
				if(data.get(nameInput.getText()).pwd.equals(passInput.getText())) {
					User user = data.get(nameInput.getText());
			        LoginPage.display(user, data, eh);
				}
				else
					corrmsg.setText("Wrong password");
			else
				corrmsg.setText("No User");
		});
		GridPane.setConstraints(login, 0, 3);
		
		Button cont = new Button("Continue");
		cont.setOnAction(e -> NologinPage.display("Guest"));
		GridPane.setConstraints(cont, 2, 3);
		
		grid.getChildren().addAll(name, nameInput, password, passInput, login, cont, corrmsg);
		
		Scene scene = new Scene(grid, 300, 250);
		window.setScene(scene);
		window.showAndWait();
	}

}
