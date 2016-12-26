import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VibFrame extends JFrame {
	MyThread th;
	public VibFrame() {
		setSize(300, 300);
		setLocation(500, 500);

		JLabel la = new JLabel("vibration");
		la.setSize(80, 30);
		la.setLocation(100, 100);
		Container c = getContentPane();
		c.setLayout(null);
		c.add(la);
		c.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Thread [] tarray = new Thread [Thread.activeCount()];
				Thread.enumerate(tarray);
				for(int i=0 ; i<tarray.length ; i++) {
					tarray[i].interrupt();
				}
				System.out.println(Thread.currentThread().getName());
			}
		});

		setVisible(true);
		
		th = new MyThread("Label", la);
		th.start();
		
		new MyThread("Vib", this).start();
		
	}

	class MyThread extends Thread {
		Component target;
		public MyThread(String name, Component target) {
			super(name);
			this.target = target;
		}
		
		public void quit() {
			interrupt();
		}
		
		public void run() {
			while(true) {
				int x = target.getX();
				int y = target.getY();
				int disX = (int)(Math.random()*10) - 5;
				int disY = (int)(Math.random()*10) - 5;
				target.setLocation(x+disX, y+disY);
				try {
					sleep(50);
				} catch (InterruptedException e) {
					System.out.println("quit?");
					return;
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		new VibFrame();
		Thread th = Thread.currentThread();
		
		System.out.println("스레드 몇개에염? :" + Thread.activeCount());
		System.out.println(th.getId());
		System.out.println(th.getName());
		System.out.println(th.getPriority());
		System.out.println(th.getState().toString());
		
		
	}

}
