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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NologinPage {

	public static void display(String string) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Apply Filter");
		window.setMinWidth(800);
		window.setMinHeight(600);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label prg = new Label("Price Range:");
		GridPane.setConstraints(prg, 0, 0);
		Label min = new Label("Minimum Price:");
		GridPane.setConstraints(min, 1, 0);
		TextField minInput = new TextField();
		GridPane.setConstraints(minInput, 2, 0);
		Label max = new Label("Maximum Price:");
		GridPane.setConstraints(max, 3, 0);
		TextField maxInput = new TextField();
		GridPane.setConstraints(maxInput, 4, 0);
		
		
		Label avb = new Label("Availability:");
		GridPane.setConstraints(avb, 0, 1);
		TextField avbInput = new TextField();
		avbInput.setPromptText("number in unit of months");
		GridPane.setConstraints(avbInput, 1, 1);
		
		Label prf = new Label("Preference:");
		GridPane.setConstraints(prf, 0, 2);
		TextField prfInput = new TextField();
		prfInput.setPromptText("Female/Male");
		GridPane.setConstraints(prfInput, 1, 2);
		
		Button search = new Button("Search");
		
		search.setOnAction(e -> {
			try {
				ResultPage.display(minInput, maxInput, avbInput, prfInput, null);
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
		GridPane.setConstraints(search, 0, 3);
		
		grid.getChildren().addAll(prg, min, minInput, max, maxInput, avb, avbInput, prf, prfInput, search);
		
		Scene scene = new Scene(grid, 300, 250);
		window.setScene(scene);
		window.showAndWait();
	}

	
}
