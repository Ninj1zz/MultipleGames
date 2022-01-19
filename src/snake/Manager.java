package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Manager extends JFrame
{
    private Point fieldSize;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Point> occupiedPositions = new ArrayList<>();
    private Items items = new Items();

    private CardLayout cardLayout;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;

    public Manager()
    {
        fieldSize = new Point(25, 25);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        //Measurement and alignment
        setSize(fieldSize.getX() * 25, fieldSize.getY() * 25);

        Insets insets = getInsets();
        int insetwidth = insets.left + insets.right;
        int insetheight = insets.top + insets.bottom;

        setSize((int) getSize().getWidth() + insetwidth,(int) getSize().getHeight() + insetheight);

        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        //Visual structure
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(players, items);

        add(menuPanel, "menu");
        add(gamePanel, "game");


        openMenu();
    }

    public void openMenu()
    {
        cardLayout.show(getContentPane(), "menu");
    }

    public void startRound(Point fieldSize, int playerCount)
    {
        cardLayout.show(getContentPane(), "game");
        gamePanel.startGraficUpdate();

        players.add(new Player(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_W, items, fieldSize, this, new Point(4, 0), menuPanel.isWallEnabled()));

        if(playerCount == 2)
        {
            players.add(new Player(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_UP, items, fieldSize, this, new Point(2, 0), menuPanel.isWallEnabled()));
        }
        requestFocus();
        addKeyListener(new Controls(players));

        startMovingSnakes();
    }

    public void startMovingSnakes()
    {
        for (int i = 0; i < players.size(); i++)
        {
            players.get(i).startSnake();
            items.spawn();
        }
    }

    public ArrayList<Point> getOccupiedPositions()
    {
        return occupiedPositions;
    }

    public synchronized void addOccupiedPositions(Point p)
    {
        occupiedPositions.add(p);
    }

    public synchronized void removeOccupiedPosition(Point p)
    {
        occupiedPositions.remove(p);
    }

    public static void main(String[] args)
    {
        new Manager();
    }

    public void playerKilled(Player player)
    {
        players.remove(player);

        //Check if 0 players are alive: End game
        if(players.size() == 0)
        {
            gamePanel.gameOver();
            openMenu();
        }
    }
}