package gameView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** This class is used to load assets and retrieve them easily */
public class AssetManager {
    private static AssetManager instance = null;
    private Map<String, BufferedImage> assets;

    private AssetManager(){
        this.assets = new HashMap<>();
    }

    public static AssetManager getInstance(){
        if(instance == null){
            instance = new AssetManager();
        }
        return instance;
    }

    public void loadAssets(){
        // We load all assets in a hashmap to retrieve them easily
        BufferedImage img = null;
        loadButtons(img);
        loadEnemiesSprites(img);
        loadTowersSprites(img);
        loadProjectilesSprites(img);
        loadDragButtonAssets(img);
        loadHealthIcon(img);
    }

    private void loadHealthIcon(BufferedImage img) {
        try {
            img = ImageIO.read(new File("assets/sprites/ui/healthIcon.png"));
            assets.put("healthIcon", img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProjectilesSprites(BufferedImage img) {
        try {
            img = ImageIO.read(new File("assets/sprites/projectile.png"));
            assets.put("projectile", img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTowersSprites(BufferedImage img) {
        try {
            img = ImageIO.read(new File("assets/sprites/towers/attack.png"));
            assets.put("attackTower", img);
            img = ImageIO.read(new File("assets/sprites/towers/defensive.png"));
            assets.put("defensiveTower", img);
            img = ImageIO.read(new File("assets/sprites/towers/money.png"));
            assets.put("moneyTower", img);
            img = ImageIO.read(new File("assets/sprites/towers/multi.png"));
            assets.put("multiTower", img);
            img = ImageIO.read(new File("assets/sprites/towers/global.png"));
            assets.put("globalTower", img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEnemiesSprites(BufferedImage img) {
        try {
            img = ImageIO.read(new File("assets/sprites/enemies/weak.png"));
            assets.put("weakEnemy", img);
            img = ImageIO.read(new File("assets/sprites/enemies/tank.png"));
            assets.put("tankEnemy", img);
            img = ImageIO.read(new File("assets/sprites/enemies/fast.png"));
            assets.put("fastEnemy", img);
            img = ImageIO.read(new File("assets/sprites/enemies/polyvalent.png"));
            assets.put("polyvalentEnemy", img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Load all buttons UIs */
    private void loadButtons(BufferedImage img) {
        try {
            img = ImageIO.read(new File("assets/sprites/ui/fastenButton1.png"));
            assets.put("fastenButton1", img);
            img = ImageIO.read(new File("assets/sprites/ui/fastenButton2.png"));
            assets.put("fastenButton2", img);
            img = ImageIO.read(new File("assets/sprites/ui/fastenButton3.png"));
            assets.put("fastenButton3", img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDragButtonAssets(BufferedImage img) {
        try {
            img = ImageIO.read(new File("assets/sprites/tower_DragButton_background.png"));
            assets.put("tower_DragButton_background", img);
            img = ImageIO.read(new File("assets/sprites/towers/icons/attackTowerIcon.png"));
            assets.put("attackTowerIcon", img);
            img = ImageIO.read(new File("assets/sprites/towers/icons/defensiveTowerIcon.png"));
            assets.put("defensiveTowerIcon", img);
            img = ImageIO.read(new File("assets/sprites/towers/icons/moneyTowerIcon.png"));
            assets.put("moneyTowerIcon", img);
            img = ImageIO.read(new File("assets/sprites/towers/icons/multiTowerIcon.png"));
            assets.put("multiTowerIcon", img);
            img = ImageIO.read(new File("assets/sprites/towers/icons/globalTowerIcon.png"));
            assets.put("globalTowerIcon", img);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(String assetName) {
        return assets.get(assetName);
    }
}
