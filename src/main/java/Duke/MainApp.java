package Duke;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application{

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Shirmin shirmin;
    final int imageSize = 50; // square image so only one field
    final int closeWaitTime = 1000;
    final int stageMinHeight = 600;
    final int stageMinWidth = 400;
    final int panePrefWidth = 385;
    final int panePrefHeight = 535;
    final int userInputWidth = 325;
    final int sendButtonWidth = 55;
    @Override
    public void init() throws Exception {
        shirmin = new Shirmin();
    }

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        Button sendButton = new Button("Send");
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Shirmin");
        stage.setResizable(false);
        stage.setMinHeight(stageMinHeight);
        stage.setMinWidth(stageMinWidth);

        mainLayout.setPrefSize(stageMinWidth, stageMinHeight);

        scrollPane.setPrefSize(panePrefWidth, panePrefHeight);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        userInput.setPrefWidth(userInputWidth);
        sendButton.setPrefWidth(sendButtonWidth);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

    }

    private final Image user = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/lulu.jpg")));
    private final Image min = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/Shirmin.jpg")));

    /** Functions Status
     * 9/9 complete  hell yea        - retest whenever big changes are made
     * LIST           WORKING
     * MARK           WORKING
     * UNMARK         WORKING
     * TODO           WORKING
     * DEADLINE       WORKING
     * EVENT          WORKING
     * DELETE         WORKING
     * FIND           WORKING
     * BYE            WORKING
     */
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().equalsIgnoreCase("bye")) {
            sayGoodbye();
            return;
        }
        handleOtherInputs(input);
        userInput.clear();
    }
    private void sayGoodbye() {
        Label goodbyeText = new Label("Goodbye! See you again!");
        ImageView dukeImageView = new ImageView(min);
        dukeImageView.setFitHeight(imageSize);
        dukeImageView.setFitWidth(imageSize);
        DialogBox goodbyeBox = new DialogBox(goodbyeText, dukeImageView);
        dialogContainer.getChildren().add(goodbyeBox);

        Platform.runLater(() -> {
            try {
                Thread.sleep(closeWaitTime);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            Platform.exit();
        });
    }

    /**
     * Handles non-bye inputs, encapsulating the previous logic for handling user inputs.
     * @param input The user input to handle.
     */
    private void handleOtherInputs(String input) {
        Label userText = new Label(input);
        ImageView userImageView = new ImageView(user);
        userImageView.setFitHeight(imageSize);
        userImageView.setFitWidth(imageSize);
        DialogBox userInputBox = DialogBox.getUserDialog(userText, userImageView);

        String response = shirmin.runCommand(input);
        Label shirminText = new Label(response);
        ImageView shirminImageView = new ImageView(min);
        shirminImageView.setFitHeight(imageSize);
        shirminImageView.setFitWidth(imageSize);
        DialogBox shirminResponseBox = DialogBox.getShirminDialog(shirminText, shirminImageView);

        dialogContainer.getChildren().addAll(userInputBox, shirminResponseBox);
    }
}


