package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreateUser {
	private VBox vBox = new VBox();
	private Button button = new Button("Done");
	private TextField text = new TextField();
	private Label label = new Label("Enter your username.");
	private Scene createUserScene;
	private Stage stage = new Stage();
	
	public CreateUser() {
		this.vBox.getChildren().addAll(label, text, button);
		this.vBox.setAlignment(Pos.TOP_CENTER);
		this.vBox.setSpacing(10);
		
		this.label.setFont(new Font("Helvatica", 12));
		this.text.setMaxWidth(250); 
		
		this.createUserScene = new Scene(vBox, 300, 100);
		this.stage.setScene(createUserScene);
		
		this.stage.setHeight(140);
		this.stage.setWidth(300);
		this.stage.setTitle("Create User");
		this.stage.centerOnScreen();
		
	}
	
	public Label getLabel() {
		return this.label;
	}
	
	public Button getButton() {
		return this.button;
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
}
