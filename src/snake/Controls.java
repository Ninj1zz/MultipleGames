package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Controls implements KeyListener
{
    ArrayList<Player> playerList;

    public Controls(ArrayList<Player> playerList)
    {
        this.playerList = playerList;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        for(int i = 0; i < playerList.size(); i++)
        {
            Player playerTmp = playerList.get(i);

            if (playerTmp.getDirection().getX() == 0)
            {
                if (key == playerTmp.getLEFT())
                {
                    playerTmp.setNextDirection(new Point(-1, 0));
                }
                else if (key == playerTmp.getRIGHT())
                {
                    playerTmp.setNextDirection(new Point(1, 0));
                }
            }
            else if (playerTmp.getDirection().getY() == 0)
            {
                if (key == playerTmp.getUP())
                {
                    playerTmp.setNextDirection(new Point(0, -1));
                }
                else if (key == playerTmp.getDOWN())
                {
                    playerTmp.setNextDirection(new Point(0, 1));
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}