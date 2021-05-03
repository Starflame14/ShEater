import java.util.ArrayList;
import java.util.Random;

public class PowerUp extends Entity{
    int id;

    double posX;
    double posY;

    int r;

    int timeRemaining;

    PowerUpType type;

    PowerUp(PowerUpType type){
        this.type = type;

        id = Main.powerUpId;
        Main.powerUpId++;

        timeRemaining = 10000;
        r = 30;

        if(type == PowerUpType.EndLevel) {
            timeRemaining = 1000000;
            r = 80;
        }
        if(type == PowerUpType.BombsUp || type == PowerUpType.Sp){
            r = 16;
        }
        if(type == PowerUpType.Mine){
            r = 20;
            timeRemaining = 1900;
        }



        double[] pos = spawnPos();
        posX = pos[0];
        posY = pos[1];
    }

    void eat(){
        timeRemaining = 0;

        switch (type){
            case EndLevel:
                Main.game.enemies = new ArrayList<>();
                Main.game.enemyMissiles = new ArrayList<>();
                Main.game.powerUps = new ArrayList<>();

                Main.player.stamina = Main.player.staminaMax;
                Main.player.shootCharge = Main.player.shootChargeMax;
                Main.player.shieldCharge = Main.player.shieldChargeMax;

                Main.player.hp += Main.player.heal * ((Main.game.levelDesign.level - 1) / 5 + 1);
                Main.player.bombCount += Main.player.bombRecharge * (Main.game.levelDesign.level - 1) / 5 + 1;

                Main.game.levelDesign.level++;
                Main.game.levelDesign.create();
                break;

            case BombsUp:
                Main.player.bombCount++;
                break;

            case Sp:
                Main.player.sp++;
                break;

            case HpUp:
                Main.player.hp++;
                break;

            case ShotsUp:
                Main.player.shootCount++;
                break;

            case ShotsChargeRateUp:
                Main.player.shootChargeRate++;
                break;

            case CheaperShots:
                Main.player.shootCost -= 10;
                break;

            case ShotsChargeMaxUp:
                Main.player.shootChargeMax += 400;
                break;

            case HealUp:
                Main.player.heal++;
                break;

            case Mine:
                Main.player.hp -= (Main.game.levelDesign.level - 1) / 5 * 2;
                break;
        }
    }

    double[] spawnPos(){
        double[] pos = new double[2];

        Random rnd = new Random();

        pos[0] = Math.abs(rnd.nextFloat() * 1000) % (Display.screenWidth - 20);
        pos[1] = Math.abs(rnd.nextFloat() * 1000) % (Display.screenHeight - 40);
        while (!(Game.distance(Main.player.posX, Main.player.posY, pos[0], pos[1]) > Main.player.r * 5)) {
            pos[0] = Math.abs(rnd.nextFloat() * 1000) % (Display.screenWidth - 20);
            pos[1] = Math.abs(rnd.nextFloat() * 1000) % (Display.screenHeight - 40);
        }

        return pos;
    }
}
