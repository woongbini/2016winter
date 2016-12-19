import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyFrame extends JFrame {
	
	public MyFrame() {
		setSize(600, 600);
		setTitle("swing frame");
		this.setContentPane(new MyPanel());
		setVisible(true);
	}
	
	class MyPanel extends JPanel {
		private MyDic dic = new MyDic();
		JTextField engText = new JTextField(10);
		JTextField korText = new JTextField(10);
		
		public MyPanel() {
			this.setBackground(Color.BLUE);
			JLabel text1 = new JLabel("eng"); 
			add(text1);
			add(engText);
			
			JLabel text2 = new JLabel("kor"); 
			add(text2);
			add(korText);
			
			JButton addButton = new JButton("add");
			add(addButton);
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String eng = engText.getText();
					String kor = korText.getText();
					if(eng.equals("") || kor.equals("")) {
						engText.setText("plz input value"); return;
					}
					dic.put(eng, kor);
					System.out.println(eng + " " + kor);
				}
			});
			
			JButton searchButton = new JButton("search"); 
			add(searchButton);
			searchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/*String eng = engText.getText();
					dic.get(eng);
					System.out.println(dic.get(eng));*/
					
					String eng = engText.getText();
					if(eng.equals("")) {
						engText.setText("plz input text"); return;
					}
					if(kor == null) korText.setText("cannot find value");
					else korText.setText(kor);
					String kor = dic.get(eng);
					korText.setText(kor);
					System.out.println(korText.getText());
				}
			});
			
			
			
			
		}
	}
	
	public static void main(String[] args) {
		new MyFrame();
	}
}
