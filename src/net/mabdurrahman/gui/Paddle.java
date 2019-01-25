package net.mabdurrahman.gui;

import java.awt.Graphics2D;

/**
 * The Paddle Interface
 * @author:  MAbdurrahman
 * @date:  30 May 2017
 */
public interface Paddle {
    void render(Graphics2D g2d);
    void update(GamePanel game);

}//end of the Paddle Interface