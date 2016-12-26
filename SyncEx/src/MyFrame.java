import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyFrame extends JFrame {
	MyLabel bar = new MyLabel(100);
	public MyFrame() { 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		Container c = getContentPane();
		c.setLayout(null);
		bar.setSize(300, 20);
		bar.setLocation(20, 50);
		c.add(bar);
		
		setVisible(true);
	}
	public static void main(String[] args) {
		new MyFrame();
	}
	
}

class MyLabel extends JLabel {
	int maxBarSize;
	public MyLabel(int maxBarSize) {
		this.maxBarSize = maxBarSize;
		this.setOpaque(true);
		this.setBackground(Color.ORANGE);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}