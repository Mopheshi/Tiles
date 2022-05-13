package tiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ndachimya Edward
 */
public class Gameplay extends JPanel implements ActionListener, KeyListener {

    private boolean play = false;
    private double score = 0.000;
    private int totalTiles = 32;
    private final Timer timer;
    private final int delay = 5;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private MapGenerator mp;

    @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
    public Gameplay() {
        mp = new MapGenerator(4, 8);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);        // background
        g.fillRect(1, 1, 692, 592);

        mp.draw((Graphics2D) g);        // drawing tiles

        g.setColor(Color.yellow);       // borders
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(681, 0, 3, 592);

        g.setColor(Color.white);        // score board
        g.setFont(new Font("Centurty Gothic", Font.ITALIC, 25));
        g.drawString(String.valueOf(score), 590, 30);

        g.setColor(Color.green.darker());        // paddle
        g.fillRoundRect(playerX, 550, 100, 8, 20, 20);

        g.setColor(Color.yellow.brighter());        // ball
        g.fillOval(ballPosX, ballPosY, 20, 20);

        if (totalTiles <= 0) {
            g.setColor(Color.green.brighter());
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("You Won... Nice play!!!", 180, 300);

            g.setColor(Color.white);
            g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
            g.drawString("Press ENTER/SPACE to replay", 180, 350);
        }

        if (ballPosY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            if (score <= 50) {
                g.setColor(Color.red);
                g.setFont(new Font("Century Gothic", Font.BOLD, 30));
                g.drawString("Game Over... Mumu man!!!", 180, 300);
            } else if (score > 50 && score < 100) {
                g.setColor(Color.yellow);
                g.setFont(new Font("Consolas", Font.BOLD, 30));
                g.drawString("Game Over... Mumu!", 180, 300);
            } else if (score == 100) {
                g.setColor(Color.green.brighter());
                g.setFont(new Font("Monospaced", Font.BOLD, 30));
                g.drawString("You Won... Nice play!!!", 180, 300);
            }
            g.setColor(Color.white);
            g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
            g.drawString("Press ENTER/SPACE to replay", 180, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }

            A:
            for (int i = 0; i < mp.map.length; i++) {
                for (int j = 0; j < mp.map[0].length; j++) {
                    if (mp.map[i][j] > 0) {
                        int tileX = j * mp.tileWidth + 80;
                        int tileY = i * mp.tileHeight + 50;
                        int tilewidth = mp.tileWidth;
                        int tileheight = mp.tileHeight;

                        Rectangle rectangle = new Rectangle(tileX, tileY, tilewidth, tileheight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle tileRect = rectangle;

                        if (ballRect.intersects(tileRect)) {
                            mp.setTileValue(0, i, j);
                            totalTiles--;
                            score += 3.125;

                            if ((ballPosX + 19 <= tileRect.x) || (ballPosX + 1 >= tileRect.x + tileRect.width)) {
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballPosX += ballXDir;
            ballPosY += ballYDir;

            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_SPACE)) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0.000;
                totalTiles = 32;
                mp = new MapGenerator(4, 8);

                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
