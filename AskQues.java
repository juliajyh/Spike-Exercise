package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AskQues {

	public static void display() {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		Label askques = new Label("Ask A Question:");
		askques.setFont(Font.font(18));
		TextField questext = new TextField();
		Button submit = new Button("Submit");
		VBox box = new VBox();
		box.getChildren().addAll(askques, questext, submit);
		
		Scene scene = new Scene(box);
		window.setScene(scene);
		window.showAndWait();
	}

}
