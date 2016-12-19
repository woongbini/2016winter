import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	public MyFrame() {
		setSize(500, 500);
		setContentPane(new MyPanel());
		setVisible(true);
		getContentPane().requestFocus();
	}
	
	class MyPanel extends JPanel {
		JLabel avata = new JLabel("ㅜㅜ");
		Monster mon = new Monster("MM");
		public MyPanel() {
			setLayout(null);
			avata.setSize(50, 50);
			avata.setLocation(100, 100);
			add(avata);
			
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int code = e.getKeyCode();
					switch (code) {
					case KeyEvent.VK_DOWN : 
						avata.setLocation(avata.getX(), avata.getY()+10); break;
					case KeyEvent.VK_UP : 
						avata.setLocation(avata.getX(), avata.getY()-10); break;
					case KeyEvent.VK_RIGHT : 
						avata.setLocation(avata.getX()+10, avata.getY()); break;
					case KeyEvent.VK_LEFT : 
						avata.setLocation(avata.getX()-10, avata.getY()); break;
					
					//default:break;
					} //end switch
				}//end keyPressed
			});
			
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					requestFocus();
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			
		}
	}

	public static void main(String[] args) {
		new MyFrame();
	}

}

class Monster extends JLabel implements Runnable {
	JLabel avata;
	public Monster(String text) {
		//상속받았기 때문에 super클래스에 넘겨준다. 
		super(text);
		this.avata = avata;
		Thread th = new Thread(this);
		th.start();
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(300);
				int x = avata.getX();
				int y = avata.getY();
				int xMon = this.getX();
				int yMon = this.getY();
				if(x < xMon) xMon -=10;
				else xMon += 10;
				if(y < yMon) yMon -=10;
				else yMon += 10;
				this.setLocation(xMon, yMon);
				
			} catch (InterruptedException e) {
				
			}
		}
	}
}




