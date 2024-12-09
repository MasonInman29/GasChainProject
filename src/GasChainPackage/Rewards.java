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
     * @param pts
     * @return
     */
    public int useRewards(double salesTotal, int rewardsToUse, int pts){
        if(rewardsToUse > pts || rewardsToUse > salesTotal ){return 0;}
        points -= rewardsToUse;
        return (rewardsToUse);
    }

    /**
     * every $10 = 1 point
     * @param salesTotal sales total (after rewards applied)
     */
    public void addRewards(double salesTotal){
        if(salesTotal < 10){
            return;
        }
        else{
            System.out.println("pre pts: " + points);
            points += (int)Math.floor(salesTotal / 10);
            System.out.println("post pts: " + points + " sale total " + salesTotal + "\n");
        }
    }

}
