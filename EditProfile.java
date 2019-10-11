package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditProfile {

	public static void display(EditHouse eh, UserData data, User user) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(300);
		window.setMinHeight(300);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Button ep = new Button("Edit Password");
		ep.setOnAction(e -> EditPwd.display(user,data));
		ep.setMinWidth(280);
		GridPane.setConstraints(ep, 0, 1);
		
		Button anh = new Button("Edit Your Houses");
		anh.setMinWidth(280);
		anh.setOnAction(e -> {
			try {
				EditHouse.display(data,user);
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
		});
		GridPane.setConstraints(anh, 0, 2);
		
		Button fr = new Button("Your Favorite List");
		fr.setMinWidth(280);
		fr.setOnAction(e -> {
			try {
				FavoList.display(user);
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
		});
		GridPane.setConstraints(fr, 0, 3);
		
		Button ques = new Button("Ask Question");
		ques.setMinWidth(280);
		ques.setOnAction(e -> AskQues.display());
		GridPane.setConstraints(ques, 0, 4);
		
		grid.getChildren().addAll(ep, anh, fr, ques);
		Scene scene = new Scene(grid, 300, 350);
		
		window.setScene(scene);
		window.showAndWait();
	}

}
