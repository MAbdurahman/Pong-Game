package net.mabdurrahman.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler Class
 * @author:  MAbdurrahman
 * @date:  30 May 2017
 */
public class KeyHandler implements KeyListener {

    /**
     * KeyHandler Constructor - Creates an instance of the InputHandler with one parameter
     * @param  - the game panel
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public KeyHandler(GamePanel game) {
        game.addKeyListener(this);

    }//end of the KeyHandler Constructor
    /**
     * keyPressed Method - Defines the keyPressed Method for the KeyListener Interface, and
     * responds to pressing the up and down arrow keys
     * @param - the event of pressing the up or down arrow key
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            GamePanel.player.movingDown = false;
            GamePanel.player.movingUp = true;

        }
        if (keyCode == KeyEvent.VK_DOWN) {
            GamePanel.player.movingUp = false;
            GamePanel.player.movingDown = true;

        }
    }//end of the keyPressed Method
    /**
     * keyReleased Method - Defines the keyReleased Method for the KeyListener Interface, and
     * responds to releasing the up and down arrow keys
     * @param - the event of releasing the up or down arrow key
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        int keyCode = ke.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            GamePanel.player.movingUp = false;

        }
        if (keyCode == KeyEvent.VK_DOWN) {
            GamePanel.player.movingDown = false;

        }
    }//end of the keyReleased Method
    @Override
    public void keyTyped(KeyEvent ke) {}

}//end of the KeyHandler Class


