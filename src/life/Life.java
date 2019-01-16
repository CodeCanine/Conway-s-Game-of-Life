package life;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Life implements MouseListener, ActionListener, Runnable {

    //variables and objects
    boolean[][] cells = new boolean[250][250];
    JFrame frame = new JFrame("Life Simulation");
    LifePanel panel = new LifePanel(cells);
    LifePanel panel1 = new LifePanel(cells);
    Container south = new Container();
    JButton step = new JButton("Step");
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    JButton faster = new JButton("Faster");
    JButton slower = new JButton("Slower");
    JButton randomize = new JButton("Randomize");
    JButton restart = new JButton("Restart");
    double n = 1;
    double sebesseg = 1;
    boolean running = false;

    //constructor
    public Life() {
        frame.setSize(1200, 1200);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        panel.addMouseListener(this);
        //south container
        south.setLayout(new GridLayout(1, 7));

        south.add(step);
        step.addActionListener(this);
        south.add(start);
        start.addActionListener(this);
        south.add(stop);
        stop.addActionListener(this);
        south.add(faster);
        faster.addActionListener(this);
        south.add(slower);
        slower.addActionListener(this);
        south.add(randomize);
        randomize.addActionListener(this);
        south.add(restart);
        restart.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Life();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    //
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());
        double width = (double) panel.getWidth() / cells[0].length;
        double height = (double) panel.getHeight() / cells.length;
        int column = Math.min(cells[0].length - 1, (int) (e.getX() / width));
        int row = Math.min(cells.length - 1, (int) (e.getY() / height));
//        System.out.println(column + "," + row);
//        System.out.println(frame.getContentPane().getSize());
        cells[row][column] = !cells[row][column];
        frame.repaint();
    }

    public void randomize() {
        Random rnd = new Random();
        Random rnd2 = new Random();
        int db = 0;
        do {
            double width = (double) panel.getWidth() / cells[0].length;
            double height = (double) panel.getHeight() / cells.length;

            double r = rnd.nextInt((int) (280 * width)) + 1;
            double r1 = rnd2.nextInt((int) (280 * height)) + 1;
            int column = Math.min(cells[0].length - 1, (int) (r / width));
            int row = Math.min(cells.length - 1, (int) (r1 / height));
            //System.out.println(column + "," + row);

            cells[row][column] = !cells[row][column];
            frame.repaint();
            db += 1;
        } while (db != 2800);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(slower)) {
            running = true;
            n = n * 2;
            sebesseg = sebesseg / 2;
            //System.out.println(sebesseg);
            //System.out.println(n);//4^m gyök n
        }
        slower.setText("Speed=" + sebesseg + " slower");
        if (e.getSource().equals(faster)) {
            running = true;
            n = n / 2;
            sebesseg = sebesseg * 2;
            //System.out.println(sebesseg);
            //System.out.println(n);//n*4^m
        }
        faster.setText("Speed=" + sebesseg + " faster");
        if (e.getSource().equals(step)) {
            step();
            running = false;
        }
        if (e.getSource().equals(start)) {
            if (running == false) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }
        if (e.getSource().equals(stop)) {
            running = false;
        }
        if (e.getSource().equals(randomize)) {
            running = false;
            //Thread t = new Thread(this);
            randomize();
            //t.start();
        }
        if (e.getSource().equals(restart)) {
            frame.dispose();
            new Life();
        }

    }

    @Override
    public void run() {
        while (running) {
            step();
            try {
                Thread.sleep((long) (500 * n));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
     * row-1,col-1    row-1,col    row-1,col+1
     * row,col-1      row,col      row,col+1   
     * row+1,col-1    row+1,col    row+1,col+1
     */
    public void step() {
        boolean[][] nextCells = new boolean[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                int neighbourCount = 0;
                //up left
                if (row > 0 && col > 0 && cells[row - 1][col - 1] == true) {
                    neighbourCount++;
                }
                //up
                if (row > 0 && cells[row - 1][col] == true) {
                    neighbourCount++;
                }
                //up right
                if (row > 0 && col < cells[0].length - 1 && cells[row - 1][col + 1] == true) {
                    neighbourCount++;
                }
                //left
                if (col > 0 && cells[row][col - 1] == true) {
                    neighbourCount++;
                }
                //right
                if (col < cells[0].length - 1 && cells[row][col + 1] == true) {
                    neighbourCount++;
                }
                //down left
                if (row < cells.length - 1 && col > 0 && cells[row + 1][col - 1] == true) {
                    neighbourCount++;
                }
                //down
                if (row < cells.length - 1 && cells[row + 1][col] == true) {
                    neighbourCount++;
                }
                //down right
                if (row < cells.length - 1 && col < cells[0].length - 1 && cells[row + 1][col + 1] == true) {
                    neighbourCount++;
                }
                //Rules of life
                if (cells[row][col] == true) {//alive
                    if (neighbourCount == 2 || neighbourCount == 3) {
                        nextCells[row][col] = true;//alive next time
                    } else {
                        nextCells[row][col] = false;//dead next time
                    }
                } else {//dead
                    if (neighbourCount == 3) {
                        nextCells[row][col] = true;//alive next time
                    } else {
                        nextCells[row][col] = false;//dead next time
                    }
                }
            }
        }
        cells = nextCells;
        panel.setCells(nextCells);
        frame.repaint();
    }
}
