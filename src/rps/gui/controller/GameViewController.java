package rps.gui.controller;

// Java imports
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import jdk.jfr.TransitionFrom;
import rps.bll.game.*;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;
import rps.gui.model.GameViewModel;
//import rps.gui.Model.GameViewModel;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {

    @FXML
    public ImageView rockImage, paperImage, sicissorsImage, playerDesicionView, aiDescisionImage, compImage, resultImage;
    @FXML
    public Label versusLabel, playerNameLabel, aiNamelLabel, resultLabelAI, resultLabelPlayer;
    public Label resultLabel;
    public MFXButton restartBtn;
    private int scoreAI = 0;
    private int scorePlayer = 0;



    private String decision;

    GameViewModel model = new GameViewModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setImages();

    }

    private void setImages(){
        rockImage.setImage(new javafx.scene.image.Image("/icons/rock-icon.png"));
        paperImage.setImage(new javafx.scene.image.Image("/icons/paper-icon.png"));
        sicissorsImage.setImage(new javafx.scene.image.Image("/icons/sicossors-icon.png"));
        compImage.setImage(new Image("/icons/AI-starting-icon.png"));
    }


    public void rockHandle(MouseEvent mouseEvent) {
        decision = "Rock";
        model.getWinner(decision);
        playerDesicionView.setImage(new Image("/icons/rock-icon.png"));
        resultLabel.setVisible(true);
        resultLabel.setText(model.getWinner(decision));
        changeToLastView();
    }

    public void handlePaper(MouseEvent mouseEvent) {
        decision = "Paper";
        model.getWinner(decision);
        playerDesicionView.setImage(new Image("/icons/paper-icon.png"));
        resultLabel.setVisible(true);
        resultLabel.setText(model.getWinner(decision));
        changeToLastView();

    }

    public void handleScissors(MouseEvent mouseEvent) {
        decision = "Scissor";
        model.getWinner(decision);
        playerDesicionView.setImage(new Image("/icons/sicossors-icon.png"));
        resultLabel.setVisible(true);
        resultLabel.setText(model.getWinner(decision));
        changeToLastView();
    }

    public void changeToLastView() {
        if (decision != null) {
            if (model.getWinner(decision).contains("Human Win")) {
                scorePlayer++;
                resultLabelPlayer.setText("" + scorePlayer);
                playerDesicionView.setVisible(false);
                versusLabel.setVisible(false);
                resultImage.setVisible(true);
                resultImage.setImage(new Image("/icons/win-icon.png"));
            } else if (model.getWinner(decision).contains("Tie")) {
                playerDesicionView.setVisible(false);
                versusLabel.setVisible(false);
                resultImage.setVisible(true);
                resultImage.setImage(new Image("/icons/draw-icon.png"));
            } else if (model.getWinner(decision).contains("AI")){
                scoreAI++;
                resultLabelAI.setText("" + scoreAI);
                playerDesicionView.setVisible(false);
                versusLabel.setVisible(false);
                resultImage.setVisible(true);
                resultImage.setImage(new Image("/icons/lose-icon.png"));
            }
        }
    }
    public void clearScreen(){
        resultImage.setVisible(false);
        resultLabel.setVisible(false);
        versusLabel.setVisible(true);
        scorePlayer = 0;
        resultLabelPlayer.setText("" + scorePlayer);
        scoreAI = 0;
        resultLabelAI.setText("" + scoreAI);
    }

    public void handleRestartBtn(ActionEvent actionEvent) {
        clearScreen();
    }
}
