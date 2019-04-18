package controller;

public enum Actions {
    MOVE("move"),
    TRADE("trade"),
    END_TURN("turnEnd"),
    PAY_BAIL("bailOut"),
    BUY ("buy"),
    AUCTION("auction"),
    PAY_RENT("payRent"),
    PAY_TAX_FIXED("payTaxFixed"),
    PAY_TAX_PERCENTAGE("payTaxPercentage"),
    DRAW_CARD("drawCard"),
    SELL_TO_BANK("sellToBank"),
    SELL_TO_PLAYER("sellToPlayer"),
    COLLECT_MONEY("collectMoney"),
    GO_TO_JAIL("goToJail");

    private final String action;

    Actions(String action) {
        this.action = action;
    }

    String getActionString() {
        return action;
    }
}
