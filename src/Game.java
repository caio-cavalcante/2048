import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener{

    Board game = new Board();
    static Game newGame = new Game();
    static JFrame frame = new JFrame("2048");
    static Color green;
    String gameBoard = game.toString();

    public static void setGUI() {
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addKeyListener(newGame);
        frame.getContentPane().add(newGame);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
            game.up();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        } else if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
            game.down();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        } else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.left();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        } else if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.right();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            game = new Board();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game = new Board();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawString("2048", 250, 20);
        g2.drawString("Score: " + game.getScore(), 200 - 4 * String.valueOf(game.getScore()).length(), 40);
        g2.drawString("Highest Tile: " + game.getHighTile(), 280 - 4 * String.valueOf(game.getHighTile()).length(), 40);
        g2.drawString("Press SPACE to play", 210, 315);
        g2.drawString("Use the arrow keys to move.", 180, 335);

        if (game.blackOut()) {
            g2.drawString("Press ENTER to restart.", 200, 355);
        }

        g2.setColor(Color.GRAY);
        g2.fillRect(140, 50, 250, 250);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                drawTiles(g, game.board[i][j], j * 60 + 150, i * 60 + 60);
            }
        }

        if (game.gameOver()) {
            g2.setColor(Color.GRAY);
            g2.fillRect(140, 50, 250, 250);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    g2.setColor(Color.RED);
                    g2.fillRoundRect(j * 60 + 150, i * 60 + 60, 50, 50, 5, 5);
                    g2.setColor(Color.BLACK);
                    g.drawString("GAME", j * 60 + 160, i * 60 + 75);
                    g.drawString("OVER", j * 60 + 160, i * 60 + 95);
                }
            }
        }
    }

    public void drawTiles(Graphics g, Tile tile, int x, int y) {
        int tileValue = tile.getValue();
        int length = String.valueOf(tileValue).length();

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRoundRect(x, y, 50, 50, 5, 5);
        g2.setColor(Color.BLACK);
        if (tileValue > 0) {
            g2.setColor(tile.getColor());
            g2.fillRoundRect(x, y, 50, 50, 5, 5);
            g2.setColor(Color.BLACK);
            g.drawString(" " + tileValue, x + 25 - length * 3, y + 25);
        }
    }

    public static void main(String[] args) {setGUI();}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
