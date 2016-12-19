import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	
	public MyFrame() {
		setSize(300, 300);
		setTitle("swing frame");
		this.setContentPane(new MyPanel());
		setVisible(true);
	}
	
	class MyPanel extends JPanel {
		public MyPanel() {
			this.setBackground(Color.BLUE);
			JLabel text1 = new JLabel("eng"); this.add(text1);
			JLabel text2 = new JLabel("kor"); this.add(text2);

		}
	}
	
	public static void main(String[] args) {
		new MyFrame();
	}
}
