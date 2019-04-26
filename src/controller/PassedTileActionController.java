package controller;

import backend.assetholder.Bank;
import backend.board.AbstractBoard;
import frontend.views.LogView;
import frontend.views.board.AbstractBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.player_stats.PlayerFundsView;
import frontend.views.player_stats.PlayerPropertiesView;

public class PassedTileActionController {

    AbstractBoard myBoard;
    Turn myTurn;
    PlayerFundsView fundsView;
    PlayerPropertiesView propertiesView;
    AbstractBoardView boardView;
    AbstractGameView myGameView;
    LogView myLogView;
    Bank myBank;

    public PassedTileActionController(AbstractBoard board, Turn turn, AbstractGameView gameView, PlayerFundsView fundsView, PlayerPropertiesView propertiesView, LogView logView) {
        this.myBoard = board;
        this.myTurn = turn;
        this.myGameView = gameView;
        this.fundsView = fundsView;
        this.propertiesView = propertiesView;
    }

    public void handleCollectMoneyPassed() {
        myBank.payFullAmountTo( myTurn.getMyCurrPlayer(), myBoard.getGoTile().getPassedMoney() );
        myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getPassedMoney() + " for passing go." );
    }

}
