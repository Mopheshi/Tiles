package tiles;

import javax.swing.JFrame;

/**
 *
 * @author Ndachimya Edward
 */
public class Tiles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Gameplay gamePlay = new Gameplay();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(10, 10, 700, 610);
        frame.setTitle("Morpheus Tiles");
        frame.setResizable(false);
        frame.add(gamePlay);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

}
