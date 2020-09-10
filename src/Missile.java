public class Missile extends Entity{
    int id;

    double posX;
    double posY;
    double dir;

    double speed;

    int r = 10;

    Entity entity;

    boolean players;

    boolean outOfBound = false;

    Missile(Player player, double dir) {
        id = Main.missileId;
        Main.missileId++;

        players = true;

        double angle = ((player.dir + 90) * Math.PI / 180) % 360;

        posX = player.posX + player.r/2f * Math.sin(angle);
        posY = player.posY + player.r/2f * Math.cos(angle);
        this.dir = dir;

        r = player.shotSize;
        speed = player.shotSpeed;

        this.entity = player;
    }

    Missile(Enemy enemy, double dir){
        id = Main.missileId;
        Main.missileId++;

        players = false;

        double angle = (dir * Math.PI / 180) % 360;

        posX = enemy.posX + enemy.r/2f * Math.sin(angle);
        posY = enemy.posY + enemy.r/2f * Math.cos(angle);
        this.dir = dir;

        speed = 0.2;

        this.entity = enemy;
    }

    void move() {
        double angle1 = ((dir + 180) * Math.PI / 180) % 360;
        double angle2 = ((dir + 0) * Math.PI / 180) % 360;

        posX = posX + (Math.cos(angle2) * speed);
        posY = posY + (Math.sin(angle1) * speed);

        if (posX < 0
                || posX > Display.screenWidth
                || posY < 0
                || posY > Display.screenHeight) {
            outOfBound = true;
        }
    }
}
