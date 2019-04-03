package BackEnd.Player;

public abstract class AbstractPlayer {
    private String icon;
    private int money;
    private String[] properties;


    public String getIcon() {
        return icon;
    }

    public int getMoney() {
        return money;
    }

    public String[] getProperties() {
        return properties;
    }

}
