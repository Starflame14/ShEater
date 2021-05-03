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
        int cost;
        int tier = (Main.game.levelDesign.level - 1) / 5 + 1;
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
                System.exit(69);
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
                cost = Main.player.currShotChargeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shootChargeRate++;
                    Main.player.currShotChargeUpCost += Main.player.shotChargeUpCost;
                }
                break;

            case SHOT_MAX:
                cost = Main.player.currShotMaxCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shootChargeMax += Main.player.shootCost;
                    Main.player.currShotMaxCost += Main.player.shotMaxCost;
                }
                break;

            case SHOTS:
                cost = Main.player.currShotsUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shootCount++;
                    Main.player.currShotsUpCost += Main.player.shotsUpCost;
                }
                break;

            case SHOT_SIZE:
                cost = Main.player.currShotSizeCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shotSize += 5;
                    Main.player.currShotSizeCost += Main.player.shotSizeCost;
                }
                break;

            case SHOT_SPEED:
                cost = Main.player.currShotSpeedCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shotSpeed += 0.05;
                    Main.player.currShotSpeedCost += Main.player.shotSpeedCost;
                }
                break;

            case SHOT_DAMAGE:
                cost = Main.player.currShotDamageCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shotDamage++;
                    Main.player.currShotDamageCost += Main.player.shotDamageCost;
                }
                break;

            case BURST:
                cost = Main.player.currBurstUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.burst += 2;
                    Main.player.currBurstUpCost += Main.player.burstUpCost;
                }
                break;

            case STAMINA_RECHARGE:
                cost = Main.player.currStaminaChargeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.staminaChargeRate += 2;
                    Main.player.currStaminaChargeUpCost += Main.player.staminaChargeUpCost;
                }
                break;

            case STAMINA_MAX:
                cost = Main.player.currStaminaMaxCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.staminaMax += 10000;
                    Main.player.currStaminaMaxCost += Main.player.staminaMaxCost;
                }
                break;

            case HEAL:
                cost = Main.player.currHealUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.heal++;
                    Main.player.currHealUpCost += Main.player.healUpCost;
                }
                break;

            case SHIELD_RECHARGE:
                cost = Main.player.currShieldChargeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shieldChargeRate++;
                    Main.player.currShieldChargeUpCost += Main.player.shieldChargeUpCost;
                }
                break;

            case SHIELD_MAX:
                cost = Main.player.currShieldMaxCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shieldChargeMax += 100000;
                    Main.player.currShieldMaxCost += Main.player.shieldMaxCost;
                }
                break;

            case SHIELD_RANGE:
                cost = Main.player.currShieldRangeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.shieldRange += 5;
                    Main.player.currShieldRangeUpCost += Main.player.shieldRangeUpCost;
                }
                break;

            case BOMB:
                Main.bombUpg.active = true;
                break;

            case BOMB_RECHARGE:
                cost = Main.player.currBombChargeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.bombRecharge++;
                    Main.player.currBombChargeUpCost += Main.player.bombChargeUpCost;
                }
                break;

            case BOMB_RANGE:
                cost = Main.player.currBombRangeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.bombRange += 20;
                    Main.player.currBombRangeUpCost += Main.player.bombRangeUpCost;
                }
                break;

            case BOMB_DAMAGE:
                cost = Main.player.currBombDamageUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.bombDamage++;
                    Main.player.currBombDamageUpCost += Main.player.bombDamageUpCost;
                }
                break;

            case STOMP_DAMAGE:
                cost = Main.player.currStompDamageUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.stompDamage++;
                    Main.player.currStompDamageUpCost += Main.player.stompDamageUpCost;
                }
                break;

            case STOMP_RANGE:
                cost = Main.player.currStompRangeUpCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.stompRange += 10;
                    Main.player.currStompRangeUpCost += Main.player.stompRangeUpCost;
                }
                break;

            case STOMP_COST:
                cost = Main.player.currStompCheaperCost;
                if (Main.player.sp >= cost) {
                    Main.player.spendSp(cost);
                    Main.player.stompCost -= 1000;
                    Main.player.currStompCheaperCost += Main.player.stompCheaperCost;
                }
                break;
        }
    }
}
