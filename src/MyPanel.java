import javax.swing.*;
import java.awt.*;

public class MyPanel extends JComponent {
    public void paint(Graphics g) {

        if (Main.mainMenu.active) {
            Display.drawMenu(g, Main.mainMenu);
        } else {
            Display.printInfo(g);
            if (Main.shotUpg.active) {
                Display.drawMenu(g, Main.shotUpg);
            } else if (Main.staminaUpg.active) {
                Display.drawMenu(g, Main.staminaUpg);
            } else if (Main.bombUpg.active) {
                Display.drawMenu(g, Main.bombUpg);
            } else if (Main.shieldUpg.active) {
                Display.drawMenu(g, Main.shieldUpg);
            } else if (Main.stompUpg.active) {
                Display.drawMenu(g, Main.stompUpg);
            } else if (Main.inGameMenu.active) {
                Display.drawMenu(g, Main.inGameMenu);
            } else if (Main.skillMenu.active) {
                Display.drawMenu(g, Main.skillMenu);
            } else {
                Display.drawPowerUps(g);
                Display.drawPlayer(g);
                Display.drawEnemies(g);
                Display.drawMissiles(g);

                if (Main.game.gameOver) Display.gameOver(g);
            }
        }
    }
}