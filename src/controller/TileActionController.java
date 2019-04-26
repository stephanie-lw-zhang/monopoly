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
import frontend.views.player_stats.PlayerFundsView;
import frontend.views.player_stats.PlayerPropertiesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileActionController {
    private AbstractBoard myBoard;
    private Turn myTurn;
    private PlayerFundsView fundsView;
    private PlayerPropertiesView propertiesView;
    private AbstractBoardView boardView;
    private AbstractGameView myGameView;
    private LogView myLogView;
    private Bank myBank;

    public TileActionController(AbstractBoard board, Turn turn, AbstractGameView gameView, PlayerFundsView fundsView, PlayerPropertiesView propertiesView, LogView logView) {
       this.myBoard = board;
       this.myTurn = turn;
       this.myGameView = gameView;
       this.fundsView = fundsView;
       this.propertiesView = propertiesView;
    }

    public void handleStayInJail() {
        myTurn.getMyCurrPlayer().addTurnInJail();
    }

    public void handleCollectMoneyLanded() {
        myBank.payFullAmountTo( myTurn.getMyCurrPlayer(), myBoard.getGoTile().getLandedOnMoney() );
        myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getLandedOnMoney() +" for landing on go." );
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
            ActionCardController actionCardController = new ActionCardController(myBoard, myTurn, fundsView, myGameView);
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

    public void handleAuction() {
        Map<AbstractPlayer,Double> auctionAmount = new HashMap<>();
        for (int i = 0; i < myBoard.getMyPlayerList().size(); i++) {
            AbstractPlayer key = getPlayerAtIndex(i);
            String value = myGameView.showInputTextDialog("Auction Amount for player " + getPlayerNameAtIndex(i),
                    "Enter your auction amount:",
                    "Amount:");
            try {
                auctionAmount.put(key, Double.parseDouble((value)));
            } catch (NumberFormatException n) {
                new IllegalInputTypeException("Input must be a number!");
                i--;
            }
        }
        AbstractPropertyTile property = (AbstractPropertyTile) myTurn.currPlayerTile();
        Map.Entry<AbstractPlayer, Double> winner = property.determineAuctionResults(auctionAmount);
        String info = winner.getKey().getMyPlayerName() + " wins " + myTurn.getTileNameforPlayer(myTurn.getMyCurrPlayer()) + " for " + winner.getValue() + " Monopoly Dollars!";
        myGameView.displayActionInfo(info);
        myLogView.gameLog.setText(info);
        Map<AbstractPlayer, Double> playerValue = convertEntrytoMap(winner);
        try {
            buyHelper(playerValue);
        } catch (IllegalActionOnImprovedPropertyException e) {
            e.popUp();
        } catch (IllegalInputTypeException e) {
            e.popUp();
        } catch (OutOfBuildingStructureException e) {
            e.popUp();
        }
        fundsView.update(myBoard.getMyPlayerList());
        propertiesView.update(myBoard.getMyPlayerList());
    }


    public void handlePayTaxPercentage() {
        double tax = myTurn.getMyCurrPlayer().getMoney() * ((IncomeTaxTile) myTurn.currPlayerTile()).getTaxMultiplier();
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(),tax);
        myLogView.gameLog.setText( myTurn.getMyCurrPlayer().getMyPlayerName() + " payed " + tax + " in taxes.");
    }

    public void handleBuy(){
        try {
            Map.Entry<AbstractPlayer, Double> playerValue = buyHelper(null);
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

    private Map.Entry<AbstractPlayer, Double> buyHelper(Map<AbstractPlayer,Double> paramMap) throws IllegalActionOnImprovedPropertyException, IllegalInputTypeException, OutOfBuildingStructureException {
        AbstractPlayer player = null;
        double value = 0;
        if (paramMap != null) {
            if (paramMap.keySet().size()==1) {
                for (AbstractPlayer p : paramMap.keySet()) {
                    player = p;
                }
            }
            value = paramMap.get(player);
        }
        else {
            player = myTurn.getMyCurrPlayer();
            value = ((AbstractPropertyTile)myTurn.currPlayerTile()).getTilePrice();
        }
        buyProperty(player, value);
        Map.Entry<AbstractPlayer,Double> ret = new AbstractMap.SimpleEntry<>(player, value);
        return ret;
    }

    private void buyProperty(AbstractPlayer player, Double value) throws IllegalActionOnImprovedPropertyException, IllegalInputTypeException, OutOfBuildingStructureException {
        AbstractPropertyTile property;
        property = (AbstractPropertyTile) myTurn.currPlayerTile();
        List<AbstractPropertyTile> sameSetProperties = myBoard.getColorListMap().get( property.getCard().getCategory());
        property.sellTo( player, value, sameSetProperties );
    }

    private ObservableList<String> getAllPlayerNames() {
        ObservableList<String> players = FXCollections.observableArrayList();
        for (AbstractPlayer p : myBoard.getMyPlayerList()) {
            players.add( p.getMyPlayerName() );
        }
        return players;
    }

    private AbstractPlayer getPlayerAtIndex(int i) {
        return myBoard.getMyPlayerList().get(i);
    }

    private Map<AbstractPlayer, Double> convertEntrytoMap(Map.Entry<AbstractPlayer, Double> param) {
        Map<AbstractPlayer, Double> mapFromSet = new HashMap<>();
        mapFromSet.put(param.getKey(), param.getValue());
        return mapFromSet;
    }

    private String getPlayerNameAtIndex(int i) {
        return getPlayerAtIndex(i).getMyPlayerName();
    }
}
