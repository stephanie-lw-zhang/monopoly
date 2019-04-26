package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;
import backend.tile.Tile;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class MoveAndPayCard extends ActionCard{

    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private Tile tile;
    private double amount;

    public MoveAndPayCard(Tile tile, List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, double amount, String type) {
        super(type);
        this.tile = tile;
        this.payers =  payers;
        this.payees = payees;
        this.amount = amount;
    }

    public MoveAndPayCard(Element n){
        super("");
        this.setType(getTagValue("Type", n));
    }

    public void setTile(Tile t){
        tile = t;
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile);
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( amount );
        return parameters;
    }
}
