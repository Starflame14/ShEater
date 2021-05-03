import java.util.ArrayList;

public class Player extends Entity {

    double posX = Display.screenWidth / 2;
    double posY = Display.screenHeight / 2;
    double dir = 0;

    double speed = 0.2;

    int hp = 3;

    int bombCount = 0;

    int bombRecharge = 0;
    int bombRange = 200;
    int bombDamage = 1;

    int bombSetOff = 300;

    ArrayList<Bomb> bombs = new ArrayList<>();

    boolean fast = false;
    boolean stomping = false;

    int shootChargeRate = 10;
    int shootCost = 400;
    int shootChargeMax = 4000;
    int shootCharge = shootChargeMax;

    int shootCount = 1;

    int staminaChargeRate = 10;
    int staminaMax = 100000;
    int stamina = staminaMax;

    int shieldChargeRate = 10;
    int shieldChargeMax = 100000;
    int shieldCharge = 0;
    int shieldRange = 5;

    int r = 50;

    Shield shield = new Shield();

    Shield stomp;

    int stompDamage = 1;
    int stompRange = 10;
    int stompCost = 100000;

    int sp = 0;
    int spent = 0;

    int heal = 0;

    int shotSize = 10;
    double shotSpeed = 0.4;

    int shotDamage = 1;

    int shotChargeUpCost = 1;
    int shotMaxCost = 1;
    int shotsUpCost = 20;
    int shotSizeCost = 3;
    int shotSpeedCost = 3;
    int shotDamageCost = 10;
    int burstUpCost = 6;

    int currShotChargeUpCost = 1;
    int currShotMaxCost = 1;
    int currShotsUpCost = 20;
    int currShotSizeCost = 3;
    int currShotSpeedCost = 3;
    int currShotDamageCost = 10;
    int currBurstUpCost = 6;

    int staminaChargeUpCost = 1;
    int staminaMaxCost = 1;
    int healUpCost = 6;

    int currStaminaChargeUpCost = 1;
    int currStaminaMaxCost = 1;
    int currHealUpCost = 6;

    int bombChargeUpCost = 3;
    int bombRangeUpCost = 1;
    int bombDamageUpCost = 10;

    int currBombChargeUpCost = 3;
    int currBombRangeUpCost = 1;
    int currBombDamageUpCost = 10;

    int shieldChargeUpCost = 1;
    int shieldMaxCost = 5;
    int shieldRangeUpCost = 1;

    int currShieldChargeUpCost = 1;
    int currShieldMaxCost = 5;
    int currShieldRangeUpCost = 1;

    int stompDamageUpCost = 5;
    int stompRangeUpCost = 2;
    int stompCheaperCost = 1;

    int currStompDamageUpCost = 5;
    int currStompRangeUpCost = 2;
    int currStompCheaperCost = 1;

    int burst = 6;

    ArrayList<Missile> missiles = new ArrayList<>();

    void move() {
        double currSpeed = currSpeed();

        double angle1 = ((dir + 180) * Math.PI / 180) % 360;
        double angle2 = ((dir + 0) * Math.PI / 180) % 360;

        posX = posX + (Math.cos(angle2) * currSpeed);
        posY = posY + (Math.sin(angle1) * currSpeed);

        if (posX < 0) posX += Display.screenWidth - 20;
        if (posX > Display.screenWidth - 20) posX -= Display.screenWidth - 20;
        if (posY < 0) posY += Display.screenHeight - 40;
        if (posY > Display.screenHeight - 40) posY -= Display.screenHeight - 40;

        shieldCharge += shieldChargeRate * 10;
        if (shieldCharge > shieldChargeMax) shieldCharge = shieldChargeMax;

        if (Main.game.tick % 10 == 0) shootCharge += shootChargeRate;
        if (shootCharge > shootChargeMax) shootCharge = shootChargeMax;
    }

    double currSpeed() {
        if (fast && stamina >= 5) {
            stamina -= 50;
            return speed * 3;
        } else {
            stamina += staminaChargeRate;
            if (stamina > staminaMax) stamina = staminaMax;
            return speed;
        }
    }

    void moveMissiles() {
        boolean finished = false;
        while (!finished) {
            finished = true;
            int index = -1;
            for (int i = 0; i < missiles.size(); i++) {
                if (missiles.get(i).outOfBound) {
                    finished = false;
                    index = i;
                    break;
                }
            }
            if (index != -1) missiles.remove(index);
        }

        for (Missile missile : missiles) {
            missile.move();
        }
    }

    void shoot() {
        if (shootCharge >= shootCost) {
            shootCharge -= shootCost;
            for (int j = 0; j < Main.player.shotDamage; j++) {
                double dir = Main.player.dir - (5 * (shootCount - 1));
                for (int i = 0; i < shootCount; i++) {
                    missiles.add(new Missile(this, dir));
                    dir += 10;
                }
            }

        }
    }

    void deployBomb() {
        if (Main.game.levelDesign.level > 5 && bombCount > 0) {
            bombCount--;
            bombs.add(new Bomb(this));
        }
    }

    void burst() {
        if (shootCharge >= shootCost * 5 && Main.game.levelDesign.level > 10) {
            shootCharge -= shootCost * 5;
            for (int j = 0; j < Main.player.shotDamage; j++) {
                double dir = Main.player.dir - (360.0 / (double) burst) * ((double) burst / 2.0);
                for (int i = 0; i < burst; i++) {
                    missiles.add(new Missile(this, dir));
                    dir += (360.0 / (double) burst);
                }
            }
        }
    }

    void explodeBombs() {
        boolean finished = false;
        while (!finished) {
            finished = true;
            int index = -1;
            for (int i = 0; i < bombs.size(); i++) {
                if (bombs.get(i).timeRemaining < -10) {
                    finished = false;
                    index = i;
                    break;
                }
            }

            if (index != -1) bombs.remove(index);
        }

        for (Bomb bomb : bombs) {
            bomb.explode();
        }
    }

    void pickUpAllPowerUps() {
        for (PowerUp powerUp : Main.game.powerUps) {
            if(powerUp.type != PowerUpType.EndLevel){
                powerUp.eat();
            }
        }
    }

    void stomp(){
        if(stamina >= stompCost && Main.game.levelDesign.level > 20){
            stamina -= stompCost;

            stomp = new Shield();

            stomping = true;
        }
    }

    //TODO balance testing
    void spendSp(int cost){
        sp -= cost;

        int currPenalty = spent / 10;

        spent += cost;

        /*
        int penalty = spent / 10 - currPenalty;

        currShotChargeUpCost += penalty;
        currShotMaxCost += penalty;
        currShotsUpCost += penalty;
        currShotSizeCost += penalty;
        currShotSpeedCost += penalty;
        currShotDamageCost += penalty;
        currBurstUpCost += penalty;

        currStaminaChargeUpCost += penalty;
        currStaminaMaxCost += penalty;
        currHealUpCost += penalty;

        currBombChargeUpCost += penalty;
        currBombRangeUpCost += penalty;
        currBombDamageUpCost += penalty;

        currShieldChargeUpCost += penalty;
        currShieldMaxCost += penalty;
        currShieldRangeUpCost += penalty;

        currStompDamageUpCost += penalty;
        currStompRangeUpCost += penalty;
        currStompCheaperCost += penalty;
        */
    }
}

