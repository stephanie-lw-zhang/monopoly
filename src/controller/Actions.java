package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.AbstractDrawCardTile;
import backend.tile.AbstractPropertyTile;
import backend.tile.JailTile;
import backend.tile.Tile;

import java.util.List;

public enum Actions {
//    MOVE("move") {
//        void performAction(AbstractPlayer player, AbstractBoard board, tile tile, int numMoves) {
//            //TODO: add action
//        }
//    },
    TRADE("trade") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            //TODO: add action
        }
    },
//    END_TURN("turnEnd") {
//        void performAction(AbstractPlayer player, AbstractBoard board, tile tile, int numMoves) {
//            //TODO: add action
//        }
//    },
    PAY_BAIL("bailOut") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            //TODO: add action
        }
    },
    BUY ("buy") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            AbstractPropertyTile property;
            property = (AbstractPropertyTile) tile;
            List<AbstractPropertyTile> sameSetProperties = board.getColorListMap().get( property.getCard().getCategory());
            Double currTilePrice = property.getCard().getTilePrice();
            property.sellTo( player, currTilePrice, sameSetProperties );
        }
    },
    AUCTION("auction") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            //TODO: add action
        }
    },
    PAY_RENT("payRent") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            AbstractPropertyTile property;
            property = (AbstractPropertyTile) tile;
            player.paysTo( property.getOwner(), property.calculateRentPrice( numMoves ) );
        }
    },
    PAY_TAX_FIXED("payTaxFixed") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            player.paysTo( board.getBank(), 200.0 );
        }
    },
    PAY_TAX_PERCENTAGE("payTaxPercentage") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            player.paysTo( board.getBank(),player.getMoney() * 0.1 );
        }
    },
    DRAW_CARD("drawCard") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            ((AbstractDrawCardTile) tile).drawCard();
        }
    },
    SELL_TO_BANK("sellToBank") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            //TODO: add action
        }
    },
    SELL_TO_PLAYER("sellToPlayer") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            //TODO: add action
        }
    },
    COLLECT_MONEY("collectMoney") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            //TODO: add action
        }
    },
    GO_TO_JAIL("goToJail") {
        void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves) {
            JailTile jail = (JailTile) board.getJailTile();
            board.getPlayerTileMap().put( player, jail);
            jail.addCriminal( player );
            player.addTurnInJail();
        }
    };

    private final String action;

    Actions(String action) {
        this.action = action;
    }

    String getActionString() {
        return action;
    }

    abstract void performAction(AbstractPlayer player, AbstractBoard board, Tile tile, int numMoves);
}
