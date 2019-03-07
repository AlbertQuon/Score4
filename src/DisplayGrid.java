/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Author Garvin Hui
class DisplayGrid {

    private JFrame frame;
    private int maxX,maxY, GridToScreenRatio;
    private int[][][] world;

    DisplayGrid(int[][][] w) {
        this.world = w;

        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / ((world.length+2)*((int)Math.sqrt(world.length)));  //ratio to fit in screen as square map

        System.out.println("Map size: "+world.length+" by "+world[0].length + " by " + world[0][0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);

        this.frame = new JFrame("Map of World");

        GridAreaPanel worldPanel = new GridAreaPanel();
        worldPanel.addMouseListener(new MouseClick());//Creating the mouse clicking object

        frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }


    public void refresh() {
        frame.repaint();
    }



    class GridAreaPanel extends JPanel {
        public void paintComponent(Graphics g) {
            //super.repaint();

            setDoubleBuffered(true);
            g.setColor(Color.BLACK);
            int counter = 0;
            int row = 0;
            int rowY;
            int rowX;

            for (int i = 0; i < world[0][0].length; i = i + 1) {
                for (int j = 0; j < world[0].length; j = j + 1) {
                    for (int k = 0; k < world.length; k++) {
                        rowY = j * GridToScreenRatio + GridToScreenRatio;
                        rowX = k * GridToScreenRatio + counter * (world.length - 1) * 2 * GridToScreenRatio + 2;
                        if (world[i][j][k] == 1)    //This block can be changed to match character-color pairs
                            g.setColor(Color.RED);
                        else if (world[i][j][k] == -1)
                            g.setColor(Color.YELLOW);
                        else
                            g.setColor(Color.WHITE);
                        g.fillRect(rowX, rowY + row*(GridToScreenRatio*(world.length+1)), GridToScreenRatio, GridToScreenRatio);
                        g.setColor(Color.BLACK);
                        g.drawRect(rowX, rowY + row*(GridToScreenRatio*(world.length+1)), GridToScreenRatio, GridToScreenRatio);
                        g.drawString("Level " + (i + 1), 2 * GridToScreenRatio + counter * (world.length - 1) * 2 * GridToScreenRatio + 2, GridToScreenRatio/2+ row*(GridToScreenRatio*(world.length+1)));
                    }
                }
                if (counter > ((int)Math.sqrt(world.length)-1)) {
                    counter = -1;
                    row += 1;
                }
                counter++;
            }
        }
    }//end of GridAreaPanel

    //This class is used to take in mouse input and also plays the player move onto the board
    //Author Garvin Hui
    static class MouseClick implements MouseListener {
        int x;
        int y;
        static int[][][] world;
        int maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        int GridToScreenRatio = maxY / ((world.length+2)*((int)Math.sqrt(world.length)));
        static int[] coordinates = new int[2];

        /**
         * This method waits for a user mouse click then checks if it's in a valid spot, then places the move onto the board
         * Author Garvin Hui
         * @param click This refers to the mouse click event.
         */
        @Override
        public void mouseClicked(MouseEvent click) {
            x = click.getX();
            y = click.getY();
            if (Score4.PlayerTurn.getPlayerTurn() == 1) {
                int counter = 0;
                int row = 0;
                int rowX;
                int rowY;
                for (int i = 0; i < world[0][0].length; i++) {
                    for (int j = 0; j < world[0].length; j++) {
                        for (int k = 0; k < world.length; k++) {
                            rowY = j * GridToScreenRatio + GridToScreenRatio;
                            rowX = k * GridToScreenRatio + counter * (world.length - 1) * 2 * GridToScreenRatio + 2;
                            if ((x > rowX) && (x < rowX + GridToScreenRatio)) {
                                if ((y > rowY + row*(GridToScreenRatio*(world.length+1))) && (y < rowY + row*(GridToScreenRatio*(world.length+1)) + GridToScreenRatio)) {
                                    coordinates[0] = k;
                                    coordinates[1] = world[0].length - j-1;
                                    Score4.boardUpdate(world, coordinates, 1);
                                    Score4.setPlaced(true);
                                }
                            }
                        }
                    }
                    if (counter > ((int) Math.sqrt(world.length)) - 1) {
                        counter = -1;
                        row++;
                    }
                    counter++;
                }
            }
        }
        public void mousePressed(MouseEvent e) {

        }
        public void mouseReleased(MouseEvent e) {

        }
        public void mouseEntered(MouseEvent e) {

        }
        public void mouseExited(MouseEvent e) {

        }

        /**
         * Method takes in board from other class
         * @Author Garvin Hui
         * @param board Current game board
         */
        static void setWorld(int[][][] board) {
            world = board;
        }

    }

} //end of DisplayGrid