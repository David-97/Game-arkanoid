import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnviromentGenerator {
  public int bricks[][];
  public int brickWidth;
  public int bricksHeight;

  public EnviromentGenerator(int rows, int columns) {
    bricks = new int [rows][columns];
    for (int i = 0;  i < bricks.length; i ++) {
      for (int j = 0; j < bricks[0].length; j++) {
        bricks[i][j] = 1;
      }
    }

    brickWidth = 1050 / columns;
    bricksHeight = 400 / rows;
  }

  public void draw(Graphics2D graphics) {
    List<Color> colors = new ArrayList<>();
    colors.add(Color.red);
    colors.add(Color.blue);
    colors.add(Color.pink);
    colors.add(Color.yellow);
    colors.add(Color.orange);
    colors.add(Color.magenta);
    for (int i = 0;  i < bricks.length; i ++) {
      for (int j = 0; j < bricks[0].length; j++) {
        if(bricks[i][j] > 0) {
          graphics.setColor(colors.get(2));
          graphics.fillRect(j * brickWidth + 80, i * bricksHeight + 50, brickWidth, bricksHeight);

          graphics.setStroke(new BasicStroke(3));
          graphics.setColor(Color.black);
          graphics.drawRect(j * brickWidth + 80, i * bricksHeight + 50, brickWidth, bricksHeight);
        }
      }
    }
  }

  public void setBrickStatus(int status, int row, int column) {
    bricks[row][column] = status;
  }
}
