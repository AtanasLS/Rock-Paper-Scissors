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
import java.util.HashMap;
import java.util.Map;


public class GameViewModel {
    private String output;
    private String getTypeOfResult;
    private String compMove;

    private GameManager gm;

    private  IPlayer human;

    private IPlayer bot;

    public String getOutput(){
        return this.output;
    }

    public GameViewModel() {
        human = new Player("Human", PlayerType.Human);
        bot = new Player("AI", PlayerType.AI);
        this.gm = new GameManager(human, bot);
    }

    public Map<String, Integer> getWinner(String decision){
        Map<String, Integer> result = new HashMap<>();
        int roundNumber = 0;
        System.out.println(decision);
        if (decision.equals("Rock")){
            output=getResultAsString(gm.playRound(Move.valueOf(decision)));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            roundNumber = gm.getGameState().getRoundNumber();
            System.out.println(compMove);
            bot.update("rock");
        } else if (decision.equals("Paper")) {
            output=getResultAsString(gm.playRound(Move.valueOf(decision)));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            System.out.println(compMove);
            roundNumber = gm.getGameState().getRoundNumber();
            bot.update("paper");
        }else {
            output=getResultAsString(gm.playRound(Move.valueOf(decision)));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            roundNumber = gm.getGameState().getRoundNumber();
            System.out.println(compMove);
            bot.update("scissors");
        }
        System.out.println(output);
        result.put(output, roundNumber);
        return result;
    }
    public String getResultAsString(Result result) {
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
