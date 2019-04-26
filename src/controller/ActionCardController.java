package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.card.property_cards.BuildingCard;
import backend.tile.AbstractPropertyTile;
import backend.tile.BuildingTile;
import backend.tile.IncomeTaxTile;
import backend.tile.Tile;
import exceptions.BuildingDoesNotExistException;
import exceptions.TileNotFoundException;
import frontend.views.board.AbstractBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.player_stats.PlayerFundsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        double amount = (Double) parameters.get( 2 );
        payHelper( payers, payees, amount );
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

    public void handleMoveAndPay(List<Object> parameters){
        Tile targetTile = (Tile) parameters.get( 0 );
        List<AbstractPlayer> payers = (List<AbstractPlayer>) parameters.get( 1 );
        List<AbstractPlayer> payees = (List<AbstractPlayer>) parameters.get( 2 );
        double amount = (Double) parameters.get( 3 );
        try {
            board.movePlayerToNearest( turn.getMyCurrPlayer(), targetTile );
            payHelper( payers, payees, amount );
        } catch (TileNotFoundException e) {
            e.popUp();
        }
    }

    public void handlePayBuildings(List<Object> parameters) throws BuildingDoesNotExistException {
        List<AbstractPlayer> payers = (List<AbstractPlayer>) parameters.get( 0 );
        List<AbstractPlayer> payees = (List<AbstractPlayer>) parameters.get( 1 );
        Map<String, Double> baseToMultiplier = (Map<String, Double>) parameters.get( 2 );
        double total = 0;
        Map<String, Integer> totalBuildings = new HashMap<>();
        for(AbstractPropertyTile property: turn.getMyCurrPlayer().getProperties()){
            if(property.isBuildingTile()){
                BuildingCard card = ((BuildingCard) property.getCard());
                String base = card.getBasePropertyType(property.getCurrentInUpgradeOrder());
                if(baseToMultiplier.containsKey( base )){
                    int number = card.getNumericValueOfPropertyType( base );
                    total += baseToMultiplier.get( base ) * number;
                } else{
                    throw new BuildingDoesNotExistException( "Building does not exist" );
                }
            }
        }
        payHelper( payers, payees, total );
    }

    private void payHelper(List<AbstractPlayer> payers, List<AbstractPlayer> payees, Double amount) {
        for (AbstractPlayer payer : payers) {
            for (AbstractPlayer payee : payees) {
                payer.payFullAmountTo( payee, amount );
            }
        }
        fundsView.updatePlayerFundsDisplay( board.getMyPlayerList() );
    }


}
