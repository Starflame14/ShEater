import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Main.game.keyEvent = e;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Main.game.keyEvent = null;
    }
}
