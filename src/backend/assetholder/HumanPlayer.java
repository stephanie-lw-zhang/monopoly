package backend.assetholder;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(String name, Double money, Bank bank) {
        super( name, money, bank );
    }

    // DEFAULT CONSTRUCTOR FOR STANDARD GAME PLAYER
    public HumanPlayer(Bank bank) {
        this("GiddyGiraffe", 1500.00, bank);
    }

}
