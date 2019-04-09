package BackEnd.Game;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;

public class Turn {

    private AbstractPlayer player;
    private int[] rolls;
    private AbstractBoard board;


    public Turn(AbstractPlayer player, int[] rolls, AbstractBoard board){
        this.player = player;
        this.rolls = rolls;
        this.board = board;
    }

    public void completeTurn(){
        if(player.isBankrupt()){
            return;
        }
        else if (player.getTurnsInJail() == 3) {
            //player must either pay 50 and move or skip one turn
            //series of states OR dialogue boxes
            //try to get out of jail() {
            //prompt user
            //get boolean
            //}
            //button.setOnAction -> eventHandler
            //states: waiting for user to answer question
            //controller calls a method in the UI to present a choice
            //model initiates question of roll or pay
            //model tells controller
            //controller tells view to show the choice
            //show popup or modal dialogue box
            //button prompts event handler
        }
        //assuming player chose to either pay $50 or skip one turn
        //else if(player.getTurnsInJail()!=-1 && ){}

        //assuming player chose to roll in jail
        else if(player.getTurnsInJail()!=-1 && rolls[0] != rolls[1]){
            return;
        }
        //player is not in jail and moves normally
        else{
            board.movePlayer(player, rolls);
            board.getPlayerTile(player).applyLandedOnAction(player);
        }
    }

    //in a turn a player can roll/move, trade, mortgage

    public boolean rolledDoubles(){
        return rolls[0] == rolls[1];
    }

}
