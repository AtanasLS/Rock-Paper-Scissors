package rps.gui.model;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;
import rps.gui.controller.GameViewController;



public class GameViewModel {

    private String output;
    private String getTypeOfResult;

    public String getWinner(String decision){

        IPlayer human = new Player("Human", PlayerType.Human);
        IPlayer bot = new Player("AI", PlayerType.AI);

        if (decision.equals("Rock")){
            GameManager gm = new GameManager(human, bot);
            gm.playRound(Move.valueOf(decision));
            gm.getGameState().getHistoricResults().forEach((result) -> {
            output = getResultAsString(result);}
            );

        } else if (decision.equals("Paper")) {
            GameManager gm = new GameManager(human, bot);
            gm.playRound(Move.valueOf(decision));
            gm.getGameState().getHistoricResults().forEach(this::getResultAsString);
            gm.getGameState().getHistoricResults().forEach((result) -> {
                output = getResultAsString(result);}
            );
        }else {
            GameManager gm = new GameManager(human, bot);
            gm.playRound(Move.valueOf(decision));
            gm.getGameState().getHistoricResults().forEach(this::getResultAsString);
            gm.getGameState().getHistoricResults().forEach((result) -> {
                output = getResultAsString(result);}
            );
        }
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




}
