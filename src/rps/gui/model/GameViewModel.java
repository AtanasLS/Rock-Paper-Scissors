package rps.gui.model;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rps.bll.game.*;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;
import rps.gui.controller.GameViewController;

import java.util.ArrayList;


public class GameViewModel {


    private String output;
    private String getTypeOfResult;
    public String compMove;

    public String getOutput(){
        return  this.output;
    }

    public String getWinner(String decision){
        IPlayer human = new Player("Human", PlayerType.Human);
        IPlayer bot = new Player("AI", PlayerType.AI);
        GameManager gm = new GameManager(human, bot);
        System.out.println( decision);
        if (decision.equals("Rock")){
            output=getResultAsString(gm.playRound(Move.valueOf(decision)));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            System.out.println(compMove);
            bot.update("rock");
        } else if (decision.equals("Paper")) {
            output=getResultAsString(gm.playRound(Move.valueOf(decision)));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            System.out.println(compMove);
            bot.update("paper");
        }else {
            output=getResultAsString(gm.playRound(Move.valueOf(decision)));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            System.out.println(compMove);
            bot.update("scissors");
        }
        System.out.println(output);
        return output;
    }
    public String getResultAsString(Result result) {
            //return result.getWinnerPlayer().getPlayerName();
            if (result.getType().toString().equals("Tie")){

                return result.getType().toString();
            }else {
                return result.getWinnerPlayer().getPlayerName() + " " + result.getType().toString();
            }
    }
    public String getCompImage (){
        String image = "";
        if (compMove!= null){
            if (compMove.equals("Rock")){
                image = "/icons/rock-icon.png";
            }else if (compMove.equals("Scissor")){
                image = "/icons/sicossors-icon.png";
            }else {
                image = "/icons/paper-icon.png";
            }
        }
        return image;
    }




}
