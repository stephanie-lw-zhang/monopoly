package BackEnd.Player;

public abstract class AbstractPlayer {
    private String icon;
    private double money;
    private String[] properties;
    private boolean isInJail;


    public String getIcon() {
        return icon;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double newValue) {
        money = newValue;
    }

    public String[] getProperties() {
        return properties;
    }

    public boolean getIsInJail() {
        return isInJail;
    }

    public void setIsInJail(boolean jail) {
        isInJail = jail;
    }

}
