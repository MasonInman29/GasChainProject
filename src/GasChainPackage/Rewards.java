package GasChainPackage;

public class Rewards {
    private int points;

    public Rewards() {
        points = 0;
    }
    public Rewards(int p) {
        points = p;
    }

    /**
     * every $10 = 1 point
     * @param price sales total (after rewards applied)
     */
    public void addPoints(double price){
        points += price % 10;
    }

    /**
     * 1 point is $1
     * @return the $ value of points a customer has
     */
    public int getRewards(){
        return points;
    }
    /**
     * to be implemented later
     */
    public boolean useRewards(int use){
        if(use > points){return false;}
        points -= use;
        return true;
    }
}
