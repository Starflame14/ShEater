import java.util.ArrayList;

public class Menu {
    ArrayList<MenuPoints> menuPoints = new ArrayList<>();

    int selected = 0;

    int type;

    boolean active;

    static final int PAUSE = 0;
    static final int MAIN = 1;
    static final int SKILL = 2;

    static final int SHOOT = 3;
    static final int STAMINA_HP = 4;
    static final int BOMB = 7;
    static final int SHIELD = 5;
    static final int STOMP = 6;

    Menu(int type){
        this.type = type;
        switch (type){
            case PAUSE:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.RESUME));
                menuPoints.add(new MenuPoints(MenuPoints.RESTART));
                break;

            case MAIN:
                active = true;
                menuPoints.add(new MenuPoints(MenuPoints.START));
                menuPoints.add(new MenuPoints(MenuPoints.SELECT_MODE));
                menuPoints.add(new MenuPoints(MenuPoints.QUIT));
                break;

            case SKILL:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.SHOOT));
                menuPoints.add(new MenuPoints(MenuPoints.STAMINA_HP));
                menuPoints.add(new MenuPoints(MenuPoints.BOMB));
                menuPoints.add(new MenuPoints(MenuPoints.SHIELD));
                menuPoints.add(new MenuPoints(MenuPoints.STOMP));
                break;

            case SHOOT:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.SHOT_RECHARGE));
                menuPoints.add(new MenuPoints(MenuPoints.SHOT_MAX));
                menuPoints.add(new MenuPoints(MenuPoints.SHOTS));
                menuPoints.add(new MenuPoints(MenuPoints.SHOT_SIZE));
                menuPoints.add(new MenuPoints(MenuPoints.SHOT_SPEED));
                menuPoints.add(new MenuPoints(MenuPoints.SHOT_DAMAGE));
                menuPoints.add(new MenuPoints(MenuPoints.BURST));
                break;

            case STAMINA_HP:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.STAMINA_RECHARGE));
                menuPoints.add(new MenuPoints(MenuPoints.STAMINA_MAX));
                menuPoints.add(new MenuPoints(MenuPoints.HEAL));
                break;

            case BOMB:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.BOMB_RECHARGE));
                menuPoints.add(new MenuPoints(MenuPoints.BOMB_RANGE));
                menuPoints.add(new MenuPoints(MenuPoints.BOMB_DAMAGE));
                break;

            case SHIELD:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.SHIELD_RECHARGE));
                menuPoints.add(new MenuPoints(MenuPoints.SHIELD_MAX));
                menuPoints.add(new MenuPoints(MenuPoints.SHIELD_RANGE));
                break;

            case STOMP:
                active = false;
                menuPoints.add(new MenuPoints(MenuPoints.STOMP_DAMAGE));
                menuPoints.add(new MenuPoints(MenuPoints.STOMP_RANGE));
                menuPoints.add(new MenuPoints(MenuPoints.STOMP_COST));
                break;


        }
    }
}
