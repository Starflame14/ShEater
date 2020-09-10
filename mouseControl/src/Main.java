import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static java.awt.MouseInfo.getPointerInfo;

public class Main {

    static Robot robot;
    static Point mouse;
    static int speed = 2;


    public static void main(String[] args) throws Exception {
        robot = new Robot();
        robot.setAutoDelay(10);
        ControllerManager controllerManager = new ControllerManager();
        controllerManager.initSDLGamepad();
        ControllerState state;
        boolean prevRb = false;
        while (true) {
            state = controllerManager.getState(0);

            float ldx = state.leftStickX;
            float ldy = -state.leftStickY;
            if (state.leftStickMagnitude < 1) {
                ldy = 0;
            }

            float rdx = state.rightStickX;
            float rdy = -state.rightStickY;
            if (state.rightStickMagnitude < 1) {
                rdy = 0;
            }

            mouse = getPointerInfo().getLocation();
            float x = mouse.x;
            float y = mouse.y;

            robot.mouseMove((int) (x + speed * ldx + 5 * speed * rdx), (int) (y + speed * ldy + 5 * speed * rdy));

            if (!prevRb && state.a) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            }
            if (prevRb && !state.a) {
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
            prevRb = state.a;

            if (state.bJustPressed) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            }

            if (state.leftTrigger == 1) {
                if (Math.random() < 0.3) {
                    robot.mouseWheel(-1);
                }
            }
            if (state.rightTrigger == 1) {
                if (Math.random() < 0.3) {
                    robot.mouseWheel(1);
                }
            }

            if (state.xJustPressed) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }
        }
    }
}