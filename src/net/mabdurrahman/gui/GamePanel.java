package net.mabdurrahman.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * The GamePanel Class
 * @author:  MAbdurrahman
 * @date:  12 October 2017
 */
public class GamePanel extends JPanel {
    //Instance Variables
    private static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int SCREEN_WIDTH = (int) SCREEN.getWidth();
    public static final int SCREEN_HEIGHT = (int) SCREEN.getHeight();
    public static final Font GAME_FONT = new Font("Montserrat Alternates", Font.PLAIN, 48);
    public static final Font BEGIN_FONT = new Font("Montserrat Alternates", Font.PLAIN, 60);
    public static final Color BLACK_COLOR = new Color(54, 54, 52);
    public static final Color WHITE_COLOR = new Color(255, 255, 212);
    public static final Color YELLOW_COLOR = new Color(240, 247, 20);

    public boolean isRunning = false;
    public JFrame frame;
    //  public Dimension gameSize;
    public static PlayerPaddle player;
    public static AIPaddle computer;
    public static Ball ball;
    protected KeyHandler keyHandler;

    private Timer timer;
    private final int delay;

    protected int playerScore;
    protected int AIScore;

    protected boolean isPlaying;

    /**
     * GameCanvas Constructor - Creates an instance of the GamePanel
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public GamePanel() {

        keyHandler = new KeyHandler(this);
        player = new PlayerPaddle(SCREEN_WIDTH - 20, 400);
        computer = new AIPaddle(0, 400);
        ball = new Ball((100 / 2), (450));
        playerScore = 0;
        AIScore = 0;
        isPlaying = false;
        delay = 8;

        addKeyListener(keyHandler);

        ActionListener actionListener = new ActionListener() {
            /**
             * actionPerformed Method - Redefines the actionPerformed Method for the ActionListener
             * Interface, and responds to the action events that takes place each time the Timer
             * fires.
             * @param - the action event of the Timer firing
             * @return Void
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (this != null) {
                    update();

                }
                repaint();

            }//end of the actionPerformed Method of actionListener
        };//end of the ActionListener Class

        timer = new Timer(delay, actionListener);

        /** Anonymous MouseListener */
        addMouseListener(new MouseAdapter() {
            /**
             * mousePressed Method - Redefines the mousePressed Method of the MouseListener
             * Interface, and responds to the mouse being pressed in the Canvas to begin the
             * game.
             * @param - the event of pressing the mouse
             * @return Void
             */
            @Override
            public void mousePressed(MouseEvent me) {
                requestFocus();

            }//end of the mousePressed Method for the Anonymous MouseListener
        });//end of the Anonymous MouseListener

