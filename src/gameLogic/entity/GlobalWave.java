package gameLogic.entity;

/** The global tower wave, its attack */
public class GlobalWave {
    private Tower globalTower;
    private int radius = 0;

    public GlobalWave(Tower globalTower){
        this.globalTower = globalTower;
    }
    public Tower getTower(){
        return globalTower;
    }

    public void setRadius(int x){
        this.radius = x;
    }

    public int getRadius() {
        return radius;
    }
}
