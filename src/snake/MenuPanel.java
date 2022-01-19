package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel
{
    private Manager manager;
    private JButton onePlayerBtn, twoPlayerBtn, exitBtn;
    private JCheckBox toggleWallCb;
    private JPanel northPanel, southPanel, centerPanel;
    private JLabel controls;
    
    public MenuPanel(Manager manager)
    {
        this.manager = manager;

        setLayout(new BorderLayout());

        onePlayerBtn = new JButton("1 player");
        twoPlayerBtn = new JButton("2 player");
        exitBtn = new JButton("EXIT");
        toggleWallCb = new JCheckBox("Enable Wall");
        controls = new JLabel("<html> WASD - Player 1 <br/> Arrow Keys - Player 2 </html>");

        northPanel = new JPanel();
        southPanel = new JPanel();
        centerPanel = new JPanel();

        add(northPanel, BorderLayout.NORTH);
        northPanel.add(onePlayerBtn);
        northPanel.add(twoPlayerBtn);
        northPanel.add(toggleWallCb);

        add(southPanel, BorderLayout.SOUTH);
        southPanel.add(exitBtn);
        
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(controls);

        initButtons();
    }

    public boolean isWallEnabled()
    {
        return toggleWallCb.isSelected();
    }

    public void initButtons()
    {
        onePlayerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                manager.startRound(new Point(25, 25), 1);
            }
        });
        twoPlayerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                manager.startRound(new Point(25, 25), 2);
            }
        });
        exitBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(1);
            }
        });
    }
}