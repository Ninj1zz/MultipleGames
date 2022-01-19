package pacman.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.AttributeSet.ColorAttribute;

public class Board extends JPanel  implements ActionListener{
	
	
	//ATTRIBUTS
	
	private Dimension d;
	private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
	
	private Image ii;
	private final Color dotColor = new Color(100, 100, 0);
	private Color mazeColor;
	
	private boolean inGame = false;
	private boolean dying = false;
	
	private final int BLOCK_SIZE = 24;
	private final int N_BLOCKS= 15;
	private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
	private final int GOKU_ANIM_DELAY = 2; // Open and Close Gokus moulth
	private final int GOKU_ANIM_COUNT = 4;
	private final int MAX_FRIEZAS = 12;
	private final int GOKU_SPEED = 6;
	
	private int gokuAnimCount = GOKU_ANIM_DELAY;
	private int gokuAnimDir = 1; 
	private int gokuAnimPos = 0;
	private int N_FRIEZAS = 6;
	private int gokusLeft;
	private int score;
	private int[] dx, dy;
	private int[] frieza_x, frieza_y, frieza_dx, frieza_dy, friezaSpeed;
	
	private Image frieza;
	private Image goku;
	private Image oben, links, rechts, unten;
	private Image oben2, links2, rechts2, unten2;
	private Image oben3, links3, rechts3, unten3;
	
	private int goku_x, goku_y, goku_dx, goku_dy;
	private int req_dx, req_dy, view_dx, view_dy; // view_dx and view_dy already have certain values 
	
	private final short levelData[] = {   //Set of numbers, each number will define an element later shown on the map
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };
	
	private final int validSpeeds[] = {1,2,3,4,6,8,10}; // Set of speeds for Pacman and ghosts
	private final int maxSpeed = 8;
	
	private int currentSpeed = 3;
	private short[] screenData;
	private Timer timer;   // Timer is needed to pause the game
	
	
	// CONSTRUCTOR
	
	public Board() {   // initialize 
		
		loadImages();
		initVariables();
		initBoard();
	}
	
	//METHODS
	
	private void initBoard() {
		addKeyListener(new TAdapter()); // Add the keylistener
		
		setFocusable(true);
		setBackground(Color.black);
	}
	
	private void initVariables() {
		
		screenData = new short [N_BLOCKS * N_BLOCKS];
		mazeColor = new Color(40,200,40);
		d = new Dimension(400,400);  // Dimension of the screen
		frieza_x = new int [MAX_FRIEZAS];
		frieza_dx = new int [MAX_FRIEZAS];
		frieza_y = new int [MAX_FRIEZAS];
		frieza_dy = new int [MAX_FRIEZAS];
		friezaSpeed = new int [MAX_FRIEZAS];
		dx = new int[4];
		dy = new int[4];
		
		timer = new Timer(40, this); // timer needed for pause the game
		timer.start();
		
	}
	
	@Override
	public void addNotify() {
		super.addNotify(); // Super needed to access the parent class
		
		initGame();
	}
	
	private void doAnim() { // 
		
		gokuAnimCount--;
		
		if(gokuAnimCount <= 0) { 
			
			gokuAnimCount = GOKU_ANIM_DELAY;
			gokuAnimPos = gokuAnimPos + gokuAnimDir;
			
			if (gokuAnimPos == (GOKU_ANIM_COUNT - 1) || gokuAnimPos == 0) {
				
				gokuAnimDir = - gokuAnimDir; // The direction makes sure that the delay gets smaller each time so a different animation will be shown
			}
		}
	}
	
	private void playGame(Graphics2D g2d) {
		
		if(dying) {
			death();
		} else {
			
		
		moveGoku();
		moveFriezas(g2d);
		checkMaze();
		drawGoku(g2d);
		}
	}
	
