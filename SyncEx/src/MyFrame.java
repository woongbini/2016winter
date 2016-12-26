import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * 연습문제 12번도 정확히 notify()를 실행하는것이다. 
 * @author woongbini
 *
 */
public class MyFrame extends JFrame {
	MyLabel bar = new MyLabel(100);
	public MyFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,300);
		Container c = getContentPane();
		c.setLayout(null);
		bar.setSize(300, 20);
		bar.setLocation(20, 50);
		c.add(bar);
		
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				bar.fill();
			}
		});
		
		setVisible(true);
		new ConsumerThread(bar).start();
		c.requestFocus();
	}
	public static void main(String[] args) {
		new MyFrame();
	}
}

class MyLabel extends JLabel {
	int maxBarSize;
	int barSize = 0;
	public MyLabel(int maxBarSize) {
		this.maxBarSize = maxBarSize;
		this.setOpaque(true);
		this.setBackground(Color.ORANGE);
	}
	synchronized public void fill() {
		if(barSize == maxBarSize) {
			try {
				wait();
			} catch (InterruptedException e) { return; }
		}
		barSize++;
		repaint();
		notify();
	}
	synchronized public void consume() throws InterruptedException {
		if(barSize == 0) wait();	
		barSize--;
		repaint();
		notify();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.MAGENTA);
		if(barSize == 0) return;
		g.fillRect(0, 0, this.getWidth()/100*barSize, this.getHeight());
	}
}

class ConsumerThread extends Thread {
	MyLabel bar;
	public ConsumerThread(MyLabel bar) {
		this.bar = bar;
	}
	public void run() {
		while(true) {
			try {
				sleep(200);
				bar.consume();
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}





