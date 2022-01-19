package Rummikub;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class Rummikub extends JFrame {

	public JPanel gameField;
	public PlayerArea playerarea;
	public Button draw_stone;
	public Button hasPairs;
	public Button btnEndRound;
	public String username;
	public Image backgroundImage;
	public JTextPane CardsLeft;
	private ImageLabel computer_2;
	private JLabel timer_for_round_computer;
	private JTextField txtTest;
	public Player player;
	public Computer computer;
	public JTextField amount_pair_player;
	private SystemRequirements rq; 
	private JTextField paircounter_player;
	private JTextField timer_for_round_game;
	public JTextField amount_pair_com;
	private JTextField paircounter_com;
	private JTextField txtGame;
	private boolean GameFlag;
	private JTextPane textPane;
	public Button btnCheckforjoker;
	public Button btnMinimumThreeOrder;
	/**
	 * Launch the application.
	 */
	public static void pain(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rummikub frame = new Rummikub();      //could have leave it, but this is really good for test purposes just with the gui
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Rummikub() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 350);
		gameField = new JPanel();
		gameField.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(gameField);
		this.setSize(1400, 1050);
		this.getContentPane().setBackground(Color.DARK_GRAY);
		gameField.setLayout(null);
		//Players Panel
		this.setVisible(true);
		username = JOptionPane.showInputDialog(this, "Enter a player name");
		while(this.checkNameforValidity(username) == false) {
			username = JOptionPane.showInputDialog(this, "Enter a player name");	
		}
		playerarea = new PlayerArea("Player");
		gameField.add(playerarea);
		draw_stone = new Button("draw_stone");
		gameField.add(draw_stone);
		CardsLeft = new JTextPane();
		CardsLeft.setBackground(Color.LIGHT_GRAY);
		CardsLeft.setText("Stones Left(56/56)");
		CardsLeft.setBounds(1209, 717, 60, 34);
		CardsLeft.setEditable(false);
		gameField.add(CardsLeft);
		this.setResizable(false);
		
		computer_2= new ImageLabel("player.png", 0);
		computer_2.setBounds(603, 91, 151, 156);
		gameField.add(computer_2);
		
		JTextPane txtpnCom_2 = new JTextPane();
		txtpnCom_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtpnCom_2.setForeground(Color.RED);
		txtpnCom_2.setText("  COM");
		txtpnCom_2.setBounds(636, 60, 46, 20);
		txtpnCom_2.setEditable(false);
		gameField.add(txtpnCom_2);
		
		txtTest = new JTextField();
		txtTest.setForeground(new Color(173, 255, 47));
		txtTest.setHorizontalAlignment(SwingConstants.CENTER);
		txtTest.setFont(new Font("Verdana Pro Semibold", Font.BOLD | Font.ITALIC, 20));
		txtTest.setEditable(false);
		txtTest.setBounds(568, 665, 119, 34);
		txtTest.setText(username);
		gameField.add(txtTest);
		txtTest.setColumns(10);
		
		hasPairs = new Button("CheckForPairs");
		gameField.add(hasPairs);
		
		btnEndRound = new Button("End Round");
		gameField.add(btnEndRound);
		
		btnCheckforjoker = new Button("CheckForJoker");
		gameField.add(btnCheckforjoker);
		
		btnMinimumThreeOrder = new Button("CheckForMinThree");		
		gameField.add(btnMinimumThreeOrder);
		
		amount_pair_player = new JTextField();
		amount_pair_player.setHorizontalAlignment(SwingConstants.CENTER);
		amount_pair_player.setBounds(1250, 527, 113, 34);
		amount_pair_player.setEditable(false);
		amount_pair_player.setFont(new Font("Serif", Font.BOLD, 20));
		amount_pair_player.setForeground(Color.GREEN);
		gameField.add(amount_pair_player);
		amount_pair_player.setColumns(10);
		
		timer_for_round_computer = new JLabel("                   0.0");
		timer_for_round_computer.setHorizontalAlignment(SwingConstants.LEFT);
		timer_for_round_computer.setFont(new Font("Times New Roman", Font.BOLD, 15));
		timer_for_round_computer.setForeground(Color.WHITE);
		timer_for_round_computer.setBounds(604, 15, 170, 34);
		gameField.add(timer_for_round_computer);
		
		paircounter_player = new JTextField("PairCounter");
		paircounter_player.setEditable(false);
		paircounter_player.setBounds(1250, 495, 113, 34);
		paircounter_player.setFont(new Font("Serif", Font.BOLD, 20));
		paircounter_player.setForeground(Color.GREEN);
		gameField.add(paircounter_player);
		paircounter_player.setColumns(10);
		
		timer_for_round_game = new JTextField();
		timer_for_round_game.setHorizontalAlignment(SwingConstants.CENTER);
		timer_for_round_game.setForeground(new Color(0, 206, 209));
		timer_for_round_game.setFont(new Font("Serif", Font.BOLD, 20));
		timer_for_round_game.setEditable(false);
		timer_for_round_game.setBounds(1256, 60, 78, 33);
		gameField.add(timer_for_round_game);
		timer_for_round_game.setColumns(10);
		
		amount_pair_com = new JTextField();
		amount_pair_com.setHorizontalAlignment(SwingConstants.CENTER);
		amount_pair_com.setFont(new Font("Serif", Font.BOLD, 20));
		amount_pair_com.setForeground(Color.RED);
		amount_pair_com.setEditable(false);
		amount_pair_com.setColumns(10);
		amount_pair_com.setBounds(176, 91, 113, 34);
		gameField.add(amount_pair_com);
		
		paircounter_com = new JTextField("PairCounter");
		paircounter_com.setForeground(Color.RED);
		paircounter_com.setFont(new Font("Serif", Font.BOLD, 20));
		paircounter_com.setEditable(false);
		paircounter_com.setColumns(10);
		paircounter_com.setBounds(176, 60, 113, 33);
		gameField.add(paircounter_com);
		
		txtGame = new JTextField("GAME");
		txtGame.setFont(new Font("Serif", Font.BOLD, 15));
		txtGame.setForeground(new Color(0, 206, 209));
		txtGame.setEditable(false);
		txtGame.setBounds(1263, 34, 60, 28);
		gameField.add(txtGame);
		txtGame.setColumns(10);
		
		textPane = new JTextPane();
		textPane.setForeground(Color.RED);
		textPane.setFont(new Font("Serif", Font.BOLD, 14));
		textPane.setEditable(false);
		textPane.setBounds(719, 135, 162, 20);
		gameField.add(textPane);
		

		
		/*
		 * Player
		 */
		player = new Player();
		computer =  new Computer();
		this.GameFlag = true; //determines when a game finishes  true: game is still on going, 2: game is finsihed
		this.instantiateStonesOnPlayerArea();
	
		
	}
	/*
	 * check requirements for username
	 * more requirements added in future
	 */
	private boolean checkNameforValidity(String username) {
		if(username.length() < 5 && username.length() > 0) {
			JOptionPane.showMessageDialog(this, "Invalid length, please enter a Username with more than 5 characters!");
			return false;
		}
		else if(username.isEmpty()) 
		{
			JOptionPane.showMessageDialog(this, "Input can't be empty.");
			return false;
		}
		
		return true;
	}
	
	public void initializeCountDown(double count, int counterType) {
		DecimalFormat df = new DecimalFormat("00");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
		private double counter = count;
		@Override
		public void run() {

		if(counterType == 1) 
		{
		timer_for_round_computer.setText(df.format(counter));
		textPane.setText("Yea, it's my turn :)!");
		}
		else if (counterType == 2) 
		{
			timer_for_round_game.setText(df.format(counter));
		}
		if (counter > 0) 
		{
		counter-= 0.1; // each 100 miliseconds 
		}
		
		if (counter <= 0 && counterType == 1) {
			counter = 0;
			btnEndRound.setEndRound(false);
	    	draw_stone.setStatus(true);
	    	hasPairs.setStatus(true);
	    	btnMinimumThreeOrder.setStatus(true);
	    	btnCheckforjoker.setStatus(true);
	    	btnEndRound.setStatus(true);
	    	player.setTurn(true);
	    	computer.setTurn(false);
	    	textPane.setText("My Round has finished!");
	    	JOptionPane.showMessageDialog(null, "It's your turn!");
	    	this.cancel(); //cancel the task afterwards, so after the computer's round has finished, method creates a new instance of timertask class!
			return;
			
		}
		else if (counter <= 0 && counterType == 2) {
			counter = 0;
		//	System.out.println("Game Finished!");
			computer.setTurn(false);
			player.setTurn(false);
			setGameFlag(false);
			return;
					
		}
		}
		};
		timer.schedule(task, 0, 100); //each 100 miliseconds, no delay! 
	}
	
	/*
	 * instantiateStoneContainer on the PlayersBoard
	 */
	private void instantiateStonesOnPlayerArea() 
	{	 
		player.setInventory(this.instantiateStoneList(1));
		computer.setInventory(this.instantiateStoneList(2));
	}
	
	
	public List<Stone> instantiateStoneList (int addToPanel)  //case 1: add to Panel, case 2; just add to inventory
	{
		rq = new SystemRequirements();
		List<Stone> stoneList = new ArrayList<Stone>(); //all stones which are on playerarea
		Random random = new Random();
		for(int i = 0; i< player.getMaxInventory(); i++) 
		{
			if(i % 2 == 0) 
			{
				System.out.println("RANDOM NUMBER " + i );
				Stone null_image = new Stone(new ImageIcon("placeholder.png"), -1, -1); 
				stoneList.add(null_image);
				if(addToPanel == 1) {
				playerarea.add(null_image);
				}
			}
			else {
				int number = random.nextInt(rq.getStonesList().size());
				System.out.print("RANDOM NUMBER " + i + ": " + number);
				System.out.println(", VALUE OF STONE:" + rq.getStonesList().get(number).getValue());
				System.out.print(", COLOR OF STONE:" + rq.getStonesList().get(number).getColor());
				Stone stone = rq.getStonesList().get(number);
				stoneList.add(stone);
				if(addToPanel == 1) 
				{
				playerarea.add(stone);
				}
				rq.getStonesList().remove(number); //we have to remove the stone from the list, so no duplicates come to the game and no error, since same stone cannot be added to the list
			}
		}
		return stoneList;
	}
	
	
	public void setGameFlag (boolean flag)
	{
		 this.GameFlag = flag;
	}
	
	public boolean getGameFlag () 
	{
		return this.GameFlag;
	}
	
	
	public void setTextForComputer (String text) 
	{
		this.textPane.setText(text);
	}
		
}
	
