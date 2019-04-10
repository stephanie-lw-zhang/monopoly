package BackEnd.AssetHolder;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(Double money, Bank bank) {
        super( money, bank );
    }

    // DEFAULT CONSTRUCTOR FOR STANDARD GAME PLAYER
    public HumanPlayer(Bank bank) {
        this(1500.00, bank);
    }


    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {

    }
}
