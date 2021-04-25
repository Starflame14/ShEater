public class Bomb extends Entity{
    double posX;
    double posY;

    int r = 20;

    int timeRemaining;

    boolean players;

    Bomb(Player player){
        posX = player.posX;
        posY = player.posY;

        timeRemaining = player.bombSetOff;

        players = true;
    }

    Bomb(Enemy enemy){
        posX = enemy.posX;
        posY = enemy.posY;

        timeRemaining = 1;
        players = false;
    }

    void explode(){
        timeRemaining--;

        if(timeRemaining == 0){
            if(players){
                for(Enemy enemy: Main.game.enemies){
                    if(Game.distance(posX, posY, enemy.posX, enemy.posY) <= Main.player.bombRange + enemy.r / 2.0){
                        enemy.hp -= Main.player.bombDamage;
                    }
                }
            } else {
                if(Game.distance(posX, posY, Main.player.posX, Main.player.posY) <= Main.player.r / 2.0 + 200){
                    Main.player.hp -= 5;
                }
            }
        }
    }
}
