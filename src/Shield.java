public class Shield {
    double posX;
    double posY;

    int r = (Main.player == null ? 50 : Main.player.r);
    int hp;

    void adjust(){
        posX = Main.player.posX;
        posY = Main.player.posY;

        r = Main.player.shieldRange * 2 + Main.player.r;
        hp = (int) Math.floor(Main.player.shieldCharge / 100000);
    }

    void stomp(){
        posX = Main.player.posX;
        posY = Main.player.posY;

        r++;
        if(r > Main.player.stompRange * 10 + Main.player.r){
            r = 0;

            Main.player.stomping = false;
        }
    }

}
