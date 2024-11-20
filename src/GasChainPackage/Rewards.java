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

//    /**
//     * to be implemented later
//     */
//    public boolean useRewards(int pts){
//        if(pts > points){return false;}
//        points -= pts;
//        return true;
//    }

    /**
     * next iteration - Lindsey
     * @param salesTotal
     * @param rewardsToUse
     * @param points
     * @return
     */
    public int useRewards(double salesTotal, int rewardsToUse, int pts){
        if(use > points){return 0;}
        points -= use;
        return pts;
    }
}
