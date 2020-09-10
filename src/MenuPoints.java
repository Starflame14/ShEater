public class MenuPoints {
    int type;

    String text;

    static final int RESUME = 0;
    static final int RESTART = 1;
    static final int START = 2;
    static final int QUIT = 3;
    static final int SELECT_MODE = 4;

    static final int SHOOT = 5;
    static final int STAMINA_HP = 6;
    static final int BOMB = 22;
    static final int SHIELD = 7;
    static final int STOMP = 8;

    static final int SHOT_RECHARGE = 28;
    static final int SHOT_MAX = 9;
    static final int SHOTS = 11;
    static final int SHOT_SIZE = 12;
    static final int SHOT_SPEED = 13;
    static final int SHOT_DAMAGE = 14;
    static final int BURST = 15;

    static final int STAMINA_RECHARGE = 16;
    static final int STAMINA_MAX = 17;
    static final int HEAL = 18;

    static final int SHIELD_RECHARGE = 19;
    static final int SHIELD_MAX = 20;
    static final int SHIELD_RANGE = 21;

    static final int BOMB_RECHARGE = 23;
    static final int BOMB_RANGE = 24;
    static final int BOMB_DAMAGE = 25;

    static final int STOMP_DAMAGE = 26;
    static final int STOMP_RANGE = 27;
    static final int STOMP_COST = 29;

    MenuPoints(int type) {
        this.type = type;

        switch (type) {
            case RESUME:
                text = "RESUME";
                break;

            case RESTART:
                text = "MAIN MENU";
                break;

            case START:
                text = "START";
                break;

            case QUIT:
                text = "QUIT";
                break;

            case SELECT_MODE:
                if (Main.mode == 0) {
                    text = "MODE: ENDLESS";
                } else if (Main.mode == 1) {
                    text = "MODE: NORMAL";
                }
                break;

            case SHOOT:
                text = "SHOOTING";
                break;

            case STAMINA_HP:
                text = "STAMINA & HP";
                break;

            case SHIELD:
                text = "SHIELD";
                break;

            case STOMP:
                text = "STOMP";
                break;

            case SHOT_RECHARGE:
                text = "RECHARGE";
                break;

            case SHOT_MAX:
                text = "MAX";
                break;

            case SHOTS:
                text = "BULLETS";
                break;

            case SHOT_SIZE:
                text = "SIZE";
                break;

            case SHOT_SPEED:
                text = "SPEED";
                break;

            case SHOT_DAMAGE:
                text = "DAMAGE";
                break;

            case BURST:
                text = "BURST BULLETS";
                break;

            case STAMINA_RECHARGE:
                text = "RECHARGE";
                break;

            case STAMINA_MAX:
                text = "MAX";
                break;

            case HEAL:
                text = "HEAL";
                break;

            case SHIELD_RECHARGE:
                text = "RECHARGE";
                break;

            case SHIELD_MAX:
                text = "MAX";
                break;

            case SHIELD_RANGE:
                text = "RANGE";
                break;

            case BOMB:
                text = "BOMB";
                break;

            case BOMB_RECHARGE:
                text = "RECHARGE";
                break;

            case BOMB_RANGE:
                text = "RANGE";
                break;

            case BOMB_DAMAGE:
                text = "DAMAGE";
                break;

            case STOMP_DAMAGE:
                text = "DAMAGE";
                break;

            case STOMP_RANGE:
                text = "RANGE";
                break;

            case STOMP_COST:
                text = "COST";
                break;
        }
    }

    void resolve() {
        switch (type) {
            case RESUME:
                Main.inGameMenu.active = false;
                break;

            case RESTART:
                Main.game.restart();
                break;

            case START:
                Main.mainMenu.active = false;
                break;

            case QUIT:
                Main.mainMenu.active = false;
                Main.player.hp = 0;
                Main.infinite = false;
                break;

            case SELECT_MODE:
                Main.mode++;
                if (Main.mode >= Main.gameModes) Main.mode = 0;
                if (Main.mode == 0) text = "MODE: ENDLESS";
                else if (Main.mode == 1) text = "MODE: NORMAL";
                break;

            case SHOOT:
                Main.shotUpg.active = true;
                break;

            case STAMINA_HP:
                Main.staminaUpg.active = true;
                break;

            case SHIELD:
                Main.shieldUpg.active = true;
                break;

            case STOMP:
                Main.stompUpg.active = true;
                break;

            case SHOT_RECHARGE:
                if(Main.player.sp >= Main.player.shotChargeUpCost * (Main.player.shootChargeRate - 9)){
                    Main.player.sp -= Main.player.shotChargeUpCost * (Main.player.shootChargeRate - 9);
                    Main.player.shootChargeRate++;
                }
                break;

            case SHOT_MAX:
                if(Main.player.sp >=
                        Main.player.shotMaxCost * (Main.player.shootChargeMax / Main.player.shootCost - 9)){
                    Main.player.sp -= Main.player.shotMaxCost * (Main.player.shootChargeMax / Main.player.shootCost - 9);
                    Main.player.shootChargeMax += Main.player.shootCost;
                }
                break;

            case SHOTS:
                if(Main.player.sp >= Main.player.shotsUpCost * Main.player.shootCount){
                    Main.player.sp -= Main.player.shotsUpCost * Main.player.shootCount;
                    Main.player.shootCount++;
                }
                break;

            case SHOT_SIZE:
                if(Main.player.sp >= Main.player.shotSizeCost * (Main.player.shotSize / 5 - 1)){
                    Main.player.sp -= Main.player.shotSizeCost * (Main.player.shotSize / 5 - 1);
                    Main.player.shotSize += 5;
                }
                break;

            case SHOT_SPEED:
                if((double)Main.player.sp >= (double)Main.player.shotSpeedCost * (Main.player.shotSpeed / 0.05 - 7)){
                    Main.player.sp -= Main.player.shotSpeedCost * (Main.player.shotSpeed / 0.05 - 7);
                    Main.player.shotSpeed += 0.05;
                }
                break;

            case SHOT_DAMAGE:
                if(Main.player.sp >= Main.player.shotDamageCost * Main.player.shotDamage){
                    Main.player.sp -= Main.player.shotDamageCost * Main.player.shotDamage;
                    Main.player.shotDamage++;
                }
                break;

            case BURST:
                if(Main.player.sp >= Main.player.burstUpCost * (Main.player.burst / 2 - 2)){
                    Main.player.sp -= Main.player.burstUpCost * (Main.player.burst / 2 - 2);
                    Main.player.burst += 2;
                }
                break;

            case STAMINA_RECHARGE:
                if(Main.player.sp >= Main.player.staminaChargeUpCost * (Main.player.staminaChargeRate / 2 - 4)){
                    Main.player.sp -= Main.player.staminaChargeUpCost * (Main.player.staminaChargeRate / 2 - 4);
                    Main.player.staminaChargeRate += 2;
                }
                break;

            case STAMINA_MAX:
                if(Main.player.sp >= Main.player.staminaMaxCost * (Main.player.staminaMax / 10000 - 9)){
                    Main.player.sp -= Main.player.staminaMaxCost * (Main.player.staminaMax / 10000 - 9);
                    Main.player.staminaMax += 10000;
                }
                break;

            case HEAL:
                if(Main.player.sp >= Main.player.healUpCost * (Main.player.heal + 1)){
                    Main.player.sp -= Main.player.healUpCost * (Main.player.heal + 1);
                    Main.player.heal++;
                }
                break;

            case SHIELD_RECHARGE:
                if(Main.player.sp >= Main.player.shieldChargeUpCost * (Main.player.shieldChargeRate - 9)){
                    Main.player.sp -= Main.player.shieldChargeUpCost * (Main.player.shieldChargeRate - 9);
                    Main.player.shieldChargeRate++;
                }
                break;

            case SHIELD_MAX:
                if(Main.player.sp >= Main.player.shieldMaxCost * (Main.player.shieldChargeMax / 100000)){
                    Main.player.sp -= Main.player.shieldMaxCost * (Main.player.shieldChargeMax / 100000);
                    Main.player.shieldChargeMax += 100000;
                }
                break;

            case SHIELD_RANGE:
                if(Main.player.sp >= Main.player.shieldRangeUpCost * (Main.player.shieldRange / 5)){
                    Main.player.sp -= Main.player.shieldRangeUpCost * (Main.player.shieldRange / 5);
                    Main.player.shieldRange += 5;
                }
                break;

            case BOMB:
                Main.bombUpg.active = true;
                break;

            case BOMB_RECHARGE:
                if(Main.player.sp >= Main.player.bombChargeUpCost * (Main.player.bombRecharge + 1)){
                    Main.player.sp -= Main.player.bombChargeUpCost * (Main.player.bombRecharge + 1);
                    Main.player.bombRecharge++;
                }
                break;

            case BOMB_RANGE:
                if(Main.player.sp >= Main.player.bombRangeUpCost * (Main.player.bombRange / 20 - 9)){
                    Main.player.sp -= Main.player.bombRangeUpCost * (Main.player.bombRange / 20 - 9);
                    Main.player.bombRange += 20;
                }
                break;

            case BOMB_DAMAGE:
                if(Main.player.sp >= Main.player.bombDamageUpCost * Main.player.bombDamage){
                    Main.player.sp -= Main.player.bombDamageUpCost * Main.player.bombDamage;
                    Main.player.bombDamage++;
                }
                break;

            case STOMP_DAMAGE:
                if(Main.player.sp >= Main.player.stompDamageUpCost * Main.player.stompDamage){
                    Main.player.sp -= Main.player.stompDamageUpCost * (Main.player.stompDamage + 1);
                    Main.player.stompDamage++;
                }
                break;

            case STOMP_RANGE:
                if(Main.player.sp >= Main.player.stompRangeUpCost * (Main.player.stompRange / 10)){
                    Main.player.sp -= Main.player.stompRangeUpCost * (Main.player.stompRange / 10);
                    Main.player.stompRange += 10;
                }
                break;

            case STOMP_COST:
                if(Main.player.sp >= Main.player.stompCheaperCost * ((101000 - Main.player.stompCost) / 1000)){
                    Main.player.sp -= Main.player.stompCheaperCost * ((101000 - Main.player.stompCost) / 1000);
                    Main.player.stompCost -= 1000;
                }
                break;
        }
    }
}
