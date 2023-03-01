package rps.gui.model;

import rps.bll.game.*;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;


public class GameViewModel {
    private String output;
    private String getTypeOfResult;
    private String compMove;

    private int roundNumber = 0;

    public String getWinner(String decision){
        IPlayer human = new Player("Human", PlayerType.Human);
        IPlayer bot = new Player("AI", PlayerType.AI);
        roundNumber++;
        if (decision.equals("Rock")){
            GameManager gm = new GameManager(human, bot);
            gm.playRound(Move.valueOf(decision));
            compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
            gm.getGameState().getHistoricResults().forEach((result) -> {
            output = getResultAsString(result);}
            );

        } else if (decision.equals("Paper")) {
            instManager(decision, human, bot);
        }else {
            instManager(decision, human, bot);
        }
        return output;
    }

    public int getRoundNumber(){
        return this.roundNumber;
    }

    private void instManager(String decision, IPlayer human, IPlayer bot) {
        GameManager gm = new GameManager(human, bot);
        gm.playRound(Move.valueOf(decision));
        compMove = String.valueOf(gm.getAIMove(Move.valueOf(decision)));
        gm.getGameState().getHistoricResults().forEach(this::getResultAsString);
        gm.getGameState().getHistoricResults().forEach((result) -> {
            output = getResultAsString(result);}
        );
    }

    public String getResultAsString(Result result) {
            if (result.getType().toString().equals("Tie")){
                return result.getType().toString();
            }else {
                return result.getWinnerPlayer().getPlayerName() + " " + result.getType().toString();
            }
    }
    public String getCompImage(){
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
