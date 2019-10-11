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

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	Stage window;
	private TableView<Room> table;
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			window.setTitle("Home");
			UserData data = new UserData();
			
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(10, 10, 10, 10));
			grid.setVgap(8);
			grid.setHgap(10);
			
			Button sgin = new Button("Sign In");
			sgin.setOnAction(e -> SignIn(data));
			GridPane.setConstraints(sgin, 1, 0);
			
			Button ca = new Button("Create Account");
			//ca.setOnAction(e -> CreateAccount.display("Create Account", data));
			ca.setOnAction(e -> CreateAccount("Create Account", data));
			GridPane.setConstraints(ca, 1, 1);
			
			grid.getChildren().addAll(sgin, ca);
			
			Scene scene = new Scene(grid, 600, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void CreateAccount(String string, UserData data) {
		// TODO Auto-generated method stub
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Create New Account");
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
		PasswordField passInput = new PasswordField();
		passInput.setPromptText("Password");
		GridPane.setConstraints(passInput, 1, 1);
		
		Label cfmpwd = new Label("Confirm Password:");
		GridPane.setConstraints(cfmpwd, 0, 2);
		PasswordField cfmpwdInput = new PasswordField();
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
			        
			    SignIn(data);
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
		
		Scene scene = new Scene(grid, 600, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}

	private void SignIn(UserData data) {
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
		PasswordField passInput = new PasswordField();
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
			        login(user, data, eh);
				}
				else
					corrmsg.setText("Wrong password");
			else
				corrmsg.setText("No User");
		});
		GridPane.setConstraints(login, 0, 3);
		
		Button cont = new Button("Continue");
		cont.setOnAction(e -> FindRoom(null));
		GridPane.setConstraints(cont, 2, 3);
		
		grid.getChildren().addAll(name, nameInput, password, passInput, login, cont, corrmsg);
		
		Scene scene = new Scene(grid, 600, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}
	
	
		private void login(User user, UserData data, EditHouse eh) {
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
			ep.setOnAction(e -> EditProfile(eh,data,user));
			ep.setMinWidth(280);
			GridPane.setConstraints(ep, 0, 1);
			
			Button anh = new Button("Add a New House");
			anh.setMinWidth(280);
			anh.setOnAction(e -> AddHouse(user,data, eh));
			GridPane.setConstraints(anh, 0, 2);
			
			Button fr = new Button("Find Room");
			fr.setMinWidth(280);
			fr.setOnAction(e -> FindRoom(user));
			GridPane.setConstraints(fr, 0, 3);
			
			
			grid.getChildren().addAll(ep, anh, fr);
			Scene scene = new Scene(grid, 600, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
	}
		private void FindRoom(User user) {
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
			GridPane.setConstraints(min, 1, 1);
			TextField minInput = new TextField();
			GridPane.setConstraints(minInput, 2, 1);
			Label max = new Label("Maximum Price:");
			GridPane.setConstraints(max, 1, 2);
			TextField maxInput = new TextField();
			GridPane.setConstraints(maxInput, 2, 2);
			
			
			Label avb = new Label("Availability:");
			GridPane.setConstraints(avb, 0, 3);
			TextField avbInput = new TextField();
			avbInput.setPromptText("number in unit of months");
			GridPane.setConstraints(avbInput, 1, 3);
			
			Label prf = new Label("Preference:");
			GridPane.setConstraints(prf, 0, 4);
			TextField prfInput = new TextField();
			prfInput.setPromptText("Female/Male");
			GridPane.setConstraints(prfInput, 1, 4);
			
			Button search = new Button("Search");
			GridPane.setConstraints(search, 0, 5);
			search.setOnAction(e -> {
				try {
					ResultPage(minInput, maxInput, avbInput, prfInput,user);
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
			
			grid.getChildren().addAll(prg, min, minInput, max, maxInput, avb, avbInput, prf, prfInput, search);
			
			Scene scene = new Scene(grid, 600, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
		}

		@SuppressWarnings("unchecked")
		private void addHouse(UserData data, EditHouse eh, User user, TextField prcInput, TextField avbInput, TextField prfInput) throws FileNotFoundException, IOException, ParseException {
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
			login(user, data, eh);
		}

		private void AddHouse(User user, UserData data, EditHouse eh) {
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
			
			Scene scene = new Scene(grid, 600, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
		}

		private void EditProfile(EditHouse eh, UserData data, User user) {
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
			ep.setOnAction(e -> EditPwd(user,data));
			ep.setMinWidth(280);
			GridPane.setConstraints(ep, 0, 1);
			
			Button anh = new Button("Edit Your Houses");
			anh.setMinWidth(280);
			anh.setOnAction(e -> {
				EditHouse(data,user);
			});
			GridPane.setConstraints(anh, 0, 2);
			
			Button fr = new Button("Your Favorite List");
			fr.setMinWidth(280);
			fr.setOnAction(e -> {
				try {
					FavoList(user);
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
			Scene scene = new Scene(grid, 600, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
		}

		private void EditPwd(User user, UserData data) {
			// TODO Auto-generated method stub
			Stage window = new Stage();
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(500);
			window.setMinHeight(500);
			
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
			PasswordField newInput = new PasswordField();
			GridPane.setConstraints(newInput, 1, 1);
			
			Label cfmpwd = new Label("Confirm Password:");
			GridPane.setConstraints(cfmpwd, 0, 2);
			PasswordField cfmpwdInput = new PasswordField();
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
				SignIn(data);
			}
		else {
			corrmsg.setText("Try Again");
		}  }
			);
			GridPane.setConstraints(upd, 0, 3);	
			
			grid.getChildren().addAll(oldpwd, oldInput, newpwd, newInput, cfmpwd, cfmpwdInput, upd, corrmsg);
			
			Scene scene = new Scene(grid, 300, 250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
		}

		@SuppressWarnings("unchecked")
		private void EditHouse(UserData data, User user) {
			// TODO Auto-generated method stub
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
			 

			ObservableList<Room> list = null;
			try {
				list = (ObservableList<Room>) getRoom(user);
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			table.setItems(list);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			table.getColumns().addAll(rentCol, availCol, prefCol);
			
			VBox box = new VBox();
			box.getChildren().addAll(table);
			
			Scene scene = new Scene(box);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
		}
		
		@SuppressWarnings("unchecked")
		public void rentEdit(Event e, User user) throws IOException, ParseException {
			TableColumn.CellEditEvent<Room, String> editEvent;
			editEvent = (TableColumn.CellEditEvent<Room, String>) e;
			Room s = editEvent.getRowValue();
			s.setRent(editEvent.getNewValue(),user);
			//update(s, user);
		}
		
		@SuppressWarnings("unchecked")
		public void availEdit(Event e,User user) throws IOException, ParseException {
			TableColumn.CellEditEvent<Room, String> editEvent;
			editEvent = (TableColumn.CellEditEvent<Room, String>) e;
			Room s = editEvent.getRowValue();
			s.setAvail(editEvent.getNewValue(),user);
		}
		
		@SuppressWarnings("unchecked")
		public void prefEdit(Event e, User user) throws IOException, ParseException {
			TableColumn.CellEditEvent<Room, String> editEvent;
			editEvent = (TableColumn.CellEditEvent<Room, String>) e;
			Room s = editEvent.getRowValue();
			s.setPref(editEvent.getNewValue(), user);
		}
		
		private ObservableList<Room> getRoom(User user) throws FileNotFoundException, IOException, ParseException {
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
		    	System.out.println(roomArray);
		    	for(Object room: roomArray) {
		    		JSONObject oneRoom = (JSONObject) room;
		    		allRoom.add(new Room((String)oneRoom.get("Rent"),
		    				(String)oneRoom.get("Availability"), (String)oneRoom.get("Preference")));
		    	}
		    	}
		    }
			return allRoom;
		}

		private void FavoList(User user) throws FileNotFoundException, IOException, ParseException {
			// TODO Auto-generated method stub
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
			
			Scene scene = new Scene(box, 600, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
		}
		
		@SuppressWarnings({ "unchecked", "unused" })
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

		// TODO Auto-generated method stub
		public void Nologin(String string) {
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
					ResultPage(minInput, maxInput, avbInput, prfInput, null);
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
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
	}

	private void ResultPage(TextField minInput, TextField maxInput, TextField avbInput, TextField prfInput,
				User user) throws FileNotFoundException, IOException, ParseException {
		Stage window = new Stage();
		int min = Integer.parseInt(minInput.getText());
		int max = Integer.parseInt(maxInput.getText());
		int avb = Integer.parseInt(avbInput.getText());
		List<Room> candidates;
		candidates = selectCand(min, max, avb, prfInput.getText());
		//System.out.println(candidates);
		List<String> display = getResult(candidates);
		//System.out.println(display);
		Button addfavo = new Button("Add to Favorite List");
		
		ObservableList<String> items =
		        FXCollections.observableArrayList(display);
		    ListView<String> roomdes = new ListView<>(items);
		    roomdes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		    roomdes.setMaxWidth(600);
		    Label corrmsg = new Label();
			corrmsg.setFont(Font.font(18));
		    addfavo.setOnAction(e->{
		    List<String> sltRoom =
		            new ArrayList<String>(roomdes.getSelectionModel().getSelectedItems());
		    try {
				write(user, sltRoom, corrmsg);
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		    });
		    FavoList favo = new FavoList();
		    //favo.insert((roomdes.getSelectionModel().getSelectedItems()).toString());
		    
		    // Add a button to continue adding to favorites.
	   
	   VBox root = new VBox();
		    root.getChildren().addAll(roomdes, addfavo, corrmsg);
		    Scene scene = new Scene(root, 700, 700);		
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    window.setScene(scene);
		    window.show();		    
	}
	
	  @SuppressWarnings("unchecked")
	private void write(User user, List<String> rooms, Label corrmsg) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		  if(user != null) {
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
	  	          newusr.put("Houses", user.rooms);
	  	          JSONArray house = (JSONArray) usr.get("Favorite");
	  	          for(String rm:rooms) {
	  	          house.add(rm);
	  	          }
	  	          newusr.put("Favorite", house);
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
	          writeFile.close(); }
		  else
		  {
			  corrmsg.setText("Please Login First");
		  }
        
		}
	  
	  private List<String> getResult(List<Room> candidates) {
			// TODO Auto-generated method stub
			  List<String> str = new ArrayList<String>();
			  for(Room cand : candidates) {
				  str.add("Rent:" + cand.rent + "   " + 
						  "Availability:" + cand.avail + "   " + "Preference:" + cand.pref);
			  }
			return str;
		}

		private List<Room> selectCand(int min, int max, int avb, String string) 
				  throws FileNotFoundException, IOException, ParseException {
			// TODO Auto-generated method stub
			List<Room> wholeList = parseJson("room.json"); 
			List<Room> selectedList = new ArrayList<Room>();
			//System.out.println(wholeList.get(0).rent);
			for(Room room:wholeList) {
				//System.out.println(room.rent);
				int rent = Integer.parseInt(room.rent);
				int avail = Integer.parseInt(room.avail);
				if(min <= rent && max >= rent && avb == avail && string.equals(room.pref)) {
					selectedList.add(room);
				}
			}
			return selectedList;
		}

		public List<Room> parseJson(String filename)
				  throws FileNotFoundException, IOException, ParseException{
			    Object obj = new JSONParser().parse(new FileReader(filename));
			    JSONObject jsonObj = (JSONObject) obj;
			    JSONArray rooms = (JSONArray) jsonObj.get("roomInfo");
			    List<Room> Rooms = new ArrayList<Room>();
			    // get question array content
			    for (Object room : rooms) {
			      JSONObject roomObj = (JSONObject) room;
			      Rooms.add(new Room((String)(roomObj.get("rent")),
			    		     (String) roomObj.get("availability"),(String) roomObj.get("preference")));
			    }
			    return Rooms;
			  }


	private boolean checkMatch(TextField passInput, TextField cfmpwdInput) {
		// TODO Auto-generated method stub
        if(passInput.getText().equals(cfmpwdInput.getText()))
        	return true;
        else
        	return false;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
} 
