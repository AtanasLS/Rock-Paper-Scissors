package rps.gui.controller;

// Java imports
import animatefx.animation.Bounce;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import rps.gui.model.GameViewModel;
import javafx.animation.KeyFrame;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private ImageView rockImage,
            paperImage,
            sicissorsImage,
            playerDesicionView,
            aiDescisionImage,
            compImage,
            resultImage,
            leftHand,
            rightHand,
            humanImg;
    @FXML
    private Label resultLabelAI, resultLabelPlayer,resultLabel,roundCount;
    @FXML
    private MFXButton restartBtn,endSession;
    private int scoreAI = 0;
    private int scorePlayer = 0;

    private String selection;

    GameViewModel model = new GameViewModel();

    private String imageAI = model.getCompImage();

    private final int MAX_ROUNDS = 5;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setImages();
        restartBtn.setOnAction(event -> handleRestartBtn(event));
        endSession.setOnAction(event -> handleEndSession(event));
    }

    private void setImages(){
        setOriginalHands();
        rockImage.setImage(new Image("/icons/left-hand.png"));
        paperImage.setImage(new Image("/icons/paper-icon.png"));
        sicissorsImage.setImage(new Image("/icons/sicossors-icon.png"));
        compImage.setImage(new Image("/icons/AI-starting-icon.png"));
        humanImg.setImage(new Image("/icons/human-face.png"));
    }

    private void setOriginalHands() {
        leftHand.setImage(new Image("/icons/left-hand.png"));
        rightHand.setImage(new Image("/icons/right-hand.png"));
    }

    public void handleScissors(MouseEvent mouseEvent) {
        handleSelection("/icons/sicossors-icon.png" ,"Scissor");
    }

    public void handlePaper(MouseEvent mouseEvent) {
        handleSelection("/icons/paper-icon.png", "Paper");
    }

    public void rockHandle(MouseEvent mouseEvent) {
        handleSelection("/icons/left-hand.png",  "Rock");
    }

    private void handleSelection(String imageFileP , String selection) {
        setOriginalHands();

        Bounce leftHandBounce = new Bounce(leftHand);
        Bounce rightHandBounce = new Bounce(rightHand);

        EventHandler<ActionEvent> setImage = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Map<String, Integer> result = model.getWinner(selection);
                roundCount.setText(String.valueOf(result.entrySet().iterator().next().getValue()));
                leftHand.setImage(new Image(imageFileP));
                resultLabel.setText(model.getOutput());
                String imageFileAI = model.getCompImage();
                rightHand.setImage(new Image(imageFileAI));
                resultLabel.setVisible(true);
                changeToLastView(selection);
            }
        };

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    leftHandBounce.play();
                    rightHandBounce.play();
                }),
                new KeyFrame(Duration.seconds(1), setImage)
        );

        timeline.play();
    }

    public void changeToLastView(String selection) {
        this.selection = selection;
        if (this.selection != null) {
            if (model.getOutput().contains("Human Win")) {
                scorePlayer++;
                resultLabelPlayer.setText("" + scorePlayer);
                playerDesicionView.setVisible(false);
                resolveScore();
            } else if (model.getOutput().contains("Tie")) {
                playerDesicionView.setVisible(false);
                resolveScore();
            } else if (model.getOutput().contains("AI")){
                scoreAI++;
                resultLabelAI.setText("" + scoreAI);
                playerDesicionView.setVisible(false);
                resolveScore();
            }
        }
    }

    private void resolveScore(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(Integer.parseInt(roundCount.getText()) == MAX_ROUNDS){
            playerDesicionView.setVisible(false);
            if(scorePlayer > scoreAI){
                alert.setContentText("You won! Your score: " + scorePlayer + ", AI score: " + scoreAI);
                alert.show();
            } else if(scorePlayer < scoreAI) {
                alert.setContentText("You lost! Your score: " + scorePlayer + ", AI score: " + scoreAI);
                alert.show();
            } else {
                alert.setContentText("It's a tie! Your score: " + scorePlayer + ", AI score: " + scoreAI);
                alert.show();
            }
            clearScreen();
        }
    }
    public void clearScreen(){
        scorePlayer = 0;
        resultLabel.setText("");
        roundCount.setText("0");
        resultLabelPlayer.setText("" + scorePlayer);
        scoreAI = 0;
        resultLabelAI.setText("" + scoreAI);
        setOriginalHands();
        model.resetScore();
    }

    private void handleRestartBtn(ActionEvent actionEvent) {
        clearScreen();
        actionEvent.consume();
    }

    private void handleEndSession(ActionEvent event) {
        if(Integer.parseInt(roundCount.getText()) != MAX_ROUNDS){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to close unfinished game ? ");
            var result = alert.showAndWait();

            if(result.get().equals(ButtonType.OK)){
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.close();
            }
        }
        event.consume();
    }
}
