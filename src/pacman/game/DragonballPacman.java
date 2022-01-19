package pacman.game;

import java.awt.EventQueue;

import javax.swing.ImageIcon;  // To add an icon
import javax.swing.JFrame;
import javax.swing.JLabel;     // To add an icon

public class DragonballPacman extends JFrame{
	
	JLabel L1;
	
	public DragonballPacman() {
		
		initUI();
	
	}
	
	private void initUI() {
		add(new Board());
		
		setTitle("Dragonball Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380,420);
		setLocationRelativeTo(null);
		
//		setContentPane(new JLabel(new ImageIcon("hintergrung.jpg")));
//		setLayout(getLayout());
//		L1 = new JLabel();
//		add(L1);
//		setSize(380,420);
		
		
	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			
			DragonballPacman ex = new DragonballPacman();
			ex.setVisible(true);
			
			String filepath = "chala.wav";
			Board musicObject = new Board();
			musicObject.playMusic(filepath);
		

	});
		
		
	}

}
