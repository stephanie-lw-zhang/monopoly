package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;
import backend.tile.Tile;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for action cards that require to move and then pay, extends ActionCard class
 * @author stephaniezhang
 */
public class MoveAndPayCard extends ActionCard{

    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private String payerString;
    private String payeeString;
    private Tile tile;
    private String targetTileType;
//    private int index;
    private double multiplier;

    /**
     * Constructor
     * @param tile -- unused, should've deleted
     * @param payers -- list of assetholders that pay
     * @param payees -- list of assetholders that receive money from each of the payers
     * @param multiplier -- multiplier for the amount needed to pay (such as when based on number of dice roll)
     * @param type -- action type as a string, to be called in a method, used in super
     * @param text -- message to be displayed on screen, used in super
     */
    public MoveAndPayCard(Tile tile, List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, double multiplier, String type, String text) {
        super(type,text);
//        this.tile = tile;
        this.payers =  payers;
        this.payees = payees;
        this.multiplier = multiplier;
    }

    /**
     * Constructor used for xml purposes
     * @param n
     */
    public MoveAndPayCard(Element n){
        super("", "");
        payerString = getTagValue("Payer", n);
        payeeString = getTagValue("Payee", n);
        multiplier = Integer.parseInt(getTagValue("Multiplier", n));
//        index = Integer.parseInt(getTagValue("TileIndex", n));
        setType(getTagValue("Type", n));
        targetTileType = getTagValue("TargetTileType",n);
        setText(getTagValue("Message", n));
    }

    /**
     * Set the tile it needs to move to
     * @param t
     */
    public void setTile(Tile t){
        tile = t;
    }

    /**
     *
     * @return the tile, payers, payees, and multiplier as a list
     */
    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(tile);
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( multiplier );
        return parameters;
    }

    /**
     *
     * @return payer as a string
     */
    public String getPayerString(){
        return payerString;
    }

    /**
     *
     * @return payee as a string
     */
    public String getPayeeString(){
        return payeeString;
    }

    /**
     * set the payers
     * @param p -- list of abstract holders, can include bank
     */
    public void setPayers(List<AbstractAssetHolder> p){
        payers = p;
    }

    /**
     * set payees
     * @param p
     */
    public void setPayees(List<AbstractAssetHolder> p){
        payees = p;
    }

    /**
     * set the target tile type, because sometimes it's not about going to a specific tile but any tile of the same type (ie railroad).
     * @return targetTileType
     */
    public String getTargetTileType() {
        return targetTileType;
    }

}
