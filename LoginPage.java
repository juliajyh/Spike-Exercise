package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginPage {

	public static void display(User user, UserData data, EditHouse eh) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(300);
		window.setMinHeight(300);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Button ep = new Button("Your Account");
		ep.setOnAction(e -> EditProfile.display(eh,data,user));
		ep.setMinWidth(280);
		GridPane.setConstraints(ep, 0, 1);
		
		Button anh = new Button("Add a New House");
		anh.setMinWidth(280);
		anh.setOnAction(e -> AddHouse.display(user,data, eh));
		GridPane.setConstraints(anh, 0, 2);
		
		Button fr = new Button("Find Room");
		fr.setMinWidth(280);
		fr.setOnAction(e -> FindRoom.display(user));
		GridPane.setConstraints(fr, 0, 3);
		
		
		grid.getChildren().addAll(ep, anh, fr);
		Scene scene = new Scene(grid, 300, 350);
		
		window.setScene(scene);
		window.showAndWait();
	}

}
