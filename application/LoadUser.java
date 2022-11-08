package application;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoadUser {
	private VBox vBox = new VBox();
	private Button selectButton = new Button("Select");
	private Button deleteButton = new Button("Delete");
	private Label label = new Label("Select user.");
	private Scene scene;
	private ComboBox<Users> cBox = new ComboBox();
	private Stage stage = new Stage();
	public LoadUser() {
	}
	
	public LoadUser(ArrayList<Users> users) {
		populateComboBox(users);
		this.vBox.getChildren().addAll(label, cBox, selectButton, deleteButton);
		this.vBox.setAlignment(Pos.TOP_CENTER);
		this.vBox.setSpacing(10);
		
		this.label.setFont(new Font("Helvatica", 12));
		this.scene = new Scene(vBox, 300, 100); 
		this.stage.setScene(scene);
		
		this.stage.setHeight(200);
		this.stage.setWidth(300);
		this.stage.setTitle("Load User");
		this.stage.centerOnScreen();
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public Button getSelectButton() {
		return this.selectButton;
	}
	
	public Button getDeleteButton() {
		return this.deleteButton;
	}
	
	public ComboBox<Users> getComboBox() {
		return this.cBox;
	}
	
	public void populateComboBox(ArrayList<Users> users) {
		for(int i = 0; i < users.size(); i++) {
			this.cBox.getItems().add(users.get(i));
		}
	}
	
	public void resetComboBox() {
		cBox = new ComboBox<>();
	}
	
}
