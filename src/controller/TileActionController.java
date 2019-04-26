package controller;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.board.AbstractBoard;
import backend.card.action_cards.ActionCard;
import backend.tile.*;
import exceptions.*;
import frontend.views.LogView;
import frontend.views.board.AbstractBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.player_stats.PlayerCardsView;
import frontend.views.player_stats.PlayerFundsView;
import frontend.views.player_stats.PlayerPropertiesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class TileActionController {
    AbstractBoard myBoard;
    Turn myTurn;
    PlayerFundsView fundsView;
    PlayerPropertiesView propertiesView;
    AbstractBoardView boardView;
    AbstractGameView myGameView;
    LogView myLogView;
    Bank myBank;
    AbstractBoardView myBoardView;

    public TileActionController(AbstractBoard board, Turn turn, AbstractGameView gameView, PlayerFundsView fundsView, PlayerPropertiesView propertiesView, LogView logView, AbstractBoardView boardView) {
       this.myBoard = board;
       this.myTurn = turn;
       this.myGameView = gameView;
       this.fundsView = fundsView;
       this.propertiesView = propertiesView;
       this.myBoardView = boardView;
    }

    public void handleStayInJail() {
        myTurn.getMyCurrPlayer().addTurnInJail();
    }

    public void handleCollectMoneyLanded() {
        //means you landed directly on it
        myBank.payFullAmountTo( myTurn.getMyCurrPlayer(), myBoard.getGoTile().getLandedOnMoney() );
        myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getLandedOnMoney() +" for landing on go." );
    }

    public void handleCollectMoneyPassed() {
        //USE REFLECTION  CollectMoney + "landed" OR "passed"
        myBank.payFullAmountTo( myTurn.getMyCurrPlayer(), myBoard.getGoTile().getPassedMoney() );
        myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getPassedMoney() + " for passing go." );
    }

    public void handleGoToJail() {
        try {
            JailTile jail = (JailTile) myBoard.getJailTile();
            myBoard.getPlayerTileMap().put( myTurn.getMyCurrPlayer(), jail);
            myTurn.getMyCurrPlayer().addTurnInJail();
            myGameView.displayActionInfo( "Arrested! You're going to Jail." );
            myLogView.gameLog.setText(myTurn.getMyCurrPlayer().getMyPlayerName() + " has been sent to Jail!");
        } catch (TileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void handlePayBail(){
        try {
            myTurn.getMyCurrPlayer().payFullAmountTo(myBoard.getBank(), myBoard.getJailTile().getBailAmount());
            myGameView.displayActionInfo("You've successfully paid the fine. You're free now!");
            fundsView.update(myBoard.getMyPlayerList());
            myLogView.gameLog.setText(myTurn.getMyCurrPlayer().getMyPlayerName() + " has paid the fine and can move!");

        } catch(TileNotFoundException e) {
            e.popUp();
        }
    }

        public void handlePayRent() {
        AbstractPropertyTile property = (AbstractPropertyTile) myBoard.getPlayerTile( myTurn.getMyCurrPlayer());
        myTurn.getMyCurrPlayer().payFullAmountTo(property.getOwner(), property.calculateRentPrice( myTurn.getNumMoves()));
        fundsView.update(myBoard.getMyPlayerList());
        myGameView.displayActionInfo( "You paid " + property.calculateRentPrice( myTurn.getNumMoves()) + " to " + ( (AbstractPlayer) property.getOwner()).getMyPlayerName() + ".");
        myLogView.gameLog.setText(myTurn.getMyCurrPlayer().getMyPlayerName() + " has paid " + property.calculateRentPrice( myTurn.getNumMoves()) + " of rent to " + ( (AbstractPlayer) property.getOwner()).getMyPlayerName() + ".");

    }

    public void handlePayTaxFixed() {
        double tax = ((AbstractTaxTile)myTurn.currPlayerTile()).getAmountToDeduct();
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(), tax);
        fundsView.update(myBoard.getMyPlayerList());
        myGameView.displayActionInfo( "It's tax season! You've paid " + tax + " in taxes.");
        myLogView.gameLog.setText( myTurn.getMyCurrPlayer().getMyPlayerName() + " payed " + tax + " in taxes.");
    }


    public void handleDrawCard(){
        try {
            ActionCard actionCard = ((AbstractDrawCardTile) myBoard.getPlayerTile(myTurn.getMyCurrPlayer())).drawCard();
            myGameView.displayActionInfo( actionCard.getText() );
            ActionCardController actionCardController = new ActionCardController(myBoard, myTurn, fundsView, myBoardView, myGameView);
            Method handle = actionCardController.getClass().getMethod("handle" + actionCard.getActionType(), List.class);
            handle.invoke(actionCardController, actionCard.getParameters());
        } catch (NoSuchMethodException e) {
            myGameView.displayActionInfo( "There is no such method" );
        } catch (SecurityException e) {
            myGameView.displayActionInfo( "Security exception" );
        } catch (IllegalAccessException e) {
            myGameView.displayActionInfo( "Illegal access exception" );
        } catch (IllegalArgumentException e) {
            myGameView.displayActionInfo( "Illegal argument exception" );
        } catch (InvocationTargetException e) {
            myGameView.displayActionInfo( "Invocation target exception" );
        }
    }



    public void handlePayTaxPercentage() {
        double tax = myTurn.getMyCurrPlayer().getMoney() * ((IncomeTaxTile) myTurn.currPlayerTile()).getTaxMultiplier();
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(),tax);
        myLogView.gameLog.setText( myTurn.getMyCurrPlayer().getMyPlayerName() + " payed " + tax + " in taxes.");
    }

    public void handleBuy(){
        try {
            Map.Entry<AbstractPlayer, Double> playerValue = this.myTurn.buy(null);
            String info = playerValue.getKey().getMyPlayerName() + " bought " + this.myTurn.getTileNameforPlayer(playerValue.getKey()) + " for " + playerValue.getValue() + " Monopoly Dollars!";
            myGameView.displayActionInfo(info);
        } catch (IllegalActionOnImprovedPropertyException e) {
            e.popUp();
        } catch (IllegalInputTypeException e) {
            e.popUp();
        } catch (OutOfBuildingStructureException e) {
            e.popUp();
        }
    }

    private ObservableList<String> getAllPlayerNames() {
        ObservableList<String> players = FXCollections.observableArrayList();
        for (AbstractPlayer p : myBoard.getMyPlayerList()) {
            players.add( p.getMyPlayerName() );
        }
        return players;
    }

}
