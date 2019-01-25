package net.mabdurrahman.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * The Ball Class represents a simulation of the ball for the game
 * @author:  MAbdurrahman
 * @date:  30 May 2017
 * @version:  1.0.0
 */
public class Ball extends Rectangle2D {
    //Instance Variables
    //private final Color BALL_COLOR = new Color(255, 255, 212);
    private final Color BALL_COLOR = new Color(245, 245, 75);
    private double width = 30;
    private double height = 30;

    protected double speed;//the velocity
    protected double xPos, yPos;//the horizontal and vertical positions
    protected double xVel, yVel;//the horizontal and vertical velocities
    protected Line2D midpoint;//the midpoint of the ball

    /**
     * Ball Constructor - Creates an instance of the Ball Class with two parameters.  The
     * first parameter takes an integer to place the ball at the horizontal position, and
     * the second parameter takes an integer to place the ball at the vertical position.
     * @param - an integer of the horizontal position
     * @param - an integer of the vertical position
     */
    public Ball(double horizontal, double vertical) {
        this.xPos = horizontal;
        this.yPos = vertical;
        this.speed = 8;
        this.xVel = speed;
        this.yVel = speed;
        this.midpoint = new Line2D.Double(getX(), (getY() + (this.height / 2)),
                (getX() + this.width), (getY() + (this.height / 2)));

    }//end of the Ball Constructor
    /**
     * update Method - Updates the playerScore, AIScore, and the ball collisions with the player
     * and AI paddles.  And it implements an advance algorithm for the smashing the ball, when
     * five different sections of the paddle is struck with the ball.
     * @param  - the game panel
     * @return Void
     */
    public void update(GamePanel game) {
        /**Update the x and y positions with the x and y velocity*/
        xPos += xVel;
        yPos += yVel;

        /**The ball is on the left side of the screen*/
        if (xPos <= 0) {
            xVel = (speed * 1.5);
            game.playerScore++;

        }
        /**The ball is on the right side of the screen*/
        if ((xPos + this.width) >= GamePanel.SCREEN_WIDTH) {
            xVel = (speed * -1.5);
            game.AIScore++;

        }
        /**The ball is at the top of the screen*/
        if (yPos <= 0) {
            yVel = (speed * 1.5);

        }
        /**The ball is at the bottom of the screen*/
        if ((yPos + (this.height + this.width)) >= GamePanel.SCREEN_HEIGHT) {
            yVel = (speed * -1.5);

        }
        /**The ball has collide with the paddle on the right side of the screen*/
        if (getCollisionWithPlayerPaddle()) {
            this.xVel *= -1.5;

            if ((this.getBounds2D().intersects(GamePanel.player.getRectangleTwoBounds()) &&
                    (this.getMidpoint().intersects(GamePanel.player.getRectangleTwoBounds())))) {
                /**The ball is at second rectangle down from the top*/

                double number = (this.yPos + this.height - GamePanel.player.yPos) /
                        (GamePanel.player.paddleHeight + this.height);
                double angle = (0.333333 * Math.PI * (2 * number - 1));//a 60 degree angle - PI/3
                double smash = Math.abs(angle) > (0.3 * Math.PI) ? 3 : 2;
                this.xVel = (smash * -2 * speed * Math.cos(angle));
                this.yVel = (smash * 2 * speed * Math.sin(angle));

            } else if ((this.getBounds2D().intersects(GamePanel.player.getRectangleFourBounds()) &&
                    (this.getMidpoint().intersects(GamePanel.player.getRectangleFourBounds())))) {
                /**The ball is at fourth rectangle down from the top*/
                // this.xVel *= (speed * 3);
                // this.yVel *= -3;


                double number = (this.yPos + this.height - GamePanel.player.yPos) /
                        (GamePanel.player.paddleHeight + this.height);
                double angle = (0.33333 * Math.PI * (2 * number - 1));//a 45 degree angle
                double smash = Math.abs(angle) > (0.333 * Math.PI) ? 3 : 2;
                this.xVel = (smash * 2 * speed * Math.cos(angle));
                this.yVel = (smash * -2 * speed * Math.sin(angle));


            } else if (this.getBounds2D().intersects(GamePanel.player.getRectangleThreeBounds()) &&
                    (this.getMidpoint().intersects(GamePanel.player.getRectangleThreeBounds()))) {
                /**The ball is at the middle rectangle of the player paddle*/
                double number = (this.yPos + this.height - GamePanel.player.yPos) /
                        (GamePanel.player.paddleHeight + this.height);
                double angle = (0.5 * Math.PI * (2 * number - 1));//a 90 degree angle - PI/2
                double smash = Math.abs(angle) > (0.2 * Math.PI) ? 5 : 3;
                this.xVel = (smash * -1 * speed * Math.cos(angle));
                this.yVel = 0;
            }
            else if (this.getBounds2D().intersects(GamePanel.player.getRectangleOneBounds())) {
                /**The ball is at the top rectangle of the player paddle*/
                double number = (this.yPos + this.height - GamePanel.player.yPos) /
                        (GamePanel.player.paddleHeight + this.height);
                double angle = (0.25 * Math.PI * (2 * number - 1));// a 45 degree angle - PI/4
                double smash = Math.abs(angle) > (0.2 * Math.PI) ? 5 : 3;
                this.xVel = (smash * -1 * speed * Math.cos(angle));
                this.yVel = (smash * 1 * speed * Math.sin(angle));

            }
            else if (this.getBounds2D().intersects(GamePanel.player.getRectangleFiveBounds())) {
                /**The ball is at the bottom rectangle of the player paddle*/
                double number = (this.yPos + this.height - GamePanel.player.yPos) /
                        (GamePanel.player.paddleHeight + this.height);
                double angle = (0.25 * Math.PI * (2 * number - 1));//a 45 degree angle - PI/4
                double smash = Math.abs(angle) > (0.2 * Math.PI) ? 5 : 3;
                this.xVel = (smash * -1 * speed * Math.cos(angle));
                this.yVel = (smash * 1 * speed * Math.sin(angle));

            }
        }
    }//end of the update Method
    /**
     * render Method - Draws the ball with the specified color at the horizontal and vertical
     * position with the ball diameter
     * @param - the 2 dimensional graphics context
     * @return Void
     */
    public void render(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(BALL_COLOR);
        g2d.fill(new Rectangle2D.Double(xPos, yPos, width, height));

    }//end of the render Method
    /**
     * getX Method - Overrides the getX Method for the Rectangle2D Class, and gets the horizontal
     * position of the Ball Class.
     * @return Double - Returns the horizontal position Rectangle2D in the form of a double
     */
    @Override
    public double getX() {
        return this.xPos;

    }//end of the getXPosition Method
    /**
     * getY Method - Overrides the getY Method for the Rectangle2D Class, and gets the vertical
     * position of the Ball Class.
     * @return Double - Returns the vertical position of the Rectangle2D in the form of a double
     */
    @Override
    public double getY() {
        return this.yPos;

    }//end of the getYPosition Method
    /**
     * getWidth Method - Gets the width dimension of the rectangle
     * @return Double - Returns the width dimension of the Rectangle2D in the form of a double
     */
    @Override
    public double getWidth() {
        return this.width;

    }//end of the getWidth Method
    /**
     * getHeight Method - Overrides the getHeight Method for the Rectangle2D Class, and respond to
     * the height of the Ball Class.
     * @return Double - Returns the height dimension of the Rectangle2D in the form of a double
     */
    @Override
    public double getHeight() {
        return this.height;

    }//end of the getHeight Method
    /**
     * getCollisionWithPlayerPaddle Method - Determines whether or not there is an intersection
     * of the ball rectangular bounds and the player paddle rectangular bounds
     * @return Boolean - Returns true, if there is an intersection of the ball bounds and the
     * player paddle.  Otherwise, it returns false.
     */
    public boolean getCollisionWithPlayerPaddle() {
        return GamePanel.player.getBounds2D().intersects(getBounds2D());

    }//end of the getCollisionWithPlayerPaddle Method
    /**
     * getCollisionWithAIPaddle Method - Determines whether or not there is an intersection of
     * the ball rectangular bounds and the AI paddle rectangular bounds
     * @return Boolean - Returns true, if there is an intersection of the ball bounds and the
     * AI paddle.  Otherwise, it returns false.
     */
    public boolean getCollisionWithAIPaddle() {
        return GamePanel.computer.getBounds2D().intersects(getBounds2D());

    }//end of the getCollisionWithPlayerPaddle Method
    /**
     * serve Method - Serves the ball toward the specified side.
     * @param - an integer of the right side, if one; the left side, if negative one
     * @return Void
     */
    public void serve(int side) {
        //Set the horizontal and vertical position
        int rand = (int) Math.round(Math.random());
        xPos = (side == 1) ? (GamePanel.player.xPos + GamePanel.player.paddleWidth) :
                (GamePanel.computer.xPos - (width + height));

        yPos = (GamePanel.SCREEN_HEIGHT - (width + height)) * rand;

        //Calculate the out-angle, higher/lower on the y-axis equals steeper angle
        double angle = (0.1 * Math.PI * (1 - (2 * rand)));

        //Set the velocity direction and magnitude
        xVel = (height * speed * Math.cos(angle));

    }//end of the serve Method
    /**
     * setRect Method - Sets the location at the (X, Y) axis with the size of the width and
     * height of the Rectangle2D.
     * @param - a double of the X position
     * @param - a double of the Y position
     * @param - a double of the width dimension
     * @param - a double of the height dimension
     * @return Void
     */
    @Override
    public void setRect(double x, double y, double w, double h) {
        this.xPos = x;
        this.yPos = y;
        this.width = w;
        this.height = h;

    }//end of the setRect Method
    /**
     * setRect Method - Overrides the setRect Method of the Rectangle2D Class, and responds to
     * the Ball Class
     * @param - the two dimension rectangle
     * @return Void
     */
    @Override
    public void setRect(Rectangle2D rectangle) {
        this.xPos   = (double) rectangle.getX();
        this.yPos   = (double) rectangle.getY();
        this.width  = (double) rectangle.getWidth();
        this.height = (double) rectangle.getHeight();

    }//end of the setRect Method
    /**
     * outcode Method -
     * @param - a double of the horizontal position
     * @param - a double of the vertical position
     * @return Int - Returns the out code in the form of an Integer
     */
    @Override
    public int outcode(double x, double y) {
        int out = 0;
        if (this.width <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;

        } else if (x < this.xPos) {
            out |= OUT_LEFT;

        } else if (x > this.xPos + (double) this.width) {
            out |= OUT_RIGHT;

        } else if (this.height <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;

        } else if (y < this.yPos) {
            out |= OUT_TOP;

        } else if (y > this.yPos + (double) this.height) {
            out |= OUT_BOTTOM;

        }
        return out;

    }//end of the outcode Method
    /**
     * getBounds2D Method -
     * @return Rectangle2D
     */
    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(xPos, yPos, width, height);

    }//end of the getBounds2D Method
    /**
     * getMidpoint Method -
     * @return Line2D
     */
    public Line2D getMidpoint() {
        return new Line2D.Double(midpoint.getX1(), midpoint.getY1(), midpoint.getX2(), midpoint.getY2());

    }//end of the getMidpoint Method
    /**
     * createIntersection Method - Creates an intersection of the Rectangle2D
     * @param - the specified 2 dimensional rectangle to be intersected
     * @return Rectangle2D - Returns the destination Rectangle2D
     */
    @Override
    public Rectangle2D createIntersection(Rectangle2D rect) {
        Rectangle2D dest;
        if (rect instanceof Float) {
            dest = new Rectangle2D.Float();

        } else {
            dest = new Rectangle2D.Double();

        }
        Rectangle2D.intersect(this, rect, dest);
        return dest;

    }//end of the createIntersection Method
    /**
     * createUnion Method -
     * @param - a 2 dimensional rectangle
     * @return Rectangle2D -
     */
    @Override
    public Rectangle2D createUnion(Rectangle2D rect) {
        Rectangle2D dest;
        if (rect instanceof Float) {
            dest = new Rectangle2D.Float();

        } else {
            dest = new Rectangle2D.Double();

        }
        Rectangle2D.union(this, rect, dest);
        return dest;

    }//end of the createUnion Method
    /**
     * intersectsLine Method - Determines if the specified line segment intersects the interior of
     * the Rectangle2D for the Ball Class
     * @param - a double of the X coordinate of the start point of the specified line segment
     * @param - a double the Y coordinate of the start point of the specified line segment
     * @param - a double of the X coordinate of the end point of the specified line segment
     * @param - a double the Y coordinate of the end point of the specified line segment
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
     * @param - the specified 2 dimensional line segment
     * @return Boolean - Returns true, if the specified line segment intersects the Ball Class Rectangle2D;
     * otherwise, it returns false
     */
    @Override
    public boolean intersectsLine(Line2D line) {
        return intersectsLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());

    }//end of the intersectsLine Method
    /**
     * isEmpty Method - Overrides the isEmpty Method of the Rectangle2D Class, and responds to
     * the Ball Class
     * @return Boolean - Returns true, if the Rectangle2D is empty.  Otherwise, it returns false.
     */
    @Override
    public boolean isEmpty() {
        return (width <= 0.0) || (height<= 0.0);

    }//end of the isEmpty Method
    /**
     * toString Method - Overrides the toString Method of the Abstract Object Class, and redefines
     * it for the Ball Class
     * @return String - Return a String representation of the Ball Class
     */
    @Override
    public String toString() {
        return getClass().getName()
                + "[x = "+ xPos  + " "
                + ".y = "+ yPos  + " "
                + ".w = "+ width + " "
                + ".h = "+ height
                + "]";

    }//end of the toString Method
}//end of the Ball Class
