import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PushClient extends JFrame implements ActionListener {
	private JTextField sAddr = new JTextField("");
	private Receiver text = new Receiver();
	private JButton startBtn = new JButton("START");
	Socket socket = null;
	private BufferedReader in = null;
	
	
	public PushClient() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.add(sAddr, BorderLayout.NORTH);
		c.add(new JScrollPane(text), BorderLayout.CENTER);
		c.add(startBtn, BorderLayout.SOUTH);
		startBtn.addActionListener(this);
		setSize(400, 400);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		setup();
		Thread th = new Thread(text);
		th.start();
		//startBtn.setEnabled(false); //-> 아래 코드로 하는게 맞다.
		((JButton)e.getSource()).setEnabled(false);
	}
	
	public void setup() {
		try {
			socket = new Socket(sAddr.getText(), 9995);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) { handleError(e.getMessage());}
	}

	private void handleError(String message) {
		System.out.println(message);
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) { System.out.println(e.getMessage());}
		}
		System.exit(0);
	}

	private class Receiver extends JTextArea implements Runnable {
		
		public void run() {
			while(true) {
				String message;
				try {
					message = in.readLine();
					this.append(message);
				} catch (IOException e) { handleError(e.getMessage());}
			} //end while
		}
	}
	
	public static void main(String[] args) {
		new PushClient();
	}
}
