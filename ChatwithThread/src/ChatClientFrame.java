import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClientFrame extends JFrame {
	Receiver recevier = new Receiver();
	JTextField tf = new JTextField();
	ServerSocket listener;
	Socket socket = null;
	BufferedReader in = null;
	BufferedWriter out = null;

	public ChatClientFrame() {
		setSize(500, 500);
		Container c = getContentPane();
		c.add(new JScrollPane(recevier), BorderLayout.CENTER);
		c.add(tf, BorderLayout.SOUTH);
		try {
			setup();
		} catch (IOException e) { handleError(e.getMessage()); }
		tf.addActionListener(new MyAction());
		setVisible(true);
		Thread th = new Thread(recevier);
		th.start();
	}

	class MyAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JTextField f = (JTextField)e.getSource();
			String user = f.getText();
			try {
				out.write(user + "\n");
				out.flush();
				recevier.append("\n" + user);
				int pos = recevier.getText().length();
				recevier.setCaretPosition(pos);
				
			} catch (IOException e1){ handleError(e1.getMessage()); }
		}
	}
	
	public void handleError(String msg) {
		try {
			if(listener != null) listener.close();
			if(socket != null) socket.close();
			System.out.println(msg);
			System.exit(0);
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public void setup() throws IOException {
		socket = new Socket("113.198.81.16", 9995);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	class Receiver extends JTextArea implements Runnable {
		
		public void run() {
			while(true) {
				try {
					String message = in.readLine();
					this.append("\n" + message);
					int pos = this.getText().length();
					this.setCaretPosition(pos);
				} catch (IOException e) { handleError(e.getMessage()); }
			}
		}
	}
	
	public static void main(String [] args) {
		new ChatClientFrame();
	}
}
