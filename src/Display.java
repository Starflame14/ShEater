import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display {

    static JFrame frame;
    static MyPanel panel;

    static final int screenWidth = 1200;
    static final int screenHeight = 600;

    static void create() {
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new MyKeyListener());
        frame.addMouseListener(new MyMouseListener());
        frame.setFocusTraversalKeysEnabled(false);
        frame.setVisible(true);

        panel = new MyPanel();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    static void refresh() {
        frame.getContentPane().remove(panel);
        panel = new MyPanel();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    static void drawPlayer(Graphics g) {
        int x = (int) Main.player.posX;
        int y = (int) Main.player.posY;
        int r = Main.player.r;
        double angle = ((Main.player.dir + 90) * Math.PI / 180) % 360;

        g.setColor(Color.GREEN);
        g.fillOval(x - r / 2, y - r / 2, r, r);

        int endX = (int) (x + 40 * Math.sin(angle));
        int endY = (int) (y + 40 * Math.cos(angle));

        g.setColor(Color.BLACK);
        g.drawLine(x, y, endX, endY);

        //shield
        if(Main.game.levelDesign.level > 15 && Main.player.shield.hp >= 1){
            g.setColor(Color.BLACK);
            g.drawOval(x - Main.player.shield.r / 2, y - Main.player.shield.r / 2,
                    Main.player.shield.r, Main.player.shield.r);
        }

        //stomp
        if(Main.player.stomping){
            g.setColor(Color.BLACK);
            g.drawOval(x - Main.player.stomp.r / 2, y - Main.player.stomp.r / 2,
                    Main.player.stomp.r, Main.player.stomp.r);
        }

    }

    static void drawMissiles(Graphics g) {
        g.setColor(Color.BLUE);
        for (Missile missile : Main.player.missiles) {
            g.fillOval((int) missile.posX - missile.r / 2, (int) missile.posY - missile.r / 2,
                    missile.r, missile.r);
        }

        g.setColor(Color.BLACK);
        for (Missile missile : Main.game.enemyMissiles) {
            g.fillOval((int) missile.posX - missile.r / 2, (int) missile.posY - missile.r / 2,
                    missile.r, missile.r);
        }

        for (Bomb bomb : Main.player.bombs) {
            g.setColor(Color.BLACK);
            g.fillOval((int) bomb.posX - bomb.r / 2, (int) bomb.posY - bomb.r / 2,
                    bomb.r, bomb.r);

            g.setColor(Color.RED);
            g.fillOval((int) bomb.posX - bomb.r / 4, (int) bomb.posY - bomb.r / 4,
                    bomb.r / 2, bomb.r / 2);

            if (bomb.timeRemaining <= 0) {
                g.setColor(Color.RED);
                g.fillOval((int) bomb.posX - Main.player.bombRange, (int) bomb.posY - Main.player.bombRange,
                        Main.player.bombRange * 2, Main.player.bombRange * 2);
            }
        }

        for (Bomb bomb: Main.game.bombs){
            if (bomb.timeRemaining <= 0) {
                g.setColor(Color.RED);
                g.fillOval((int) bomb.posX - 200, (int) bomb.posY - 200,
                        400, 400);
            }
        }
    }

    static void drawEnemies(Graphics g) {
        for (Enemy enemy : Main.game.enemies) {
            ArrayList<Double> posXs = new ArrayList<>();
            ArrayList<Double> posYs = new ArrayList<>();

            posXs.add(enemy.posX + Display.screenWidth - 20);
            posXs.add(enemy.posX - Display.screenWidth + 20);
            posXs.add(enemy.posX);
            posXs.add(enemy.posX);
            posXs.add(enemy.posX);

            posYs.add(enemy.posY);
            posYs.add(enemy.posY);
            posYs.add(enemy.posY + Display.screenHeight - 40);
            posYs.add(enemy.posY - Display.screenHeight + 40);
            posYs.add(enemy.posY);

            for (int i = 0; i < 5; i++) {
                double posX = posXs.get(i);
                double posY = posYs.get(i);

                switch (enemy.type) {
                    case BASIC:
                    case BOSS_BASIC:
                    case MOTHER:
                    case BOSS_MOTHER:
                        g.setColor(Color.RED);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case EATER:
                    case BOSS_EATER:
                        g.setColor(Color.MAGENTA);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case CHARGER:
                        g.setColor(Color.BLACK);
                        g.drawOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.RED);
                        if (enemy.dir == 1 || enemy.dir == 3) {
                            g.fillRect((int) posX - 10, (int) posY - 30, 20, 60);
                        } else {
                            g.fillRect((int) posX - 30, (int) posY - 10, 60, 20);
                        }

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case BOSS_CHARGER:
                        g.setColor(Color.BLACK);
                        g.drawOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.RED);
                        if (enemy.dir == 1 || enemy.dir == 3) {
                            g.fillRect((int) posX - 20, (int) posY - 50, 40, 100);
                        } else {
                            g.fillRect((int) posX - 50, (int) posY - 20, 100, 40);
                        }

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case SHOOTER:
                    case BOSS_SHOOTER:
                        g.setColor(Color.ORANGE);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case GROWER:
                    case BOSS_GROWER:
                        g.setColor(Color.BLACK);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.WHITE);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case MINER:
                    case BOSS_MINER:
                        g.setColor(Color.RED);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.BLACK);
                        g.fillOval((int) posX - enemy.r / 4, (int) posY - enemy.r / 4,
                                enemy.r / 2, enemy.r / 2);

                        g.setColor(Color.GREEN);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case BEAMER:
                        g.setColor(Color.BLACK);
                        g.drawOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.RED);
                        g.fillRect((int) posX - 15, (int) posY - 15,
                                30, 30);
                        g.drawLine(0, (int) posY, Display.screenWidth, (int) posY);
                        g.drawLine((int) posX, 0, (int) posX, Display.screenHeight);

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case BOSS_BEAMER:
                        g.setColor(Color.BLACK);
                        g.drawOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.RED);
                        g.fillRect((int) posX - 30, (int) posY - 30,
                                60, 60);
                        g.fillRect(0, (int) posY - 10, Display.screenWidth, 20);
                        g.fillRect((int) posX - 10, 0, 20, Display.screenHeight);

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case SNIPER:
                        g.setColor(Color.YELLOW);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.BLACK);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;

                    case BLOWER:
                        g.setColor(Color.BLACK);
                        g.fillOval((int) posX - enemy.r / 2, (int) posY - enemy.r / 2,
                                enemy.r, enemy.r);

                        g.setColor(Color.RED);
                        g.fillOval((int) posX - enemy.r / 4, (int) posY - enemy.r / 4,
                                enemy.r / 2, enemy.r / 2);

                        g.setColor(Color.GREEN);
                        g.drawString("HP: " + enemy.hp, (int) posX - 15, (int) posY + 5);
                        break;
                }
            }
        }
    }

    static void drawPowerUps(Graphics g) {
        for (PowerUp powerUp : Main.game.powerUps) {
            switch (powerUp.type) {
                case EndLevel:
                case ShotsChargeMaxUp:
                    g.setColor(Color.BLUE);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.BLACK);
                    g.fillOval((int) powerUp.posX - powerUp.r / 4, (int) powerUp.posY - powerUp.r / 4,
                            powerUp.r / 2, powerUp.r / 2);
                    break;

                case BombsUp:
                    g.setColor(Color.BLUE);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);
                    break;

                case Sp:
                    g.setColor(Color.GREEN);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.BLACK);
                    g.drawOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);
                    break;

                case HpUp:
                    g.setColor(Color.RED);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.GREEN);
                    g.fillRect((int) powerUp.posX - 6, (int) powerUp.posY - 13, 12, 26);
                    g.fillRect((int) powerUp.posX - 13, (int) powerUp.posY - 6, 26, 12);
                    break;

                case ShotsUp:
                    g.setColor(Color.BLUE);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.BLACK);
                    g.fillRect((int) powerUp.posX - 3, (int) powerUp.posY - 13, 6, 26);
                    g.fillRect((int) powerUp.posX - 13, (int) powerUp.posY - 3, 26, 6);
                    break;

                case ShotsChargeRateUp:
                    g.setColor(Color.BLUE);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.RED);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX, (int) powerUp.posY + 12);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX - 8, (int) powerUp.posY - 4);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX + 8, (int) powerUp.posY - 4);
                    break;

                case CheaperShots:
                    g.setColor(Color.BLUE);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.GREEN);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX, (int) powerUp.posY + 12);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY + 12,
                            (int) powerUp.posX - 8, (int) powerUp.posY + 4);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY + 12,
                            (int) powerUp.posX + 8, (int) powerUp.posY + 4);
                    break;

                case HealUp:
                    g.setColor(Color.RED);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);

                    g.setColor(Color.GREEN);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX, (int) powerUp.posY + 12);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX - 8, (int) powerUp.posY - 4);
                    g.drawLine((int) powerUp.posX, (int) powerUp.posY - 12,
                            (int) powerUp.posX + 8, (int) powerUp.posY - 4);
                    break;

                case Mine:
                    g.setColor(Color.BLACK);
                    g.fillOval((int) powerUp.posX - powerUp.r / 2, (int) powerUp.posY - powerUp.r / 2,
                            powerUp.r, powerUp.r);
                    break;
            }
        }
    }

    static void drawMenu(Graphics g, Menu menu) {
        int posY = 100;

        ArrayList<MenuPoints> points = menu.menuPoints;
        for (int i = 0; i < points.size(); i++) {
            MenuPoints menuPoints = points.get(i);

            if (menu.selected == i) g.setColor(Color.RED);
            else g.setColor(Color.BLACK);
            g.setFont(new Font(null, 0, 30));
            String text = menuPoints.text;
            switch (menuPoints.type) {
                case MenuPoints.SHOT_RECHARGE:
                    text += " (";
                    text += Main.player.shotChargeUpCost * (Main.player.shootChargeRate - 9);
                    text += ")";
                    break;

                case MenuPoints.SHOT_MAX:
                    text += " (";
                    text += Main.player.shotMaxCost * (Main.player.shootChargeMax / Main.player.shootCost - 9);
                    text += ")";
                    break;

                case MenuPoints.SHOTS:
                    text += " (";
                    text += Main.player.shotsUpCost * Main.player.shootCount;
                    text += ")";
                    break;

                case MenuPoints.SHOT_SIZE:
                    text += " (";
                    text += Main.player.shotSizeCost * (Main.player.shotSize / 5 - 1);
                    text += ")";
                    break;

                case MenuPoints.SHOT_SPEED:
                    text += " (";
                    text += Main.player.shotSpeedCost * (int) (Main.player.shotSpeed / 0.05 - 7);
                    text += ")";
                    break;

                case MenuPoints.SHOT_DAMAGE:
                    text += " (";
                    text += Main.player.shotDamageCost * Main.player.shotDamage;
                    text += ")";
                    break;

                case MenuPoints.BURST:
                    text += " (";
                    text += Main.player.burstUpCost * (Main.player.burst / 2 - 2);
                    text += ")";
                    break;

                case MenuPoints.STAMINA_RECHARGE:
                    text += " (";
                    text += Main.player.staminaChargeUpCost * (Main.player.staminaChargeRate / 2 - 4);
                    text += ")";
                    break;

                case MenuPoints.STAMINA_MAX:
                    text += " (";
                    text += Main.player.staminaMaxCost * (Main.player.staminaMax / 10000 - 9);
                    text += ")";
                    break;

                case MenuPoints.HEAL:
                    text += " (";
                    text += Main.player.healUpCost * (Main.player.heal + 1);
                    text += ")";
                    break;

                case MenuPoints.SHIELD_RECHARGE:
                    text += " (";
                    text += Main.player.shieldChargeUpCost * (Main.player.shieldChargeRate - 9);
                    text += ")";
                    break;

                case MenuPoints.SHIELD_MAX:
                    text += " (";
                    text += Main.player.shieldMaxCost * (Main.player.shieldChargeMax / 100000);
                    text += ")";
                    break;

                case MenuPoints.SHIELD_RANGE:
                    text += " (";
                    text += Main.player.shieldRangeUpCost * (Main.player.shieldRange / 5);
                    text += ")";
                    break;

                case MenuPoints.BOMB_RECHARGE:
                    text += " (";
                    text += Main.player.bombChargeUpCost * (Main.player.bombRecharge + 1);
                    text += ")";
                    break;

                case MenuPoints.BOMB_RANGE:
                    text += " (";
                    text += Main.player.bombRangeUpCost * (Main.player.bombRange / 20 - 9);
                    text += ")";
                    break;

                case MenuPoints.BOMB_DAMAGE:
                    text += " (";
                    text += Main.player.bombDamageUpCost * Main.player.bombDamage;
                    text += ")";
                    break;

                case MenuPoints.STOMP_DAMAGE:
                    text += " (";
                    text += Main.player.stompDamageUpCost * Main.player.stompDamage;
                    text += ")";
                    break;

                case MenuPoints.STOMP_RANGE:
                    text += " (";
                    text += Main.player.stompRangeUpCost * (Main.player.stompRange / 10);
                    text += ")";
                    break;

                case MenuPoints.STOMP_COST:
                    text += " (";
                    text += Main.player.stompCheaperCost * ((101000 - Main.player.stompCost) / 1000);
                    text += ")";
                    break;
            }
            g.drawString(text, 520, posY);

            posY += 50;
        }
    }

    static void printInfo(Graphics g) {
        String info = "";
        info += "HP: " + Main.player.hp;
        info += "   shots: " + (Main.player.shootCharge / Main.player.shootCost)
                + "/" + (Main.player.shootChargeMax / Main.player.shootCost);
        info += "   stamina: " + (int) (100 * ((double) Main.player.stamina / (double) Main.player.staminaMax)) + "%";
        if (Main.game.levelDesign.level > 5)
            info += "   bombs: " + Main.player.bombCount;
        if(Main.game.levelDesign.level > 15)
            info += "   shield: " + Main.player.shield.hp;
        info += "   SP: " + Main.player.sp;
        if (Main.mode == 0) info += "   score: " + Main.score;
        else if (Main.mode == 1) info += "   level: " + Main.game.levelDesign.level;

        g.setColor(Color.BLACK);
        g.drawString(info, 10, 15);
    }

    static void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font(null, 0, 100));
        g.drawString("GAME OVER", 300, 300);
    }
}
