import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JjinMakVer {
	static final String SCORE_TEXT = "Score : ";
	static int score = 0;
	static ColorControlThread colorControlThread;
	
	public static void main(String[]args) {
		JFrame frame = new JFrame();
		JPanel scorePanel = new JPanel();
		JLabel scoreLabel = new JLabel();
		JPanel clickPanel = new JPanel();
		
		colorControlThread = new ColorControlThread(clickPanel);
		
		frame.setSize(800, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		scorePanel.setBackground(Color.WHITE);
		scorePanel.add(scoreLabel, "Center");
		scoreLabel.setText(SCORE_TEXT + score);
		
		clickPanel.setBackground(Color.GREEN);
		clickPanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color backgroundColor = clickPanel.getBackground();
				if(backgroundColor == Color.RED) {
					colorControlThread.setStopFlag(true);
					score = 0;
					JOptionPane.showMessageDialog(null, "GAMEOBER");
				}else if(backgroundColor == Color.GREEN) {
					score++;
				}else {
					throw new RuntimeException();
				}
				scoreLabel.setText(SCORE_TEXT + score);
			}
		});
		frame.add(scorePanel, BorderLayout.NORTH);
		frame.add(clickPanel, BorderLayout.CENTER);
		frame.setVisible(true);
		colorControlThread.start();
	}
}

class ColorControlThread extends Thread{
	final JPanel clickPanel;
	boolean stopFlag = false;
	public ColorControlThread(JPanel clickPanel) {
		this.clickPanel = clickPanel;
	}
	
	@Override
	public void run() {
		while(!stopFlag) {
			try {
				Thread.sleep((long)(Math.random()*2000));
				if(clickPanel.getBackground() == Color.RED) {
					clickPanel.setBackground(Color.GREEN);
				}else {
					clickPanel.setBackground(Color.RED);
				}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		public void setStopFlag(boolean stopFlag) {
			this.stopFlag = stopFlag;
		}
	}
