package Controller;

public enum Actions {
    MOVE("move") {
        void performAction() {

        }
    },
    TRADE("trade") {
        void performAction() {

        }
    },
    END_TURN("turnEnd") {
        void performAction() {

        }
    },
    PAY_BAIL("bailOut") {
        void performAction() {

        }
    },
    BUY ("buy") {
        void performAction() {

        }
    },
    AUCTION("auction") {
        void performAction() {

        }
    },
    PAY_RENT("payRent") {
        void performAction() {

        }
    },
    PAY_TAX_FIXED("payTaxFixed") {
        void performAction() {

        }
    },
    PAY_TAX_PERCENTAGE("payTaxPercentage") {
        void performAction() {

        }
    },
    DRAW_CARD("drawCard") {
        void performAction() {

        }
    },
    SELL_TO_BANK("sellToBank") {
        void performAction() {

        }
    },
    SELL_TO_PLAYER("sellToPlayer") {
        void performAction() {

        }
    },
    COLLECT_MONEY("collectMoney") {
        void performAction() {

        }
    },
    GO_TO_JAIL("goToJail") {
        void performAction() {

        }
    };

    private final String action;

    Actions(String action) {
        this.action = action;
    }

    String getActionString() {
        return action;
    }

    abstract void performAction();
}
