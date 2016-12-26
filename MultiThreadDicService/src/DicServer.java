import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DicServer extends JFrame {
	HashMap<String, String> dic = new HashMap<String, String>();
	JTextArea ta = new JTextArea(7, 40);
	JTextField engField = new JTextField(10);
	JTextField korField = new JTextField(10);
	
	public DicServer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JScrollPane(ta));
		c.add(new JLabel("eng"));
		c.add(engField);
		c.add(new JLabel("kor"));
		c.add(korField);
		engField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korText = dic.get(engField.getText());
				korField.setText(korText);
			}
		});
		korField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dic.put(engField.getText(), korField.getText());
			}
		});
		
		
		setSize(500, 200);
		setVisible(true);
		
	}
	
	public void startService() {
		new ServerThread().start();
	}
	
	public void handleError(String text) {
		System.out.println(text);
		System.exit(1);
	}
	
	private class ServerThread extends Thread {
		ServerSocket listener = null;
		Socket socket = null;
		public void run() {
			try {
				listener = new ServerSocket(9995);
			} catch (IOException e) { handleError(e.getMessage()); }
			
			while(true) {
				try {
					socket = listener.accept();
					ServiceThread th = new ServiceThread(socket);
					th.start();
				} catch (IOException e) { 
					try {
						listener.close();
						socket.close();
					} catch (IOException e1) { handleError(e.getMessage());}
					handleError(e.getMessage()); 
				}
			}
		}
	}
	
	private class ServiceThread extends Thread {
		Socket socket;
		public ServiceThread(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				while(true) {
					String engText = in.readLine();
					String korText = dic.get(engText);
					out.write(korText + "\n");
					out.flush();
					ta.append(engText + ":" + korText );
					int pos = ta.getText().length();
					ta.setCaretPosition(pos);
				}
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					handleError(e1.getMessage());
				}
				handleError(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		new DicServer();
	}
}
