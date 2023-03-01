package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;
import java.util.Random;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    private float[][] markovChain;
    private int[] timesPlayed;
    private int lastMove;
    private int moveBeforeLast;


    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
        this.markovChain=new float[][] {{0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}};//Didn't really see a need to do this via a loop when a simple assign statement would do
        this.timesPlayed=new int[] {0, 0, 0};
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        Random rand = new Random();
        float ranFloat = rand.nextFloat();
        if(ranFloat <= markovChain[lastMove][1]){
            return Move.Paper;
        }
        else if (ranFloat <= markovChain[lastMove][2] + markovChain[lastMove][1]){

            return Move.Scissor;
        }
        else{
            return Move.Rock;
        }

    }

    public void update(String newMove){//Takes in the results of the last game played, and uses them to update the Markov chain using relevant data
        moveBeforeLast = lastMove;
        if(newMove.equals("rock")){
            lastMove = 0;
        }
        else if(newMove.equals("paper")){
            lastMove = 1;
        }
        else{
            lastMove = 2;
        }

        //Here comes the hard part: updating the Markov Chain
        /*
         * 1. Multiply everything in the appropriate column of the Markov Chain by timesPlayed[moveBeforeLast]
         * 2. Increment the row value we want (that is, markovChain[moveBeforeLast][lastMove] by one
         * 3. Increment timesPlayed[moveBeforeLast] by one
         * 4. Divide all values in markovChain[moveBeforeLast][x] by timesPlayed[moveBeforeLast]
         */

        for(int i = 0; i < 3; i++){ //1. Multiply everything in the appropriate column of the Markov Chain by timesPlayed[moveBeforeLast]
            markovChain[moveBeforeLast][i] *= timesPlayed[moveBeforeLast];
        }

        //2. Increment the row value we want (that is, markovChain[moveBeforeLast][lastMove] by one
        markovChain[moveBeforeLast][lastMove] += 1;

        //3. Increment timesPlayed[moveBeforeLast] by one
        timesPlayed[moveBeforeLast]++;

        //4. Divide all values in markovChain[moveBeforeLast][x] by timesPlayed[moveBeforeLast]
        for(int j = 0; j < 3; j++){
            markovChain[moveBeforeLast][j] /= timesPlayed[moveBeforeLast];
        }

        //For debug purposes, let's go ahead and print the contents of this Markov Chain:
        System.out.println("New Markov Chain");
        System.out.println("Rock to Rock: " + markovChain[0][0] + " Rock to Paper: " + markovChain[0][1] + " Rock to Scissors: " + markovChain[0][2]);
        System.out.println("Paper to Rock: " + markovChain[1][0] + " Paper to Paper: " + markovChain[1][1] + " Paper to Scissors: " + markovChain[1][2]);
        System.out.println("Scissors to Rock: " + markovChain[2][0] + " Scissors to Paper: " + markovChain[2][1] + " Scissors to Scissors: " + markovChain[2][2]);


    }
    public String getAIMove(IGameState state){
        return doMove(state).name();
    }
}
