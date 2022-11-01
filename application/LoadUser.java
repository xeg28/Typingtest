package application;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoadUser {
	private VBox vBox = new VBox();
	private Button button = new Button("Select");
	private Label label = new Label("Select user.");
	private Scene scene;
	private ComboBox<Users> cBox = new ComboBox();
	
	public LoadUser() {
	}
	
	public LoadUser(ArrayList<Users> users) {
		populateComboBox(users);
		this.vBox.getChildren().addAll(label, cBox, button);
		this.vBox.setAlignment(Pos.TOP_CENTER);
		this.vBox.setSpacing(10);
		this.scene = new Scene(vBox, 300, 100); 
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public Button getButton() {
		return this.button;
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
