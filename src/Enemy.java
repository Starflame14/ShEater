import java.util.Random;

public class Enemy extends Entity {
    int id;

    double posX;
    double posY;
    double dir;

    double speed;

    EnemyType type;

    int r;

    int hp;

    int lifeSpan = 0;
    boolean fast = false;

    Random rnd = new Random();

    Enemy(EnemyType type) {
        id = Main.enemyId;
        Main.enemyId++;

        int multiplier = ((Main.game.levelDesign.level - 1) / 5) + 1;

        this.type = type;

        switch (type) {
            case BASIC:
                speed = 0.1;
                r = 30;
                hp = multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case EATER:
                speed = 0.05;
                r = 50;
                hp = 3 * multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case CHARGER:
                speed = 0.1;
                r = 60;
                hp = multiplier;
                dir = rnd.nextInt(4);
                break;

            case SHOOTER:
                speed = 0.1;
                r = 40;
                hp = 2 * multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case GROWER:
                speed = 0;
                r = 10;
                hp = 9 * multiplier;
                dir = 0;
                break;

            case MOTHER:
                speed = 0.1;
                r = 60;
                hp = 3 * multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case MINER:
            case BEAMER:
                speed = 0.2;
                r = 40;
                hp = 8 * multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BLOWER:
                speed = 0.3;
                r = 30;
                hp = 5 * multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case SNIPER:
                speed = 0.2;
                r = 40;
                hp = 5 * multiplier;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BOSS_BASIC:
                speed = 0.3;
                r = 250;
                hp = 50;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BOSS_EATER:
                speed = 0.1;
                r = 250;
                hp = 50;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BOSS_SHOOTER:
                speed = 0.1;
                r = 200;
                hp = 100;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BOSS_CHARGER:
                speed = 0.1;
                r = 100;
                hp = 50;
                dir = rnd.nextInt(4);
                break;

            case BOSS_MOTHER:
                speed = 0.3;
                r = 250;
                hp = 150;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BOSS_GROWER:
                speed = 0;
                r = 100;
                hp = 300;
                dir = 0;
                break;

            case BOSS_MINER:
                speed = 1;
                r = 60;
                hp = 400;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

            case BOSS_BEAMER:
                speed = 0.6;
                r = 80;
                hp = 200;
                dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                break;

        }

        double[] pos = spawnPos();
        posX = pos[0];
        posY = pos[1];
    }

    void move() {
        double angle1, angle2;
        switch (type) {
            case BASIC:
            case BOSS_BASIC:
            case MOTHER:
            case BOSS_MOTHER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan >= 500 + id) {
                    lifeSpan = 0;

                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                    speed *= 1.05;
                }
                break;

            case EATER:
            case BOSS_EATER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan >= 500 + id) {
                    lifeSpan = 0;

                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                }
                break;

            case CHARGER:
            case BOSS_CHARGER:
                switch ((int) dir) {
                    case 0:
                        posX += speed;
                        break;

                    case 1:
                        posY += speed;
                        break;

                    case 2:
                        posX -= speed;
                        break;

                    case 3:
                        posY -= speed;
                        break;
                }

                speed = 0.1;

                lifeSpan++;
                if (lifeSpan >= 500 + id) {
                    lifeSpan = 0;
                    dir = rnd.nextInt(4);
                } else if (lifeSpan >= 200 && lifeSpan <= 300) {
                    speed = 4;
                }
                break;

            case SHOOTER:
            case BOSS_SHOOTER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan % (type == EnemyType.SHOOTER ? 200 : 50) == 0) {
                    double dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                    Main.game.enemyMissiles.add(new Missile(this, dir));
                }
                if (lifeSpan >= 800 + id) {
                    lifeSpan = 0;
                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                }
                break;

            case GROWER:
                lifeSpan++;
                if (lifeSpan % 40 == 0) {
                    r++;
                }
                if (lifeSpan >= 200) {
                    lifeSpan = 0;
                    hp++;
                }
                break;

            case BOSS_GROWER:
                lifeSpan++;
                if (lifeSpan % 20 == 0) {
                    r++;
                }
                if (lifeSpan >= 100) {
                    lifeSpan = 0;
                    hp++;
                }
                break;

            case MINER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan >= 400 + id) {
                    lifeSpan = 0;
                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                    Main.game.powerUps.add(0, new PowerUp(PowerUpType.Mine));
                    Main.game.powerUps.get(0).posX = posX;
                    Main.game.powerUps.get(0).posY = posY;
                }
                break;

            case BOSS_MINER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan % 100 == 0) {
                    Main.game.powerUps.add(0, new PowerUp(PowerUpType.Mine));
                    Main.game.powerUps.get(0).posX = posX;
                    Main.game.powerUps.get(0).posY = posY;
                }
                if (lifeSpan >= 400 + id) {
                    lifeSpan = 0;
                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                }
                break;

            case BEAMER:
            case BOSS_BEAMER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan >= 1000 + id) {
                    lifeSpan = 0;
                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                }
                break;

            case SNIPER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;

                if (lifeSpan % 200 == 0) {
                    Main.game.enemyMissiles.add(new Missile(
                            this,
                            Game.getDir(this.posX, this.posY, Main.player.posX, Main.player.posY)
                    ));
                }
                if (lifeSpan >= 1000 + id) {
                    lifeSpan = 0;
                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                }
                break;

            case BLOWER:
                angle1 = ((dir + 180) * Math.PI / 180) % 360;
                angle2 = ((dir + 0) * Math.PI / 180) % 360;

                posX = posX + (Math.cos(angle2) * speed);
                posY = posY + (Math.sin(angle1) * speed);

                lifeSpan++;
                if (lifeSpan >= 500 + id) {
                    Main.game.bombs.add(new Bomb(this));

                    lifeSpan = 0;

                    dir = Math.abs(rnd.nextFloat() * 1000) % 360;
                }
                break;
        }

        if (posX < 0) posX += Display.screenWidth - 20;
        if (posX > Display.screenWidth - 20) posX -= Display.screenWidth - 20;
        if (posY < 0) posY += Display.screenHeight - 40;
        if (posY > Display.screenHeight - 40) posY -= Display.screenHeight - 40;

    }

    double[] spawnPos() {
        double[] pos = new double[2];

        pos[0] = Math.abs(rnd.nextFloat() * 1000) % Display.screenWidth;
        pos[1] = Math.abs(rnd.nextFloat() * 1000) % Display.screenHeight;
        while (!(Game.distance(Main.player.posX, Main.player.posY, pos[0], pos[1]) > Main.player.r * 5)) {
            pos[0] = Math.abs(rnd.nextFloat() * 1000) % Display.screenWidth;
            pos[1] = Math.abs(rnd.nextFloat() * 1000) % Display.screenHeight;
        }

        return pos;
    }

    boolean isBoss(){
        return type == EnemyType.BOSS_BASIC || type == EnemyType.BOSS_EATER ||
                type == EnemyType.BOSS_SHOOTER || type == EnemyType.BOSS_CHARGER ||
                type == EnemyType.BOSS_GROWER || type == EnemyType.BOSS_MOTHER ||
                type == EnemyType.BOSS_MINER || type == EnemyType.BOSS_BEAMER;
    }
}
