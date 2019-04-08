package BackEnd.Controller;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import BackEnd.Dice.AbstractDice;

public class Turn {

    private AbstractPlayer player;
    private int[] rolls;
    private AbstractBoard board;
    private AbstractDice dice;


    public Turn(AbstractPlayer player, int[] rolls, AbstractBoard board, AbstractDice dice){
        this.player = player;
        this.rolls = rolls;
        this.board = board;
        this.dice = dice;
    }

    public void movePlayer(){
        if (player.getTurnsInJail() == 3) {
            //player must either pay 50 and move or skip one turn
        }
        else if(player.getTurnsInJail()!=-1 && rolls[0] != rolls[1]){
            return;
        }
        else{
            board.movePlayer(player, rolls);
            board.getPlayerTile(player).applyLandedOnAction(player);
        }
    }

}
