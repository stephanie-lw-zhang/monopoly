package backend.assetholder;

public class AutomatedPlayer extends AbstractPlayer {
    public AutomatedPlayer(Double money, Bank bank) {
        super("CPU", money, bank );
    }

    @Override
    public void paysFullAmountTo(AbstractAssetHolder receiver, Double debt) {

    }
}
