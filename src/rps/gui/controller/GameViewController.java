package rps.gui.controller;

// Java imports
import animatefx.animation.Bounce;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import rps.gui.model.GameViewModel;
import javafx.animation.KeyFrame;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {

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
    private Label versusLabel, playerNameLabel, aiNamelLabel, resultLabelAI, resultLabelPlayer,resultLabel;
    @FXML
    private MFXButton restartBtn;
    private int scoreAI = 0;
    private int scorePlayer = 0;

    private String decision;

    GameViewModel model = new GameViewModel();

    private String imageAI = model.getCompImage();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setImages();
    }

    private void setImages(){
        setOriginalHands();
        rockImage.setImage(new Image("/icons/rock-icon.png"));
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
        handleSelection("/icons/rock-icon.png",  "Rock");

    }

    private void handleSelection(String imageFileP , String selection) {
        setOriginalHands();

        Bounce leftHandBounce = new Bounce(leftHand);
        Bounce rightHandBounce = new Bounce(rightHand);


        EventHandler<ActionEvent> setImage = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                leftHand.setImage(new Image(imageFileP));
                resultLabel.setText(model.getWinner(selection));
                String imageFileAI = model.getCompImage();
                rightHand.setImage(new Image(imageFileAI)); //Done! but maybe can be better written
                resultLabel.setVisible(true);
                changeToLastView();
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

    public void changeToLastView() {
        if (decision != null) {
            if (model.getWinner(decision).contains("Human Win")) {
                scorePlayer++;
                resultLabelPlayer.setText("" + scorePlayer);
                playerDesicionView.setVisible(false);
                versusLabel.setVisible(false);
                resultImage.setVisible(true);
               // resultImage.setImage(new Image("/icons/win-icon.png"));
            } else if (model.getWinner(decision).contains("Tie")) {
                playerDesicionView.setVisible(false);
                versusLabel.setVisible(false);
                resultImage.setVisible(true);
              //  resultImage.setImage(new Image("/icons/draw-icon.png"));
            } else if (model.getWinner(decision).contains("AI")){
                scoreAI++;
                resultLabelAI.setText("" + scoreAI);
                playerDesicionView.setVisible(false);
                versusLabel.setVisible(false);
                resultImage.setVisible(true);
              //  resultImage.setImage(new Image("/icons/lose-icon.png"));
            }
        }
    }
    public void clearScreen(){
        scorePlayer = 0;
        resultLabelPlayer.setText("" + scorePlayer);
        scoreAI = 0;
        resultLabelAI.setText("" + scoreAI);
        setOriginalHands();
    }

    public void handleRestartBtn(ActionEvent actionEvent) {
        clearScreen();
    }
}
