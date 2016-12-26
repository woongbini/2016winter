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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PushServer extends JFrame {
	private MyTextField text = new MyTextField(10);
	private JLabel clientCount = new JLabel("0");
	private JLabel deliveredCount = new JLabel("0");
	private JTextArea clientList = new JTextArea(7, 40);
	
	public PushServer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JLabel("TEXT"));
		c.add(text);
		c.add(new JLabel("JubSok JaSoo:"));
		c.add(clientCount);
		c.add(new JLabel("JunSong JaSoo:"));
		c.add(deliveredCount);
		c.add(new JScrollPane(clientList));
		
		setSize(400, 400);
		setVisible(true);
		
		startService();
	}
	
	private void startService() {
		new ServerThread().start();
	}

	class ServerThread extends Thread {
		public void run() {
			ServerSocket listener = null;
			Socket socket = null;
			
			try {
				listener = new ServerSocket(9995);
			} catch (IOException e) { handleError(e.getMessage());}
			
			while(true) {
				try {
					socket = listener.accept();
					clientList.append("CLIENT ON BOARD \n");
					ServiceThread th = new ServiceThread(socket);
					clientCount.setText(Integer.toString(threadCount));
					th.start();
				} catch (IOException e) { 
					try {
						listener.close();
					} catch (IOException e1) {handleError(e1.getMessage());} 
					handleError(e.getMessage());
				}
			} //end while
		}
	}
	
	/***/
	private int threadCount = 0;
	private class ServiceThread extends Thread {
		Socket socket;
		public ServiceThread(Socket socket) {
			this.socket = socket;
			threadCount++;
		}
		
		public void run() {
			try {
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				while(true) {
					String msg = text.get();
					out.write(msg + "\n");
					out.flush();
					clientList.append(msg + "\n"); 
				}
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void handleError(String message) {
		System.out.println(message);
		System.exit(0);
	}
	
	private class MyTextField extends JTextField {
		int dCount = 0;
		private MyTextField(int size) {
			super(size);
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					put();
				}
			});
		}
		
		void increaseDCount() {
			dCount++;
			deliveredCount.setText(Integer.toString(dCount));
		}
		
		void clearDCount() {
			dCount = 0;
			deliveredCount.setText(Integer.toString(dCount));
		}
		
		synchronized public String get() {
			try {
				wait();
				increaseDCount();
			} catch (InterruptedException e) {
				handleError(e.getMessage());
			}
			if(dCount == threadCount) {
				notify();
			}
			return this.getText();
		}
		
		synchronized public void put() {
			if(dCount != threadCount) {
				try {
					wait();
				} catch (InterruptedException e) {
					handleError(e.getMessage());
				}
			} 
			clearDCount();
			notifyAll(); //일어나 모두 일어나 
		}
	}
	
	public static void main(String[] args) {
		new PushServer();
	}

}
