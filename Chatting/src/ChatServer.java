import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		ServerSocket listener = null;
		Socket socket = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		BufferedReader stin = null;
		
		//making soc
		try {
			listener = new ServerSocket(9995);
			socket = listener.accept(); //waiting connection
			System.out.println("connection");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			stin = new BufferedReader(new InputStreamReader(System.in));
			
			while(true) {
				String message = in.readLine();
				if(message.equalsIgnoreCase("BB")) break;
				System.out.println(message);
				String user = stin.readLine();
				out.write(user+"\n");
				out.flush();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(listener == null) return;
				listener.close();
				socket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

}
