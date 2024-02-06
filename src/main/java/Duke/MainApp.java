package Duke;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintStream;

public class MainApp extends Application{

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private VBoxOutputStream customOut;
    private Duke duke;
    @Override
    public void init() throws Exception {
//`        super.init();
//        VBox dialogContainer = new VBox();
//        customOut = new VBoxOutputStream(dialogContainer); // Initialize with your VBox instance
//        System.setOut(new PrintStream(customOut, true));`
        duke = new Duke();



    }

    public static void main(String[] args) {
        // ...
    }
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!"); // Creating a new Label control
        Scene scene = new Scene(helloWorld); // Setting the scene to be our Label

        stage.setScene(scene); // Setting the stage to show our screen
        stage.show(); // Render the stage.
        //Step 1. Setting up required components

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Shirmin");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        //You will need to import `javafx.scene.layout.Region` for this.
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        //Step 3. Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });
        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

    }
    /**
     * Iteration 1:
     * Creates a label with the specified text and adds it to the dialog container.
     * @param text String containing text to add
     * @return a label with the specified text that has word wrap enabled.
     */
    private Label getDialogLabel(String text) {
        Label textToAdd = new Label(text);
        textToAdd.setWrapText(true);

        return textToAdd;
    }
    private final Image user = new Image(this.getClass().getResourceAsStream("/images/lulu.jpg"));
    private final Image min = new Image(this.getClass().getResourceAsStream("/images/Shirmin.jpg"));

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
//        Label userText = new Label(userInput.getText());
//        Label dukeText = new Label(getResponse(userInput.getText()));
//        dialogContainer.getChildren().addAll(
//                DialogBox.getUserDialog(userText, new ImageView(user)),
//                DialogBox.getDukeDialog(dukeText, new ImageView(min))
//        );
//        userInput.clear();
        String input = userInput.getText();
//        if (!input.isEmpty()) {
//            String response = duke.runCommand(input); // Use Duke to process the command and get the response
//            // Now display the response in your GUI
//            Label dukeResponse = getDialogLabel(response); // You might adjust getDialogLabel for better formatting
//            dialogContainer.getChildren().add(dukeResponse);
//            userInput.clear(); // Clear the input field after processing
//        }
//
//
//        String input = userInput.getText();
        if (!input.isEmpty()) {
            // Display user input with user image
            Label userText = new Label(input);
            ImageView userImageView = new ImageView(user); // Assuming 'user' is the Image for the user
            userImageView.setFitHeight(50); // Adjust size as needed
            userImageView.setFitWidth(50);
            DialogBox userInputBox = new DialogBox(userText, userImageView);

            // Get Duke's response and display it with Duke's image
            String response = duke.runCommand(input); // Use Duke to process the command and get the response
            Label dukeText = new Label(response);
            ImageView dukeImageView = new ImageView(min); // Assuming 'min' is the Image for Duke
            dukeImageView.setFitHeight(50); // Adjust size as needed
            dukeImageView.setFitWidth(50);
            DialogBox dukeResponseBox = new DialogBox(dukeText, dukeImageView);

            dialogContainer.getChildren().addAll(userInputBox, dukeResponseBox); // Add both DialogBoxes to the container
            userInput.clear(); // Clear the input field after processing
        }
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    private String getResponse(String input) {
        return "Duke heard: " + input;
    }
}


