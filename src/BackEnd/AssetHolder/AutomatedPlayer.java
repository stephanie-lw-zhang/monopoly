package BackEnd.AssetHolder;

public class AutomatedPlayer extends AbstractPlayer {
    public AutomatedPlayer(Double money, Bank bank) {
        super( money, bank );
    }

    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {

    }
}
