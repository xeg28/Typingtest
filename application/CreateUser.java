package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateUser {
	private VBox vBox = new VBox();
	private Button button = new Button("Done");
	private TextField text = new TextField();
	private Label label = new Label("Enter your username.");
	private Scene createUserScene;
	
	public CreateUser() {
		this.vBox.getChildren().addAll(label, text, button);
		this.vBox.setAlignment(Pos.TOP_CENTER);
		this.vBox.setSpacing(10);
		this.createUserScene = new Scene(vBox, 300, 100); 
		
	}
	
	public Button getButton() {
		return this.button;
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public Scene getScene() {
		return this.createUserScene;
	}
}
