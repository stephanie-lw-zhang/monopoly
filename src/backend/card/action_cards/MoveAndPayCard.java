package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;
import backend.tile.Tile;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class MoveAndPayCard extends ActionCard{

    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private String payerString;
    private String payeeString;
    private Tile tile;
    //private int index;
    private double multiplier;

    public MoveAndPayCard(Tile tile, List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, double multiplier, String type, String text) {
        super(type,text);
        this.tile = tile;
        this.payers =  payers;
        this.payees = payees;
        this.multiplier = multiplier;
    }

    public MoveAndPayCard(Element n){
        super("", "");
        payerString = getTagValue("Payer", n);
        payeeString = getTagValue("Payee", n);
        multiplier = Integer.parseInt(getTagValue("Multiplier", n));
        //index = Integer.parseInt(getTagValue("TileIndex", n));
        setType(getTagValue("Type", n));
        setText(getTagValue("Message", n));
    }

    public void setTile(Tile t){
        tile = t;
    }

//    public int getIndex(){
//        return index;
//    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile);
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( multiplier );
        return parameters;
    }

    public String getPayerString(){
        return payerString;
    }

    public String getPayeeString(){
        return payeeString;
    }


}
