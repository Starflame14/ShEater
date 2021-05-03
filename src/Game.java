import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    ControllerManager controllers = new ControllerManager();
    ControllerState currState;

    LevelDesign levelDesign = new LevelDesign();

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Missile> enemyMissiles = new ArrayList<>();
    ArrayList<Bomb> bombs = new ArrayList<>();
    ArrayList<PowerUp> powerUps = new ArrayList<>();

    KeyEvent keyEvent;
    boolean mousePressed = false;

    int tick;

    Random rnd = new Random();

    boolean gameOver = false;

    void start() {
        controllers.initSDLGamepad();

        waitWhileMenuActive(Main.mainMenu);

        if (Main.mode == 0) {
            for (int i = 0; i < 3; i++) {
                enemies.add(new Enemy(EnemyType.BASIC));
            }

            int nextLevelUp = rnd.nextInt(5000) + 5000;

            for (tick = 0; Main.player.hp > 0; tick++) {
                if (tick == nextLevelUp) {
                    nextLevelUp += rnd.nextInt(5000) + 5000;
                    levelDesign.level++;
                    levelDesign.createPowerUps(false);
                }

                resolveControls();
            }

            resolveGameOver();

        } else if (Main.mode == 1) {
            levelDesign.create();

            for (tick = 0; Main.player.hp > 0; tick++) {
                resolveControls();
            }

            resolveGameOver();
        }

        Display.frame.dispose();
        controllers.quitSDLGamepad();
    }

    void resolveControls() {
        long time = System.currentTimeMillis();

        currState = controllers.getState(0);

        if (currState.isConnected) {
            if (currState.startJustPressed) {
                Main.inGameMenu.active = true;
            }
        } else {
            if (keyEvent != null && keyEvent.getKeyChar() == '\u001B') {
                Main.inGameMenu.active = true;
                keyEvent = null;
            }
        }
        waitWhileMenuActive(Main.inGameMenu);

        if (currState.isConnected) {
            if (currState.backJustPressed) {
                Main.skillMenu.active = true;
            }
        } else {
            if (keyEvent != null && keyEvent.getKeyChar() == '\t') {
                Main.skillMenu.active = true;
                keyEvent = null;
            }
        }
        while (Main.skillMenu.active) {
            currState = controllers.getState(0);

            if (currState.isConnected) {
                if (currState.bJustPressed) {
                    Main.skillMenu.active = false;
                }
                if (currState.aJustPressed) {
                    Main.skillMenu.menuPoints.get(Main.skillMenu.selected).resolve();
                }
                if (currState.dpadDownJustPressed) {
                    Main.skillMenu.selected++;
                    if (Main.skillMenu.selected >= Main.skillMenu.menuPoints.size())
                        Main.skillMenu.selected = Main.skillMenu.menuPoints.size() - 1;
                }
                if (currState.dpadUpJustPressed) {
                    Main.skillMenu.selected--;
                    if (Main.skillMenu.selected < 0) {
                        Main.skillMenu.selected = 0;
                    }
                }
            } else {
                if (keyEvent != null && keyEvent.getKeyChar() == '\u001B') {
                    Main.skillMenu.active = false;
                    keyEvent = null;
                }
                if (keyEvent != null && (keyEvent.getKeyChar() == '\n' || keyEvent.getKeyChar() == ' ')) {
                    Main.skillMenu.menuPoints.get(Main.skillMenu.selected).resolve();
                    keyEvent = null;
                }
                if (keyEvent != null && keyEvent.getKeyChar() == 's') {
                    Main.skillMenu.selected++;
                    if (Main.skillMenu.selected >= Main.skillMenu.menuPoints.size()) {
                        Main.skillMenu.selected = Main.skillMenu.menuPoints.size() - 1;
                    }
                    keyEvent = null;
                }
                if (keyEvent != null && keyEvent.getKeyChar() == 'w') {
                    Main.skillMenu.selected--;
                    if (Main.skillMenu.selected < 0) {
                        Main.skillMenu.selected = 0;
                    }
                    keyEvent = null;
                }
            }

            Display.refresh();

            waitWhileMenuActive(Main.shotUpg);
            waitWhileMenuActive(Main.staminaUpg);
            waitWhileMenuActive(Main.bombUpg);
            waitWhileMenuActive(Main.shieldUpg);
            waitWhileMenuActive(Main.stompUpg);
        }

        if (currState.isConnected) {
            Main.player.dir = currState.leftStickAngle;
            if (currState.rightTrigger == 1) Main.player.fast = true;
            else Main.player.fast = false;
            if (currState.bJustPressed) Main.player.deployBomb();
            if (currState.lbJustPressed) Main.player.pickUpAllPowerUps();
            if (currState.yJustPressed) Main.player.burst();
            if (currState.aJustPressed) Main.player.stomp();
        } else {
            int mouseX = MouseInfo.getPointerInfo().getLocation().x;
            int mouseY = MouseInfo.getPointerInfo().getLocation().y;
            int frameX = Display.frame.getLocationOnScreen().x;
            int frameY = Display.frame.getLocationOnScreen().y;
            int x = mouseX - frameX;
            int y = mouseY - frameY;

            Main.player.dir = getDir(Main.player.posX, Main.player.posY + Main.player.r / 2, x, y);

            if (keyEvent != null && keyEvent.isShiftDown()) {
                Main.player.fast = true;
            } else {
                Main.player.fast = false;
            }
            if (keyEvent != null && keyEvent.getKeyChar() == 'c') {
                Main.player.deployBomb();
                keyEvent = null;
            }
            if (keyEvent != null && keyEvent.getKeyChar() == '\b') {
                Main.player.pickUpAllPowerUps();
                keyEvent = null;
            }
            if (keyEvent != null && keyEvent.getKeyChar() == 'z') {
                Main.player.burst();
                keyEvent = null;
            }
            if (keyEvent != null && keyEvent.getKeyChar() == ' ') {
                Main.player.stomp();
                keyEvent = null;
            }
        }

        Main.player.move();
        Main.player.moveMissiles();
        Main.player.explodeBombs();
        Main.player.shield.adjust();
        if (Main.player.stomping) Main.player.stomp.stomp();
        eatPowerUps();
        moveEnemies();
        moveEnemyMissiles();
        explodeBombs();
        killShield();
        killPlayer();
        killEnemies();

        if (currState.isConnected) {
            if (currState.xJustPressed) Main.player.shoot();
        } else {
            if ((keyEvent != null && keyEvent.getKeyChar() == 'x') || mousePressed) {
                Main.player.shoot();
                keyEvent = null;
                mousePressed = false;
            }
        }

        Display.refresh();

        while (System.currentTimeMillis() - time < 2) {
        }
    }

    void resolveGameOver() {
        gameOver = true;
        Display.refresh();

        if (Main.infinite) {
            while (true) {
                ControllerState currState = controllers.getState(0);
                if (currState.isConnected) {
                    if (currState.aJustPressed) restart();
                } else {
                    if (keyEvent != null && keyEvent.getKeyChar() == ' ') {
                        restart();
                        keyEvent = null;
                    }
                }
            }
        }
    }

    void eatPowerUps() {
        for (PowerUp powerUp : powerUps) {
            powerUp.timeRemaining--;
            if (distance(Main.player.posX, Main.player.posY, powerUp.posX, powerUp.posY)
                    <= Main.player.r / 2f + powerUp.r / 2f) {
                powerUp.eat();
            }
        }

        boolean finished = false;
        while (!finished) {
            finished = true;
            int index = -1;
            for (int i = 0; i < powerUps.size(); i++) {
                if (powerUps.get(i).timeRemaining <= 0) {
                    finished = false;
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Main.score++;
                powerUps.remove(index);
            }
        }
    }

    void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
    }

    static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    static double getDir(double x1, double y1, double x2, double y2) {
        return Math.atan2(y1 - y2, x2 - x1) * 180 / Math.PI;
    }

    void killEnemies() {
        for (Enemy enemy : enemies) {
            for (Missile missile : Main.player.missiles) {
                if (distance(enemy.posX, enemy.posY, missile.posX, missile.posY) <= enemy.r / 2f + missile.r / 2f) {
                    enemy.hp--;
                    missile.outOfBound = true;
                }
            }

            if (Main.player.stomp != null) {
                if (distance(enemy.posX, enemy.posY, Main.player.posX, Main.player.posY) <=
                        enemy.r / 2.0 + Main.player.stomp.r / 2.0) {
                    if (Main.game.tick % 50 == 0)
                        enemy.hp -= Main.player.stompDamage;
                }
            }
        }

        //eater
        for (Enemy enemy1 : enemies) {
            if (enemy1.type == EnemyType.EATER) {
                for (Enemy enemy2 : enemies) {
                    if (enemy2.id != enemy1.id && enemy2.type != EnemyType.GROWER && !enemy2.isBoss()
                            && distance(enemy1.posX, enemy1.posY, enemy2.posX, enemy2.posY)
                            <= enemy1.r / 2f + enemy2.r / 2f) {
                        if (enemy1.r < enemy2.r && enemy2.type == EnemyType.EATER) {
                            enemy2.hp += enemy1.hp;
                            enemy1.hp = 0;

                            enemy2.r += enemy1.r / 2;
                            enemy2.speed += enemy1.speed / 2;
                        } else {
                            enemy1.hp += enemy2.hp;
                            enemy2.hp = 0;

                            enemy1.r += enemy2.r / 2;
                            enemy1.speed += enemy2.speed / 2;
                        }
                    }
                }
            }
        }

        //boss eater
        for (Enemy enemy1 : enemies) {
            if (enemy1.type == EnemyType.BOSS_EATER) {
                for (Enemy enemy2 : enemies) {
                    if (enemy1.id != enemy2.id && enemy2.type != EnemyType.BOSS_GROWER
                            && distance(enemy1.posX, enemy1.posY, enemy2.posX, enemy2.posY)
                            <= enemy1.r / 2f + enemy2.r / 2f) {
                        enemy1.hp += enemy2.hp * 5;
                        enemy2.hp = 0;

                        enemy1.r += enemy2.r / 2;
                        enemy1.speed += enemy2.speed / 2;
                    }
                }
            }
        }

        //grower
        for (Enemy enemy1 : enemies) {
            if (enemy1.type == EnemyType.GROWER) {
                for (Enemy enemy2 : enemies) {
                    if (enemy2.id != enemy1.id && !enemy2.isBoss()
                            && distance(enemy1.posX, enemy1.posY, enemy2.posX, enemy2.posY)
                            <= enemy1.r / 2f + enemy2.r / 2f) {

                        if (enemy1.r < enemy2.r && enemy2.type == EnemyType.GROWER) {
                            enemy2.hp += enemy1.hp;
                            enemy1.hp = 0;

                            enemy2.r += enemy1.r / 2;
                        } else {
                            enemy1.hp += enemy2.hp;
                            enemy2.hp = 0;

                            enemy1.r += enemy2.r / 2;
                        }
                    }
                }
            }
        }

        //boss grower
        for (Enemy enemy1 : enemies) {
            if (enemy1.type == EnemyType.BOSS_GROWER) {
                for (Enemy enemy2 : enemies) {
                    if (enemy2.id != enemy1.id
                            && distance(enemy1.posX, enemy1.posY, enemy2.posX, enemy2.posY)
                            <= enemy1.r / 2f + enemy2.r / 2f) {

                        enemy1.hp += enemy2.hp * 5;
                        enemy2.hp = 0;

                        enemy1.r += enemy2.r * 2 / 3;
                    }
                }
            }
        }

        boolean finished = false;
        while (!finished) {
            finished = true;
            int index = -1;
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).hp <= 0) {
                    finished = false;
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Main.score++;

                //mother
                if (enemies.get(index).type == EnemyType.MOTHER) {
                    double x = enemies.get(index).posX;
                    double y = enemies.get(index).posY;

                    for (int i = 0; i < 3; i++) {
                        enemies.add(0, new Enemy(EnemyType.BASIC));
                        enemies.get(0).posX = x;
                        enemies.get(0).posY = y;
                    }

                    index += 3;
                }

                //boss mother
                if (enemies.get(index).type == EnemyType.BOSS_MOTHER) {
                    double x = enemies.get(index).posX;
                    double y = enemies.get(index).posY;

                    for (int i = 0; i < 9; i++) {
                        enemies.add(0, new Enemy(EnemyType.MOTHER));
                        enemies.get(0).posX = x;
                        enemies.get(0).posY = y;
                    }

                    index += 9;
                }

                //blower
                if (enemies.get(index).type == EnemyType.BLOWER) {
                    bombs.add(new Bomb(enemies.get(index)));
                }

                enemies.remove(index);
                if (Main.mode == 1 && enemies.size() == 0) {
                    levelDesign.createPowerUps(true);
                }
            }
        }
    }

    void killPlayer() {
        for (Enemy enemy : enemies) {
            if (distance(Main.player.posX, Main.player.posY, enemy.posX, enemy.posY)
                    <= Main.player.r / 2f + enemy.r / 2f) {
                enemy.hp--;
                Main.player.hp--;
                controllers.doVibration(0, 1f, 1f, 1000);
            }

            if (enemy.type == EnemyType.BEAMER) {
                if (Math.abs(Main.player.posX - enemy.posX) <= Main.player.r / 2.0
                        || Math.abs(Main.player.posY - enemy.posY) <= Main.player.r / 2.0) {
                    if (tick % 50 == 0) Main.player.hp -= (Main.game.levelDesign.level - 1) / 5 - 1;
                }
            }

            if (enemy.type == EnemyType.BOSS_BEAMER) {
                if (Math.abs(Main.player.posX - enemy.posX) <= Main.player.r / 2.0 - 10
                        || Math.abs(Main.player.posY - enemy.posY) <= Main.player.r / 2.0 - 10) {
                    if (tick % 10 == 0) Main.player.hp -= (Main.game.levelDesign.level - 1) / 5 - 1;
                }
            }
        }

        for (Missile missile : enemyMissiles) {
            if (distance(Main.player.posX, Main.player.posY, missile.posX, missile.posY)
                    <= Main.player.r / 2f + missile.r / 2f) {
                missile.outOfBound = true;
                Main.player.hp -= (Main.game.levelDesign.level - 1) / 5;
                controllers.doVibration(0, 1f, 1f, 1000);
            }
        }
    }

    void killShield() {
        if (Main.game.levelDesign.level <= 15 || Main.player.shield.hp <= 0) return;
        for (Enemy enemy : enemies) {
            if (distance(Main.player.shield.posX, Main.player.shield.posY, enemy.posX, enemy.posY)
                    <= Main.player.shield.r / 2f + enemy.r / 2f) {
                enemy.hp--;
                Main.player.shieldCharge -= 100000;
                controllers.doVibration(0, 1f, 1f, 1000);
            }

            if (enemy.type == EnemyType.BEAMER) {
                if (Math.abs(Main.player.shield.posX - enemy.posX) <= Main.player.shield.r / 2.0
                        || Math.abs(Main.player.shield.posY - enemy.posY) <= Main.player.shield.r / 2.0) {
                    if (tick % 50 == 0) Main.player.shieldCharge -= 100000;
                }
            }

            if (enemy.type == EnemyType.BOSS_BEAMER) {
                if (Math.abs(Main.player.shield.posX - enemy.posX) <= Main.player.shield.r / 2.0 - 10
                        || Math.abs(Main.player.shield.posY - enemy.posY) <= Main.player.shield.r / 2.0 - 10) {
                    if (tick % 10 == 0) Main.player.shieldCharge -= 100000;
                }
            }
        }

        for (Missile missile : enemyMissiles) {
            if (distance(Main.player.shield.posX, Main.player.shield.posY, missile.posX, missile.posY)
                    <= Main.player.shield.r / 2f + missile.r / 2f) {
                missile.outOfBound = true;
                Main.player.shieldCharge -= 100000 * ((Main.game.levelDesign.level - 1) / 5);
                controllers.doVibration(0, 1f, 1f, 1000);
            }
        }
    }

    void moveEnemyMissiles() {
        boolean finished = false;
        while (!finished) {
            finished = true;
            int index = -1;
            for (int i = 0; i < enemyMissiles.size(); i++) {
                if (enemyMissiles.get(i).outOfBound) {
                    finished = false;
                    index = i;
                    break;
                }
            }
            if (index != -1) enemyMissiles.remove(index);
        }

        for (Missile missile : enemyMissiles) {
            missile.move();
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

    void restart() {
        Main.missileId = 0;
        Main.enemyId = 0;
        Main.powerUpId = 0;
        Main.mode = 1;
        Main.score = 0;

        Main.game = new Game();
        Main.player = new Player();

        Main.inGameMenu = new Menu(Menu.PAUSE);
        Main.mainMenu = new Menu(Menu.MAIN);
        Main.skillMenu = new Menu(Menu.SKILL);

        Main.shotUpg = new Menu(Menu.SHOOT);
        Main.staminaUpg = new Menu(Menu.STAMINA_HP);
        Main.bombUpg = new Menu(Menu.BOMB);
        Main.shieldUpg = new Menu(Menu.SHIELD);
        Main.stompUpg = new Menu(Menu.STOMP);

        Display.frame.dispose();
        controllers.quitSDLGamepad();

        sleep(500);
        Main.main(Main.args);

    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void waitWhileMenuActive(Menu menu) {
        while (menu.active) {
            currState = controllers.getState(0);

            if (currState.isConnected) {
                if (currState.bJustPressed) {
                    menu.active = false;
                }
                if (currState.aJustPressed) {
                    menu.menuPoints.get(menu.selected).resolve();
                }
                if (currState.dpadDownJustPressed) {
                    menu.selected++;
                    if (menu.selected >= menu.menuPoints.size())
                        menu.selected = menu.menuPoints.size() - 1;
                }
                if (currState.dpadUpJustPressed) {
                    menu.selected--;
                    if (menu.selected < 0) {
                        menu.selected = 0;
                    }
                }
            } else {
                if (keyEvent != null && keyEvent.getKeyChar() == '\u001B') {
                    menu.active = false;
                    keyEvent = null;
                } else if (keyEvent != null && (keyEvent.getKeyChar() == '\n' || keyEvent.getKeyChar() == ' ')) {
                    menu.menuPoints.get(menu.selected).resolve();
                    keyEvent = null;
                } else if (keyEvent != null && keyEvent.getKeyChar() == 's') {
                    menu.selected++;
                    if (menu.selected >= menu.menuPoints.size()) {
                        menu.selected = menu.menuPoints.size() - 1;
                    }
                    keyEvent = null;
                } else if (keyEvent != null && keyEvent.getKeyChar() == 'w') {
                    menu.selected--;
                    if (menu.selected < 0) {
                        menu.selected = 0;
                    }
                    keyEvent = null;
                }
            }

            Display.refresh();
        }

    }

    static boolean hasEnemy(EnemyType type) {
        for (Enemy enemy : Main.game.enemies) {
            if (enemy.type == type) return true;
        }

        return false;
    }
}
