import java.util.ArrayList;

public abstract class Entity {
    double posX;
    double posY;
    double dir;

    int r;

    double speed;

    ArrayList<Missile> missiles;

    void move(){}
}
