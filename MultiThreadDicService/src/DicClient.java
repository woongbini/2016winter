import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DicClient extends JFrame implements ActionListener {
	JTextField engField = new JTextField(10);
	JTextField korField = new JTextField(10);
	Socket socket = null;
	BufferedReader in = null;
	BufferedWriter out = null;
	
	public DicClient() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JLabel("eng"));
		c.add(engField);
		c.add(new JLabel("kor"));
		c.add(korField);
		
		engField.addActionListener(this);
		
		setSize(500, 100);
		setVisible(true);
		
		setup();
	}
	
	public void handleError(String text) {
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) { System.out.println(e.getMessage()); }
		}
		System.out.println(text);
		System.exit(1);
	}
	
	public void setup() {
		try {
			socket = new Socket("localhost", 9995);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (IOException e) { handleError(e.getMessage()); }
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			out.write(engField.getText() + "\n");
			out.flush();
			String korText = in.readLine();
			korField.setText(korText);
		} catch (IOException e1) { handleError(e1.getMessage()); }
	}
	
	public static void main(String[] args) {
		new DicClient();
	}

}
