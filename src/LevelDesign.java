import java.util.Random;

public class LevelDesign {
    int level = 1;

    Random rnd = new Random();

    void create(){
        create(level);
    }

    void create(int level) {
        switch (level){
            case 0:
                for (int i = 0; i < 5; i++) {
                    Main.game.enemies.add(new Enemy(EnemyType.BASIC));
                }
                break;

            case 1:
            case 2:
            case 3:
            case 4:
                if(rnd.nextFloat() <= 0.5){
                    for (int i = 0; i < 10; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.BASIC));
                    }
                } else {
                    for (int i = 0; i < 2; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.EATER));
                    }
                    for (int i = 0; i < 5; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.BASIC));
                    }
                }
                break;

            case 5:
                if(rnd.nextFloat() <= 0.5){
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_BASIC));
                } else {
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_EATER));
                    System.out.println("boss eater");
                    for (int i = 0; i < 8; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.BASIC));
                        System.out.println("basic " + i);
                    }
                }
                break;

            case 6:
            case 7:
            case 8:
            case 9:
                if(rnd.nextFloat() <= 0.5){
                    for (int i = 0; i < 5; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.SHOOTER));
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.CHARGER));
                    }
                }
                create(rnd.nextInt(level - 5));
                break;

            case 10:
                if(rnd.nextFloat() <= 0.5){
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_SHOOTER));
                } else {
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_CHARGER));
                }
                break;

            case 11:
            case 12:
            case 13:
            case 14:
                if(rnd.nextFloat() <= 0.5){
                    Main.game.enemies.add(new Enemy(EnemyType.GROWER));
                    Main.game.enemies.add(new Enemy(EnemyType.GROWER));
                } else {
                    for (int i = 0; i < 5; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.MOTHER));
                    }
                }
                create(rnd.nextInt(level - 5));
                break;

            case 15:
                if(rnd.nextFloat() <= 0.5) {
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_MOTHER));
                } else {
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_GROWER));
                    for (int i = 0; i < 10; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.BASIC));
                    }
                }
                break;

            case 16:
            case 17:
            case 18:
            case 19:
                if(rnd.nextFloat() <= 0.5){
                    for (int i = 0; i < 5; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.MINER));
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        Main.game.enemies.add(new Enemy(EnemyType.BEAMER));
                    }
                }
                create(rnd.nextInt(level - 5));
                break;

            case 20:
                if(rnd.nextFloat() <= 0.5){
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_MINER));
                } else {
                    Main.game.enemies.add(new Enemy(EnemyType.BOSS_BEAMER));
                }
                break;

            case 21:
            case 22:
            case 23:
            case 24:
                if(rnd.nextFloat() <= 0.99){
                    Main.game.enemies.add(new Enemy(EnemyType.SNIPER));
                } else {

                }



        }

        if (rnd.nextFloat() <= 0.01) create();
    }

    void createPowerUps() {
        Main.game.powerUps.add(new PowerUp(PowerUpType.EndLevel));

        for (int i = 0; i < level + 1; i++) {
            while (true) {
                if (rnd.nextFloat() <= 0.5) {
                    Main.game.powerUps.add(new PowerUp(PowerUpType.Sp));
                } else break;
            }
            if (rnd.nextFloat() <= Math.pow(0.7, Math.floor(level / 5) + 1)) {
                Main.game.powerUps.add(new PowerUp(PowerUpType.HpUp));
            }
            if(level > 5 && rnd.nextFloat() <= Math.pow(0.8, Math.floor(level / 5) + 1)){
                Main.game.powerUps.add(new PowerUp(PowerUpType.BombsUp));
            }

        }

        if (rnd.nextFloat() <= 0.01) createPowerUps();
    }
}
