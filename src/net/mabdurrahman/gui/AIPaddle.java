package net.mabdurrahman.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import static java.awt.geom.Rectangle2D.OUT_BOTTOM;
import static java.awt.geom.Rectangle2D.OUT_LEFT;
import static java.awt.geom.Rectangle2D.OUT_RIGHT;
import static java.awt.geom.Rectangle2D.OUT_TOP;

/**
 * The AIPaddle Class
 * @author:  MAbdurrahman
 * @date:  30 May 2017
 */
public class AIPaddle extends Rectangle2D implements Paddle {
    //Instance Variables
    private final Color PADDLE_COLOR = new Color(255, 255, 212);

    private double paddleWidth = 15;
    private double paddleHeight = 150;
    private final double GRAVITY = 0.94;
    private final double SPEED = 6;
    protected double xPos;
    protected double yPos;
    protected double yMidpoint;
    protected boolean movingUp;
    protected boolean movingDown;

    /**
     * AIPaddle Constructor - Creates an instance of the AIPaddle
     * @param - a double of the horizontal position
     * @param - a double of the vertical position
     */
    public AIPaddle(double horizontal, double vertical) {
        this.xPos = horizontal;
        this.yPos = vertical;
        double yPos2 = (yPos + this.paddleHeight);
        this.yMidpoint = ((yPos + yPos2) / 2);
        this.movingUp = false;
        this.movingDown = false;

    }//end of the PlayerPaddle Constructor
    /**
     * update Method -
     * @param - the game panel
     * @return Void
     */
    @Override
    public void update(GamePanel game) {
        if (GamePanel.ball.yPos < this.yPos) {
            yPos -= SPEED;
        }
        if (GamePanel.ball.yPos > this.yPos) {
            yPos += SPEED;
        }

    }//end of the update Method
    /**
     * render Method -
     * @param - the 2 dimensional graphics context
     */
    @Override
    public void render(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(PADDLE_COLOR);
        g2d.fill(new Rectangle2D.Double(xPos, yPos, paddleWidth, paddleHeight));

    }//end of the render Method
    /**
     * getX Method - Overrides the getX Method for the Rectangle2D Class, and gets the horizontal
     * position of the AIPaddle Class.
     * @return Double - Returns the horizontal position Rectangle2D in the form of a double
     */
    @Override
    public double getX() {
        return this.xPos;

    }//end of the getXPosition Method
    /**
     * getY Method - Overrides the getY Method for the Rectangle2D Class, and gets the vertical
     * position of the AIPaddle Class.
     * @return Double - Returns the vertical position of the Rectangle2D in the form of a double
     */
    @Override
    public double getY() {
        return this.yPos;

    }//end of the getYPosition Method
    /**
     * getWidth Method - Overrides the getWidth Method for the Rectangle2D Class, and responds to
     * the AIPaddle Class
     * @return Double - Returns the width dimension of the Rectangle2D in the form of a double
     */
    @Override
    public double getWidth() {
        return this.paddleWidth;

    }//end of the getWidth Method
    /**
     * getHeight Method - Override the getHeight Method for the Rectangle2D Class, and responds to
     * the AIPaddle Class.
     * @return Double - Returns the height dimension of the Rectangle2D in the form of a double
     */
    @Override
    public double getHeight() {
        return this.paddleHeight;

    }//end of the getHeight Method
    /**
     * setRect Method - Sets the location at the (X, Y) axis with the size of the width and
     * height of the Rectangle2D.
     * @param - a double of the X position
     * @param - a double of the Y position
     * @param - a double of the width dimension
     * @param - a double of the height dimension
     */
    @Override
    public void setRect(double x, double y, double w, double h) {
        this.xPos = x;
        this.yPos = y;
        this.paddleWidth = w;
        this.paddleHeight = h;

    }//end of the setRect Method
    /**
     * setRect Method - Overrides the setRect Method of the Rectangle2D Class, and responds to
     * the AIPaddle Class
     * @param  - the two dimension rectangle
     */
    @Override
    public void setRect(Rectangle2D rectangle) {
        this.xPos   = (double) rectangle.getX();
        this.yPos   = (double) rectangle.getY();
        this.paddleWidth  = (double) rectangle.getWidth();
        this.paddleHeight = (double) rectangle.getHeight();

    }//end of the setRect Method
    /**
     * outcode Method
     * @param - a double of the horizontal position
     * @param - a double of the vertical position
     * @return Integer - Returns the bitmask that indicates a specified point
     */
    @Override
    public int outcode(double x, double y) {
        int out = 0;
        if (this.paddleWidth <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;

        } else if (x < this.xPos) {
            out |= OUT_LEFT;

        } else if (x > this.xPos + (double) this.paddleWidth) {
            out |= OUT_RIGHT;

        } else if (this.paddleHeight <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;

        } else if (y < this.yPos) {
            out |= OUT_TOP;

        } else if (y > this.yPos + (double) this.paddleHeight) {
            out |= OUT_BOTTOM;

        }
        return out;
    }
    /**
     * getBounds2D Method - Overrides the getBounds2D Method of the Rectangle2D Class, and responds to
     * the AIPaddle Class
     * @return Rectangle2D - Returns a Rectangle2D rectangle at horizontal and vertical position with the
     * dimension of width and height.
     */
    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(xPos, yPos, paddleWidth, paddleHeight);

    }//end of the getBounds2D Method
    /**
     * intersectsLine Method - Determines if the specified line segment intersects the interior of
     * the Rectangle2D for the Ball Class
     * @param - a double of the X coordinate of the start point of the specified line segment
     * @param - a double of the Y coordinate of the start point of the specified line segment
     * @param - a double of the X coordinate of the end point of the specified line segment
     * @param - a double of the Y coordinate of the end point of the specified line segment
     * @return Boolean - Returns true, if the specified line segment intersects the interior of
     * this (Ball Class) Rectangle2D rectangle; otherwise, it returns false.
     */
    @Override
    public boolean intersectsLine(double x1, double y1, double x2, double y2) {
        int out1, out2;
        if ((out2 = outcode(x2, y2)) == 0) {
            return true;

        }
        while ((out1 = outcode(x1, y1)) !=0) {
            if ((out1 & out2) != 0) {
                return false;

            }
            if ((out1 & (OUT_LEFT | OUT_RIGHT)) != 0) {
                double x = getX();
                if ((out1 & OUT_RIGHT) != 0) {
                    x += getWidth();

                }
                y1 = (y1 + (x - x1) * (y2 - y1) / (x2 - x));
                x1 = x;

            } else {
                double y = getY();
                if ((out1 & OUT_BOTTOM) != 0) {
                    y += getHeight();

                }
                x1 = (x1 + (y - y1) * (x2 - x1) / (y2 - y1));
                y1 = y;
            }
        }
        return true;

    }//end of the intersectsLine Method
    /**
     * intersectsLine Method - Determines whether the specified line segment intersects the interior of
     * this (Ball Class) Rectangle2D
     * @param- the specified 2 dimensional line segment
     * @return Boolean - Returns true, if the specified line segment intersects the Ball Class Rectangle2D;
     * otherwise, it returns false
     */
    @Override
    public boolean intersectsLine(Line2D line) {
        return intersectsLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());

    }//end of the intersectsLine Method
    /**
     * createIntersection Method - Creates an intersection of the Rectangle2D
     * @param - the specified 2 dimensional rectangle to be intersected
     * @return Rectangle2D - Returns the destination Rectangle2D rectangle
     */
    @Override
    public Rectangle2D createIntersection(Rectangle2D rect) {
        Rectangle2D dest;
        if (rect instanceof Rectangle2D.Float) {
            dest = new Rectangle2D.Float();

        } else {
            dest = new Rectangle2D.Double();

        }
        Rectangle2D.intersect(this, rect, dest);
        return dest;

    }//end of the createIntersection Method
    /**
     * createUnion Method - Creates a union of the AIPaddle Rectangle2D with the specified
     * Rectangle2D at destination Rectangle2D
     * @param - the specified Rectangle2D
     * @return Rectangle2D - Returns the destination Rectangle2D
     */
    @Override
    public Rectangle2D createUnion(Rectangle2D rect) {
        Rectangle2D dest;
        if (rect instanceof Rectangle2D.Float) {
            dest = new Rectangle2D.Float();

        } else {
            dest = new Rectangle2D.Double();

        }
        Rectangle2D.union(this, rect, dest);
        return dest;

    }//end of the createUnion Method

    /**
     * isEmpty Method - Overrides the isEmpty Method of the Rectangle2D Class, and responds to
     * the PlayerPaddle Class
     * @return Boolean - Returns true, if the Rectangle2D is empty.  Otherwise, it returns false.
     */
    @Override
    public boolean isEmpty() {
        return (paddleWidth <= 0.0) || (paddleHeight <= 0.0);

    }//end of the isEmpty Method
    /**
     * toString Method - Overrides the toString Method of the Object Class, and redefines it
     * for the AIPaddle Class
     * @return String - Return a String representation of the Ball
     */
    @Override
    public String toString() {
        return getClass().getName()
                + "[x = "+ xPos
                + ".y = "+ yPos
                + ".w = "+ paddleWidth
                + ".h = "+ paddleHeight
                + "]";

    }//end of the toString Method
}//end of the AIPaddle Class


