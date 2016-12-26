import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		BufferedReader stin = null;
		
		try {
			socket = new Socket(args[0], 9995);
			System.out.println("connected");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			stin = new BufferedReader(new InputStreamReader(System.in));
			
			while(true) {
				String user = stin.readLine();
				if(user.equalsIgnoreCase("BB")) {
					out.write(user);
					out.flush();
					break;
				}
				out.write(user + "\n");
				out.flush();
				
				String message = in.readLine();
				System.out.println(message);
			}
			
		} catch (IOException e) { 
			System.out.println(e.getMessage()); 
		} finally {
			if(socket == null) return;
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}

}
