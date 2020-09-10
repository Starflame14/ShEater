public class Main {


    static int missileId = 0;
    static int enemyId = 0;
    static int powerUpId = 0;

    static boolean infinite = true;

    static String[] args;

    static int score = 0;

    static int mode = 1;

    static int gameModes = 2;

    static Game game = new Game();
    static Player player = new Player();

    static Menu inGameMenu = new Menu(Menu.PAUSE);
    static Menu mainMenu = new Menu(Menu.MAIN);
    static Menu skillMenu = new Menu(Menu.SKILL);

    static Menu shotUpg = new Menu(Menu.SHOOT);
    static Menu staminaUpg = new Menu(Menu.STAMINA_HP);
    static Menu bombUpg = new Menu(Menu.BOMB);
    static Menu shieldUpg = new Menu(Menu.SHIELD);
    static Menu stompUpg = new Menu(Menu.STOMP);


    public static void main(String[] args) {
        Main.args = args;

        Display.create();

        game.start();
    }
}