	public void playMusic(String musicLocation) {
		
		try {
			
			File musicPath = new File(musicLocation);
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY); // Music plays continuously
				
			} else {
				System.out.println("No File found");
			}
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void showIntroScreen(Graphics2D g2d) {
		
		g2d.setColor(new Color(70,32,48));
		g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		g2d.setColor(Color.white);
		g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		
		String s = "PRESS s OR S TO START.";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small); // FontMetrics is a class
		
		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s))/ 2, SCREEN_SIZE / 2); // Draw the String on the Screen
	}
	
	
	private void death() {
		gokusLeft--;
		
		if(gokusLeft == 0) {
			inGame = false;
		}
		continueLevel();
	}
	
	private void checkMaze() { // Winning the game
		
		short i = 0;
		boolean finished = true;
		
		while (i < N_BLOCKS * N_BLOCKS && finished) {
			
			if ((screenData[i] & 48) != 0) { // Elements of the Gameplay
				
				finished = false;
				
			}
			
			i++;
			
		}
		
		if(finished) {
			
			score += 50;
			
			if(N_FRIEZAS < MAX_FRIEZAS) {
				
				N_FRIEZAS ++; // Each time we win the Friezas become more
				
			}
			if (currentSpeed < maxSpeed) {
				
				currentSpeed ++; // speed also gets higher
				
			}
			
			initLevel();
		}
	}
	
	private void drawScore(Graphics2D g) {
		int i;
		String s;
		
		g.setFont(smallFont);
		g.setColor(new Color(96,100,255));
		s = "Score: " + score;
		g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 13); // Position of score
		
		g.setFont(smallFont);
		g.setColor(new Color(6,128,255));
		s = "Press P/p to pause";
		g.drawString(s, SCREEN_SIZE / 2 + 56, SCREEN_SIZE + 27);
		
		for(i = 0; i < gokusLeft; i++) { // Show current number of lives left
			g.drawImage(links2,  i * 28 + 15, SCREEN_SIZE - 3, this); // Position of Gokus shown
		}
		
	}
	
	private void moveFriezas(Graphics2D g2d) {
		short i ;
		int pos;
		int count;
		
		for(i = 0; i < N_FRIEZAS; i++) { // inizialize the Friezas
			
			if(frieza_x[i] % BLOCK_SIZE == 0 && frieza_y[i] % BLOCK_SIZE == 0) { // determining the position of Friezas
				
				pos = frieza_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (frieza_y[i] / BLOCK_SIZE);
				
				count = 0;
				
				if((screenData[pos] & 1) == 0 && frieza_dx[i] != 1) {
					
					dx[count] = - 1;
					dy[count] = 0;
					count++;
					
				}
				
				if((screenData[pos] & 2) == 0 && frieza_dy[i] != 1) {
				
					dx[count] = 0;
					dy[count] = - 1;
				    count++;
				    
				}
				
				if((screenData[pos] & 4) == 0 && frieza_dx[i] != - 1) {
					
					dx[count] = 1;
					dy[count] = 0;
					count++;
					
				}
				
				if((screenData[pos] & 8) == 0 && frieza_dy[i] != - 1) {
					
					dx[count] = 0;
					dy[count] = 1;
					count++;
					
				}
				
				if(count == 0) {
					
					if((screenData[pos] & 15) == 15) {
						
						frieza_dx[i] = 0;
						frieza_dy[i] = 0;
						
					} else {
						
						frieza_dx[i] = - frieza_dx[i]; // If frieza faces the wall he should go away from it
						frieza_dy[i] = - frieza_dy[i];
					}
					
				} else {
					count = (int) (Math.random() * count); // Generate a random number that makes the Friezas walk randomly
					
					if(count > 3) {
						count = 3;
					}
					
					frieza_dx[i] = dx[count]; // make the Friezas walk over the fruits
					frieza_dy[i] = dy[count];
				}
			}
			
			frieza_x[i] = frieza_x[i] + (frieza_dx[i] * friezaSpeed[i]);  // estimating the next Frieza position
			frieza_y[i] = frieza_y[i] + (frieza_dy[i] * friezaSpeed[i]);
			drawGhost(g2d, frieza_x[i] + 1, frieza_y[i] + 1);  // Display fireza on the display 
			
			if(goku_x > (frieza_x[i] - 12) && goku_x < (frieza_x[i] + 12) // Checking if Goku hits a freza
					&& goku_y > (frieza_y[i] - 12) && goku_y < (frieza_y[i] + 12)
					&&inGame) {
				
				dying = true;
				
			}
		}
	}
	
	private void drawGhost(Graphics2D g2d, int x, int y) { // Display fireza on the display
		
		g2d.drawImage(frieza, x, y, this);
	}
	
	 
	private void moveGoku() {
		
		int pos;
		int ch;
		
		if(req_dx == - goku_dx && req_dy == goku_dy) { // Required valuas are setted from the kayboard
		
			goku_dx = req_dx;
			goku_dy = req_dy;
			view_dx = goku_dx;
			view_dy = goku_dy;
			
		}
		
		if(goku_x % BLOCK_SIZE == 0 && goku_y % BLOCK_SIZE == 0) {
			
			pos = goku_x / BLOCK_SIZE + N_BLOCKS * (int)(goku_y / BLOCK_SIZE); //ensure that we have a int for the position
			ch = screenData[pos]; // we have about 50 position depending of the pos value we have each position Goku goes
			
			if ((ch & 16) != 0) {  // Loop if Pacman ate the fruit
				
				screenData[pos] = (short)(ch & 15);
				score++;
				
				}
			
		    if(req_dx != 0 || req_dy != 0) { // check if the req dx or req dy is not 0
		    	
		    	if(!((req_dx == - 1 && req_dy == 0 && (ch & 1) !=0)
		    	   || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
		    	   ||(req_dx == 0 && req_dy == - 1 && (ch & 2) != 0)
		    	   || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
		    		
		    		goku_dx = req_dx;  // giving Goku the positions
		    		goku_dy = req_dy;
		    		view_dx = goku_dx;
		    		view_dy = goku_dy;
		    	}
		    	
		    }
		    if ((goku_dx == - 1 && goku_dy== 0 && (ch & 1) != 0) // Checking if the Goku is not moving to stop him
		    		|| (goku_dx == 1 && goku_dy == 0 && (ch & 4) != 0)
		    		||(goku_dx == 0 && goku_dy == - 1 && (ch & 2) != 0)
		    		|| (goku_dx == 0 && goku_dy == 1 && (ch & 8) != 0))	{
		    	
		    	goku_dx = 0; // Goku does not move
		    	goku_dy = 0; // Goku does not move
		    	
		    }
		   
		    
		 }
		    goku_x = goku_x + GOKU_SPEED * goku_dx;
		    goku_y = goku_y + GOKU_SPEED * goku_dy;
		    
		    }
		
		
		
	
	
	
	private void drawGoku(Graphics2D g2d) {
		
		if(view_dx == - 1) {
			
			drawGokuLeft(g2d);
			
		} else if (view_dx == 1) {
			
			drawGokuRight(g2d);
			
		}else if (view_dy == - 1) {
			
			drawGokuUp(g2d);
			
		}else {
			
			drawGokuDown(g2d);
			
		}
		
	}
	
	private void drawGokuUp(Graphics2D g2d) {
		
		switch(gokuAnimPos) {
		
		case 1:
			
			g2d.drawImage(oben, goku_x + 1, goku_y + 1, this);
			break;
			
		case 2:
			
			g2d.drawImage(oben2, goku_x + 1, goku_y + 1, this);
			break;
			
		case 3:
			
			g2d.drawImage(oben3, goku_x + 1, goku_y + 1, this);
			break;
			
		default:
			
			g2d.drawImage(goku, goku_x + 1, goku_y + 1, this);
			break;
		}
	}
	
	private void drawGokuDown(Graphics2D g2d) {
		
		switch(gokuAnimPos) {
		
		case 1:
			
			g2d.drawImage(unten, goku_x + 1, goku_y + 1, this);
			break;
			
		case 2:
			
			g2d.drawImage(unten2, goku_x + 1, goku_y + 1, this);
			break;
			
		case 3:
			
			g2d.drawImage(unten3, goku_x + 1, goku_y + 1, this);
			break;
			
		default:
			
			g2d.drawImage(goku, goku_x + 1, goku_y + 1, this);
			break;
		}
	}
	
	private void drawGokuLeft(Graphics2D g2d) {
		
		switch(gokuAnimPos) {
		
		case 1:
			
			g2d.drawImage(links, goku_x + 1, goku_y + 1, this);
			break;
			
		case 2:
			
			g2d.drawImage(links2, goku_x + 1, goku_y + 1, this);
			break;
			
		case 3:
			
			g2d.drawImage(links3, goku_x + 1, goku_y + 1, this);
			break;
			
		default:
			
			g2d.drawImage(goku, goku_x + 1, goku_y + 1, this);
			break;
		}
	}
	
	private void drawGokuRight(Graphics2D g2d) {
		
		switch(gokuAnimPos) {
		
		case 1:
			
			g2d.drawImage(rechts, goku_x + 1, goku_y + 1, this);
			break;
			
		case 2:
			
			g2d.drawImage(rechts2, goku_x + 1, goku_y + 1, this);
			break;
			
		case 3:
			
			g2d.drawImage(rechts3, goku_x + 1, goku_y + 1, this);
			break;
			
		default:
			
			g2d.drawImage(goku, goku_x + 1, goku_y + 1, this);
			break;
		}
	}
	
	// Standard values on which the game starts
	private void initGame() {
		
		gokusLeft = 3;
		score = 0;
		initLevel();
		N_FRIEZAS = 6;
		currentSpeed = 3;
		
	}
	
	private void initLevel() {
		int i;
		
		for(i = 0; i< N_BLOCKS * N_BLOCKS; i++) {
			screenData[i] = levelData[i];
			
		}
		
		continueLevel();
		
	}
	
	private void continueLevel() {  // Function that lets the friezas move randomly imedientily after we start the game
		int i;
		int dx = 1;
		int random;
		
		for(i = 0; i < N_FRIEZAS; i++) { // for each frieza
			
			frieza_y[i] = 4 * BLOCK_SIZE; // y direction of friezas
			frieza_x[i] = 4 * BLOCK_SIZE; // x direction of friezas
			frieza_dy[i] = 0; // Determine Friezas diectons
			frieza_dx[i] = dx;
			dx = - dx;
			random = (int) (Math.random() * (currentSpeed + 1));  // Make friezas move randomly
			
			if(random > currentSpeed) {
				
				random = currentSpeed; // Random number should not be higher than the current speed
				
			}
			friezaSpeed[i] = validSpeeds[random];
		}
		
		goku_x = 7 * BLOCK_SIZE;
		goku_y = 11 * BLOCK_SIZE;
		goku_dx = 0;  // determine the Goku startpoint
		goku_dy = 0;
		req_dx = 0;
		req_dy = 0;
		view_dx = - 1;
		view_dy = 0;
		dying = false;
	}
	
	
	// Method to draw the maze
	private void drawMaze(Graphics2D g2d) {
	
		short i = 0;
		int x;
		int y;
		
		for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {  // Until the max Screen size is reached (Blocksize with of the block)
			for(x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) { // Until the max Screen size is reached (Blocksize with of the block)
				
				g2d.setColor(mazeColor);
				g2d.setStroke(new BasicStroke(2));
				
				if((screenData[i] & 1) != 0) {
					
					g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
				}
				
				if((screenData[i] & 2) != 0) {
					
					g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y );
				}
				
				if((screenData[i] & 4) != 0) {
					
					g2d.drawLine(x+ BLOCK_SIZE - 1 , y, x + BLOCK_SIZE - 1 , y+ BLOCK_SIZE - 1);
				}
				if((screenData[i] & 8) != 0) {
					
					g2d.drawLine(x , y+ BLOCK_SIZE - 1, x + BLOCK_SIZE - 1 , y+ BLOCK_SIZE - 1);
				}
				if((screenData[i] & 16) != 0) {
					g2d.setColor(dotColor);
					g2d.fillRect(x + 11, y + 11, 2, 2);
				}
				
				i++;
	
				
			}
		}
		
	}
	
  @Override
  public void paintComponent(Graphics g) {
	  super.paintComponent(g);
	  
	  doDrawing(g);
  }
  // Draws the whole maze
  private void doDrawing(Graphics g) {
	  
	  Graphics2D g2d = (Graphics2D) g;
	  
	  g2d.setColor(Color.black);  // Color of the background
	  g2d.fillRect(5,5,d.width, d.height);
	  
	  
	  doAnim();
	  
	  drawMaze(g2d);
	  
	  drawScore(g2d);
	  
	  if(inGame) {
	  playGame(g2d);
	  
	  } else {
		  showIntroScreen(g2d); // Show Welcome screen 
	  }
	  g2d.drawImage(ii, 5,5,this);
	  Toolkit.getDefaultToolkit().sync(); 
	  g2d.dispose(); //
  }
  
  private void loadImages() {
	  frieza = new ImageIcon("1234.png").getImage();
	  goku = new ImageIcon("Goku.png").getImage();
	  oben = new ImageIcon("oben.png").getImage();
	  oben2 = new ImageIcon("oben2.png").getImage();
	  oben3 = new ImageIcon("oben3.png").getImage();
	  unten = new ImageIcon("unten.png").getImage();
	  unten2 = new ImageIcon("unten2.png").getImage();
	  unten3 = new ImageIcon("unten3.png").getImage();
	  links = new ImageIcon("links.png").getImage();
	  links2 = new ImageIcon("links2.png").getImage();
	  links3 = new ImageIcon("links3.png").getImage();
	  rechts = new ImageIcon("rechts.png").getImage();
	  rechts2 = new ImageIcon("rechts2.png").getImage();
	  rechts3 = new ImageIcon("rechts3.png").getImage();
  }

  
 