        /** Anonymous FocusListener */
        addFocusListener(new FocusListener() {
            /**
             * focusGained Method - Redefines the focusGained Method of the FocusListener
             * Interface, and responds to the mousePressed Method of requesting focus which
             * starts the thread and renders the Canvas.
             * @param - the event of gaining focus
             * @return Void
             */
            @Override
            public void focusGained(FocusEvent fe) {
                timer.start();
                isPlaying = true;
                repaint();

            }//end of the focusGained Method
            /**
             * focusLost Method - Redefines the focusLost Method of the FocusListener Interface, and
             * responds to stopping the thread at the end of the game
             * @param - the event of loosing focus
             * @return Void
             */
            @Override
            @SuppressWarnings("CallToThreadStopSuspendOrResumeManager")
            public void focusLost(FocusEvent fe) {
                timer.stop();
                isPlaying = false;
                repaint();

            }//end of the focusLost Method
        });

    }//end of the GamePanel Constructor
    /**
     * paint Method - Overrides the paint method of Abstract JPanel Class, and paints the ball,
     * paddle, bricks, and score
     * @param - the graphic context
     * @return Void
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        /**Draw the center dotted line */
        g2d.setColor(WHITE_COLOR);
        g2d.setStroke(new BasicStroke(16, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        int width1 = (GamePanel.SCREEN_WIDTH / 2);
        int height1 = 0;
        int height2 = 30;
        while (height1 < SCREEN_HEIGHT) {
            g2d.drawLine(width1, height1, width1, height2);
            height1 += 60;
            height2 += 60;
        }

        if (hasFocus()) {
            player.render(g2d);
            computer.render(g2d);
            ball.render(g2d);

            /**Draw the game score*/
            g2d.setColor(WHITE_COLOR);
            g2d.setFont(GAME_FONT);
            g2d.drawString(""+AIScore, (getWidth()/2 - 130), 50);
            g2d.drawString(""+playerScore, (getWidth()/2 + 100), 50);

        } else {
            player.render(g2d);
            computer.render(g2d);
            ball.render(g2d);

            /**Draw the game score*/
            g2d.setColor(WHITE_COLOR);
            g2d.setFont(GAME_FONT);
            g2d.drawString(""+AIScore, (getWidth()/2 - 130), 50);
            g2d.drawString(""+playerScore, (getWidth()/2 + 100), 50);

            g2d.setColor(YELLOW_COLOR);
            g2d.setFont(GAME_FONT);
            g2d.drawString("CLICK TO BEGIN", ((getWidth() / 2) - 190), (getHeight() / 2));
        }
    }//end of the paint Method
    /**
     * update Method -
     * @return Void
     */
    public void update() {
        player.update(this);
        computer.update(this);
        ball.update(this);

        repaint();
    }//end of the update Method
    /**
     * GameOverDialog Class - The dialog for the game over
     */
    class GameOverDialog extends JDialog implements ActionListener {
        //Instance Variables
        private final JTextArea textArea;
        private final JButton okayButton;
        private final JPanel textPanel;
        private final JPanel buttonPanel;

        /**
         * GameOverDialog Constructor - Creates an instance of the GameOverDialog
         * @param - the parent frame
         * @param - the string of the title
         * @param - the boolean of modal
         */
        @SuppressWarnings("LeakingThisInConstructor")
        public GameOverDialog(JFrame frame, String title, Boolean modal) {
            super(frame, title, modal);

            /**The following 2 lines of code create a null icon for the JDialog*/
            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(icon);

            setBounds(500, 250, 450, 250);

            StringBuilder text = new StringBuilder();
            text.append("G A M E  O V E R!");

            textArea = new JTextArea(1, 1);
            textArea.setFont(GAME_FONT);
            textArea.setText(text.toString());
            textArea.setEditable(false);

            okayButton = new JButton("  OK  ");
            okayButton.addActionListener(this);

            addWindowListener(new WindowAdapter() {
                /**
                 * windowClosing Method - Redefines the windowClosing Method of the WindowListener
                 * Interface, and responds to the closing of the JDialog
                 * @param - the window event
                 * @return Void
                 */
                @Override
                public void windowClosing(WindowEvent we) {
                    Window window = we.getWindow();
                    window.dispose();

                }//end of the windowClosing Method
            });

            textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            textPanel.add(textArea);
            buttonPanel.add(okayButton);

            getContentPane().add(textPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.CENTER);

        }//end of the GameOverDialog Constructor
        /**
         * actionPerformed Method - Overrides the actionPerformed Method of the ActionListener
         * Interface, and responds to action events of the JButtons of the JDialog
         * @param - the action events of the JButtons
         * @return Void
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == doGameOverDialog()) {
                this.setVisible(true);
            }
            if (ae.getSource() == okayButton) {
                this.dispose();
            }

        }//end of the actionPerformed Method
    }//end of the GameOverDialog Class
    /**
     * PlayAgainDialog Class -
     */
    class PlayAgainDialog extends JDialog implements ActionListener {
        //Instance Variables
        private final JTextArea textArea;
        private final JButton yesButton;
        private final JButton noButton;
        private final JPanel textPanel;
        private final JPanel buttonPanel;

        /**
         * PlayAgainDialog Constructor - Creates an instance of the PlayAgainDialog
         * @param - the parent frame
         * @param - the string of the title
         * @param - the boolean of the modal
         */
        @SuppressWarnings("LeakingThisInConstructor")
        public PlayAgainDialog(JFrame frame, String title, boolean modal) {
            super(frame, title, modal);

            /**The following 2 lines of code creates a null icon for the JDialog*/
            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(icon);

            setBounds(500, 250, 450, 250);

            StringBuilder text = new StringBuilder();
            text.append("Play Again?");

            textArea = new JTextArea(1, 1);
            textArea.setFont(GAME_FONT);
            textArea.setText(text.toString());
            textArea.setEditable(false);

            yesButton = new JButton("Yes");
            yesButton.addActionListener(this);

            noButton = new JButton("No");
            noButton.addActionListener(this);

            addWindowListener(new WindowAdapter() {
                /**
                 * windowClosing Method - Redefines the windowClosing Method of the WindowListener
                 * Interface, and responds to the closing of the JDialog
                 * @param - the event of window closing
                 * @return Void
                 */
                @Override
                public void windowClosing(WindowEvent we) {
                    Window window = we.getWindow();
                    window.dispose();
                }
            });

            textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            textPanel.add(textArea);
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);

            getContentPane().add(textPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.CENTER);

        }//end of the PlayAgainDialog Constructor
        /**
         * actionPerformed Method - Redefines the actionPerformed Method for the ActionListener
         * Interface, and responds to the action events of the JButtons of the JDialog
         * @param - the action events of the JButtons
         * @return Void
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == doPlayAgain()) {
                this.setVisible(true);
            }
            if (ae.getSource() == yesButton) {
                doNewGame();
            }
            if (ae.getSource() == noButton) {
                doEndGame();
                this.dispose();
            }
        }//end of the actionPerformed Method
    }//end of the PlayAgainDialog Class
    /**
     * doGameOverDialog Method -
     * @return JDialog -
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private JDialog doGameOverDialog() {
        return new GameOverDialog(null, null, true);

    }//end of the doGameOverDialog Method
    /**
     * doPlayAgain Method -
     * @return JDialog -
     */
    public JDialog doPlayAgain() {
        return new PlayAgainDialog(null, null, true);

    }//end of the doPlayAgain Method
    /**
     * doNewGame Method -
     * @return Void
     */
    public void doNewGame() {
        /* Set the Nimbus look and feel */

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /** Create and display the Game */
        java.awt.EventQueue.invokeLater(() -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();
            @SuppressWarnings("LocalVariableHidesMemberVariable")
            JFrame frame = new JFrame("P O N G");
            frame.setBounds(0, 0, screenWidth, screenHeight);
            GamePanel game = new GamePanel();
            game.setBackground(BLACK_COLOR);
            frame.add(game);
            frame.setResizable(false);
            //Image icon = Toolkit.getDefaultToolkit().getImage(GamePanel.class.
                   // getResource("/img/tennisBall.png"));
           // frame.setIconImage(icon);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }//end of the doNewGame Method
    /**
     * doEndGame Method -
     * @return Void
     */
    public void doEndGame() {

    }//end of the doEndGame Method
    /**
     * main Method - Contains the command line arguments
     * @param - the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /** Create and display the Game */
        java.awt.EventQueue.invokeLater(() -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();
            JFrame frame = new JFrame("P O N G");
            frame.setBounds(0, 0, screenWidth, screenHeight);
            GamePanel game = new GamePanel();
            game.setBackground(BLACK_COLOR);
            frame.add(game);
            frame.setResizable(false);
           // Image icon = Toolkit.getDefaultToolkit().getImage(GamePanel.class.
                   // getResource("/img/tennisBall.png"));
            //frame.setIconImage(icon);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }//end of the main Method
}//end of the GamePanel Class

