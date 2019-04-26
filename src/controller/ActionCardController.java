package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.Tile;
import exceptions.TileNotFoundException;
import frontend.views.board.AbstractBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.player_stats.PlayerFundsView;

import java.util.List;

public class ActionCardController{

    AbstractBoard board;
    Turn turn;
    PlayerFundsView fundsView;
    AbstractBoardView boardView;
    AbstractGameView myGameView;

    public ActionCardController(AbstractBoard board, Turn turn, PlayerFundsView fundsView, AbstractBoardView boardView, AbstractGameView myGameView) {
        this.board = board;
        this.turn = turn;
        this.fundsView = fundsView;
        this.boardView = boardView;
        this.myGameView = myGameView;
    }

    public void handlePay(List<Object> parameters){
        List<AbstractPlayer> payers = (List<AbstractPlayer>) parameters.get( 0 );
        List<AbstractPlayer> payees = (List<AbstractPlayer>) parameters.get( 1 );
        Double amount = (Double) parameters.get( 2 );
        for(AbstractPlayer payer: payers){
            for (AbstractPlayer payee: payees){
                payer.payFullAmountTo( payee, amount );
            }
        }
        fundsView.updatePlayerFundsDisplay( board.getMyPlayerList() );
    }

    public void handleMove(List<Object> parameters){
        board.movePlayer( turn.getMyCurrPlayer(), (Tile) parameters.get( 0 ) );
        boardView.move(turn.getMyCurrPlayer().getMyIcon(), (Tile) parameters.get( 0 ));

    }

    public void handleGetOutOfJail(List<Object> parameters){
        try {
            board.getJailTile().removeCriminal(turn.getMyCurrPlayer());
            myGameView.displayActionInfo( "You've used your handle get out of jail card. You're free now!" );
        } catch (TileNotFoundException e) {
            e.popUp();
        }
    }
}
