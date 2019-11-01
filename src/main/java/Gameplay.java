import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

  private boolean play = false;
  private int score = 0;
  private int totalBricks;
  private int delay = 5;
  private Timer timer;
  private int playerX = 600;
  private int ballX = 500;
  private int ballY = 500;
  private int ballXDir = -1;
  private int ballYDir = -2;

  private EnviromentGenerator enviromentGenerator;

  public Gameplay() {
    int[] size = getRandomSize();
    totalBricks = size[0] * size[1];
    enviromentGenerator = new EnviromentGenerator(size[0], size[1]);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
  }

  public void paint(Graphics graphics) {
    graphics.setColor(Color.black);
    graphics.fillRect(1, 1, 1192, 1192);

    enviromentGenerator.draw((Graphics2D) graphics);

    graphics.setColor(Color.red);
    graphics.setFont(new Font("serif", Font.BOLD, 24));
    graphics.drawString(score + " points", 50, 40);

    graphics.setColor(Color.yellow);
    graphics.fillRect(0, 0, 3, 1192);
    graphics.fillRect(0, 0, 1192, 3);
    graphics.fillRect(1191, 0, 3, 1192);
    graphics.fillRect(0, 1150, 1192, 3);

    graphics.setColor(Color.green);
    graphics.fillRect(playerX, 1130, 100, 10);

    graphics.setColor(Color.blue);
    graphics.fillOval(ballX, ballY, 20, 20);

    if (totalBricks <= 0) {
      play = false;
      ballYDir = 0;
      ballXDir = 0;
      graphics.setColor(Color.white);
      graphics.setFont(new Font("serif", Font.BOLD, 32));
      graphics.drawString("You Won, total points: " + score, 420, 400);

      graphics.setFont(new Font("serif", Font.BOLD, 24));
      graphics.drawString("Press ENTER to restart", 430, 480);
    }

    if (ballY > 1170) {
      play = false;
      ballYDir = 0;
      ballXDir = 0;
      graphics.setColor(Color.white);
      graphics.setFont(new Font("serif", Font.BOLD, 32));
      graphics.drawString("Game Over, total points: " + score, 400, 400);

      graphics.setFont(new Font("serif", Font.BOLD, 24));
      graphics.drawString("Press ENTER to restart", 450, 480);
    }

    graphics.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    timer.start();
    if (play) {
      if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 1130, 100, 8))) {
        ballYDir = -ballYDir;
      }

      loop:
      for (int i = 0; i < enviromentGenerator.bricks.length; i++) {
        for (int j = 0; j < enviromentGenerator.bricks[0].length; j++) {
          if (enviromentGenerator.bricks[i][j] > 0) {
            int brickX = j * enviromentGenerator.brickWidth + 80;
            int brickY = i * enviromentGenerator.bricksHeight + 50;
            int brickWidth = enviromentGenerator.brickWidth;
            int brickHeight = enviromentGenerator.bricksHeight;

            Rectangle bricksRectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            Rectangle ballRectangle = new Rectangle(ballX, ballY, 20, 20);

            if (ballRectangle.intersects(bricksRectangle)) {
              enviromentGenerator.setBrickStatus(0, i, j);
              totalBricks--;
              score += 10;

              if (ballX + 19 <= bricksRectangle.x || ballX + 1 >= bricksRectangle.x + bricksRectangle.width) {
                ballXDir = -ballXDir;
              } else {
                ballYDir = -ballYDir;
              }
              break loop;
            }
          }
        }
      }

      ballX += ballXDir;
      ballY += ballYDir;
      if (ballX < 0) {
        ballXDir = -ballXDir;
      }
      if (ballY < 0) {
        ballYDir = -ballYDir;
      }
      if (ballX > 1175) {
        ballXDir = -ballXDir;
      }
    }
    repaint();
  }

  public int[] getRandomSize() {
    Random random = new Random();
    return new int[]{random.nextInt(8) + 1, random.nextInt(8) + 1};
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (playerX >= 1100) {
        playerX = 1100;
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

    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!play) {
        play = true;
        ballX = 500;
        ballY = 500;
        ballXDir = -1;
        ballYDir = -2;
        playerX = 600;
        score = 0;
        totalBricks = 30;
        int[] size = getRandomSize();
        enviromentGenerator = new EnviromentGenerator(size[0], size[1]);

        repaint();
      }
    }
  }

  private void moveLeft() {
    play = true;
    playerX -= 20;
  }

  private void moveRight() {
    play = true;
    playerX += 20;
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }
}
