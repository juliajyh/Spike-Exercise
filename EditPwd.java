package application;

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

public class EditPwd {

	public static void display(User user, UserData data) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(300);
		window.setMinHeight(300);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label oldpwd = new Label("UserName");
		GridPane.setConstraints(oldpwd, 0, 0);
		TextField oldInput = new TextField();
		GridPane.setConstraints(oldInput, 1, 0);
		
		Label newpwd = new Label("New Password:");
		GridPane.setConstraints(newpwd, 0, 1);
		TextField newInput = new TextField();
		GridPane.setConstraints(newInput, 1, 1);
		
		Label cfmpwd = new Label("Confirm Password:");
		GridPane.setConstraints(cfmpwd, 0, 2);
		TextField cfmpwdInput = new TextField();
		cfmpwdInput.setPromptText("Password");
		GridPane.setConstraints(cfmpwdInput, 1, 2);
		
		Label corrmsg = new Label();
		corrmsg.setFont(Font.font(18));
		GridPane.setConstraints(corrmsg, 0, 4);
		
		Button upd = new Button("Update");
		upd.setOnAction(e -> {
			if(checkMatch(newInput, cfmpwdInput)) {try {
			data.updatePwd(oldInput.getText(),newInput.getText());
			} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			SignIn.display(data);
		}
	else {
		corrmsg.setText("Try Again");
	}  }
		);
		GridPane.setConstraints(upd, 0, 3);	
		
		grid.getChildren().addAll(oldpwd, oldInput, newpwd, newInput, cfmpwd, cfmpwdInput, upd, corrmsg);
		
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
