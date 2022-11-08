package application;
	
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

//TODO: Make the program automatically switch users when a user is deleted.
public class Main extends Application {
	private TextField text = new TextField();
	private Label wpmLabel = new Label();
	private TextArea quoteTextArea = new TextArea();
	private Label titleLabel = new Label();
	private Label userWPMDataLabel = new Label();
	private Users currentUser = new Users("Default User");
	
	private PauseTransition wpmPause = new PauseTransition(Duration.millis(100));
	private PauseTransition highlightPause = new PauseTransition(Duration.millis(1));
	private long start = 0;
    private int currentQuoteIndex;
    private boolean restart = true;
	
	
	public void start(Stage primaryStage) {
		KeyCombination newQuoteKey = new KeyCodeCombination(KeyCode.K, KeyCodeCombination.ALT_DOWN, KeyCodeCombination.CONTROL_DOWN);
		try {
			Image icon = new Image(this.getClass().getResourceAsStream("/images/icon.png"));
            primaryStage.getIcons().add((Image)icon);
            primaryStage.centerOnScreen();
            
			BorderPane border = new BorderPane();
			Scene scene = new Scene(border, 1200, 600);
			
			HBox titleHBox = addTitleHBox();
			Button createUserBtn = new Button("Create User");
			Button loadUserBtn = new Button("Load User");
			
			
			currentUser.loadUsers();
			LoadUser loadUser = new LoadUser(currentUser.getUsers());
			Stage loadUserStage = loadUser.getStage();
			
			loadUserBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					loadUserStage.show();
				}
			});
			
			loadUser.getDeleteButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					Users removedUser = loadUser.getComboBox().getValue();
					currentUser.deleteUser(removedUser);
					loadUser.getComboBox().getItems().remove(removedUser);
				}
			});
			
			loadUser.getSelectButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(loadUser.getComboBox().getValue() != null) {
						loadUser.getComboBox().getValue().setUsers(currentUser.getUsers());
						currentUser = loadUser.getComboBox().getValue();
						titleLabel.setText(currentUser.getName());
						userWPMDataLabel.setText("Highest WPM: " + currentUser.getBestWPM() + 
						" wpm"+ "\t\tAverage WPM: " + currentUser.getAvgWPM() + " wpm");
						loadUserStage.close();
					}
				}
			});
			
			titleHBox.getChildren().addAll(createUserBtn, loadUserBtn);
			border.setTop(titleHBox);
			
			CreateUser createUser = new CreateUser();
			Stage createUserStage = createUser.getStage();
			
			createUserBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					createUserStage.show();
					
				}
			});
			createUser.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(createUser.getText().length() <= 17) {
						Users newUser = new Users(createUser.getText());
						currentUser.saveUsers(newUser);
						loadUser.getComboBox().getItems().add(newUser);
						createUser.getStage().close();
					}
					else {
						createUser.getLabel().setText("Enter a name less than 17 characters");
					}
				}
			});
			
			HBox wpmHBox = addWPMHBox();
			border.setRight(wpmHBox);
			this.wpmLabel.setText("0.0 wpm");
			
			
			Button addQuoteBtn = new Button("Add Quote");
			VBox lowerVbox = new VBox();
		    lowerVbox.setPadding(new Insets(40, 30, 40, 30));
		    lowerVbox.setSpacing(10);
			border.setBottom(lowerVbox);
			
			
			TextField addQuoteText = new TextField();
			addQuoteText.setPromptText("Enter a quote.");
			lowerVbox.getChildren().addAll(addQuoteText, addQuoteBtn);
			
			QuoteWriter write = new QuoteWriter();
			addQuoteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(addQuoteText.getText().length() >= 44) {
						write.writeQuote(addQuoteText.getText());
						addQuoteText.setPromptText("Enter a quote.");
					}
					else {
						addQuoteText.setPromptText("Quote must have 44 or more characters.");
					}
					try {
						write.readQuote();
						if(write.getQuotes().size() == 1) {
							setRandQuote(write.getQuotes());
						}
						addQuoteText.clear();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			write.readQuote();

			Button deleteQuoteButton = new Button("Delete Quote");
            deleteQuoteButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle( MouseEvent arg0) {
                    try {
                        deleteQuote(write);
                        setRestart(true);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
			VBox centerVBox = addQuoteVBox(write.getQuotes());
			centerVBox.getChildren().addAll(this.text, deleteQuoteButton);
			border.setCenter(centerVBox);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Typeracer Clone");
			primaryStage.show();
			
			this.text.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent arg0) {
	                if (getRestart()) {
	                    start();
	                    updateWPM();
	                    updateTextHighlight();
	                    setRestart(false);
	                }
	                if (newQuoteKey.match(arg0)) {
	                    setRestart(true);
	                    wpmLabel.setText("0.0 wpm");
	                    setRandQuote(write.getQuotes());
	                    System.out.println("in QuoteKey");
	                }	
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		Application.launch(args);
	}
	
    private void startTypeTest() {
        this.start();
        this.updateWPM();
        this.updateTextHighlight();
    }
    
    private void setRandQuote( ArrayList<Quote> quotes) {
    	if(quotes.isEmpty()) {
    		this.quoteTextArea.setText("No quotes left, add a quote. To get the default quotes back"
    				+ ", delete the Quotes.txt file.");
    	    this.text.clear();
    		return;
    	}
    	
        int randInt = (int)(Math.random() * quotes.size());
        this.quoteTextArea.setText(quotes.get(randInt).toString());
        this.currentQuoteIndex = randInt;
        this.text.clear();
    }
	
	
	private HBox addTitleHBox() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(50);
	    hbox.setStyle("-fx-background-color: #336699;");
	    
		userWPMDataLabel.setText("Highest WPM: " + currentUser.getBestWPM() + 
		" wpm"+ "\t\tAverage WPM: " + currentUser.getAvgWPM() + " wpm");
	    userWPMDataLabel.setFont(Font.font("Helvatica", FontWeight.EXTRA_BOLD, 20));
	    userWPMDataLabel.setStyle("-fx-text-fill: #FFFFFF");
	    userWPMDataLabel.setPrefWidth(600);
	    
	    titleLabel.setText(currentUser.getName());
	    titleLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 25));
	    titleLabel.setStyle("-fx-text-fill: #FFFFFF");
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(titleLabel, userWPMDataLabel);
	    return hbox;
	}
	 
	private HBox addWPMHBox() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    wpmLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 35));
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(this.wpmLabel);

	    return hbox;
	}

	private VBox addQuoteVBox(ArrayList<Quote> quotes) {
		VBox vbox = new VBox();
        vbox.setPadding(new Insets(15.0, 12.0, 15.0, 12.0));
        vbox.setSpacing(10.0);
        
        int randInt = (int)(Math.random() * quotes.size());
         
        this.currentQuoteIndex = randInt;
        setRandQuote(quotes);
        this.quoteTextArea.setWrapText(true);
        this.quoteTextArea.setPrefWidth(900.0);
        this.quoteTextArea.setMaxHeight(400.0);
        this.quoteTextArea.setEditable(false);
        this.quoteTextArea.setFont(Font.font("Helvatica", FontWeight.BOLD, 20.0));
        
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.getChildren().add(this.quoteTextArea);
        
        return vbox;
	}	
	
	private void endOfTest(double finalWPM) {
		currentUser.addTest(finalWPM);
		currentUser.updateAndSave(currentUser);
		
		userWPMDataLabel.setText("Highest WPM: " + currentUser.getBestWPM() +  
		" wpm"+ "\t\tAverage WPM: " + currentUser.getAvgWPM() + " wpm");
		
		this.wpmLabel.setText(finalWPM + " wpm");
		System.out.println(userWPMDataLabel.getWidth());
		this.quoteTextArea.clear();
		this.quoteTextArea.setText("Press CTRL+ALT+K to go to the next test.");
		wpmPause.stop();
	}
	
    private void updateWPM() {
        wpmPause.setOnFinished(event ->{
        	this.wpmLabel.setText(getWPM()+ " wpm");
        	if(this.text.getText().equals(quoteTextArea.getText())) {
        		double finalWPM = getWPM();
        		endOfTest(finalWPM);
        	}
        	else {
        		wpmPause.play();
        	}
        });
        wpmPause.play();
      
    }
    
    private void updateTextHighlight() {
    	highlightPause.setOnFinished(event ->{
        	highlightText();
        	if(this.text.getText().equals(quoteTextArea.getText())) {
        		highlightPause.stop();
        	}
        	else {
        		highlightPause.play();
        	}
        });
    	highlightPause.play();
    }
    


    private double getWPM() {
    	DecimalFormat format1 = new DecimalFormat("0.#");
    	double userWPM = Double.parseDouble(format1.format(((this.text.getText().length()/5.0)/elapsedTime())*60));
    	return userWPM;
    }

	private void highlightText() {
    	String userTyped = this.text.getText();
		String quote = quoteTextArea.getText();
		String testQuote = "";
		
		for(int i = 0; i < userTyped.length(); i++) {
			if(userTyped.length() < quote.length()) {
				testQuote += quote.charAt(i);
			}

			else {
				testQuote = quote;
			}
		}

		if(testQuote.equals(userTyped)) {
			 this.quoteTextArea.setStyle("-fx-text-fill: #000000");
			 this.quoteTextArea.selectRange(0, userTyped.length());
		}
		else {
			this.quoteTextArea.deselect();
			this.quoteTextArea.selectRange(numOfChars(), userTyped.length());
			this.quoteTextArea.setStyle("-fx-text-fill: #FF0000");
		}
    }
    
    private int numOfChars() {
    	int numOfChars = 0;
    	String userTyped = this.text.getText();
		String quote = quoteTextArea.getText();
    	for(int i = 0; i < this.text.getText().length(); i++) {
    		if(userTyped.charAt(i) != quote.charAt(i)) {
    			break;
    		}
    		else if(userTyped.charAt(i) == quote.charAt(i))
    			numOfChars++;
    	}
    	return numOfChars;
    	
    }
    
    private void deleteQuote( QuoteWriter writer) throws IOException {
        writer.deleteQuote(this.currentQuoteIndex);
        writer.writeQuote();
        this.setRandQuote(writer.getQuotes());
        this.startTypeTest();
    }
    
    public void start() {
    	this.start = System.nanoTime();
    }
    
    public void setRestart( boolean bool) {
        this.restart = bool;
    }
    
    public boolean getRestart() {
        return this.restart;
    }
    
    public double elapsedTime() {
        long now = System.nanoTime();
        return (now - start) / 1000000000.0;
    }
    
}


	