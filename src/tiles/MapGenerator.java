package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Ndachimya Edward
 */
public class MapGenerator {

    public int[][] map;
    public int tileWidth, tileHeight;

    public MapGenerator(int rows, int columns) {
        map = new int[rows][columns];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        tileWidth = 540 / columns;
        tileHeight = 150 / rows;
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g2d.setColor(Color.white);
                    g2d.fillRoundRect(j * tileWidth + 80, i * tileHeight + 50, tileWidth, tileHeight, 10, 10);

                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(Color.black);
                    g2d.drawRoundRect(j * tileWidth + 80, i * tileHeight + 50, tileWidth, tileHeight, 10, 10);
                }
            }
        }
    }

    public void setTileValue(int value, int row, int column) {
        map[row][column] = value;
    }
}