class TAdapter extends KeyAdapter{
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(inGame) {
			if(key == KeyEvent.VK_LEFT) {
				
				req_dx =- 1;
				req_dy = 0;		
				
			} else if(key == KeyEvent.VK_RIGHT) {
				
				req_dx = 1;
				req_dy = 0;	
				
			} else if(key == KeyEvent.VK_UP) {
				
				req_dx = 0;
				req_dy = -1;	
				
		}     else if(key == KeyEvent.VK_DOWN) {
			
			    req_dx = 0;
			    req_dy = 1;	
			    
		}     else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
			
			    inGame = false;
			    
		}     else if (key == KeyEvent.VK_P) {
			
			      if (timer.isRunning()) {
			    	  
			    	  timer.stop();
			    	  
			      } else {
			    	  
			    	  timer.start();
			    	  
			      }
		}
			
			
		} else {
			if (key == 's' || key == 'S') { // Check if s or S is pressed to start the Game
				inGame = true;
				initGame();
		}
	}
	}		
	@Override
	public void keyReleased(KeyEvent e) { // release the keys to stop Goku from moving
		
		int key = e.getKeyCode();
		
		if(key == Event.LEFT || key == Event.RIGHT
				|| key == Event.UP || key == Event.DOWN) {
			
			req_dx = 0;
			req_dy = 0;
			
		}
	}
}	
  
@Override
public void actionPerformed(ActionEvent arg0) { // once a key is selected we repaint the screen
	
	repaint();
	
	
}
  
	


	
	

}
