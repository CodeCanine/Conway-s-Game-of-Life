package life;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class LifePanel extends JPanel {

    boolean[][] cells; //different pointing to nothing null
    double width;
    double height;

    public LifePanel(boolean[][] in) {
        cells = in;
    }

    public void setCells(boolean[][] newCells) {
        cells = newCells;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = (double) this.getWidth() / cells[0].length;
        height = (double) this.getHeight() / cells.length;

        //draw cells
        g.setColor(Color.BLACK);
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                if (cells[row][column] == true) {
                    g.fillRect((int) Math.round(column * width), (int) Math.round(row * height), (int) width + 1, (int) height + 1);
                }
            }
        }

        //draw grid
        g.setColor(Color.BLACK);
        for (int x = 0; x < cells[0].length + 1; x++) {
            g.drawLine((int) Math.round(x * width), 0, (int) Math.round(x * width), this.getHeight());
        }
        for (int y = 0; y < cells.length + 1; y++) {
            g.drawLine(0, (int) Math.round(y * height), this.getWidth(), (int) Math.round(y * height));
        }
    }

}
