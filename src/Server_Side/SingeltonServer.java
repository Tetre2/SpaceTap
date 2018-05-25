package Server_Side;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import com.sun.security.ntlm.Client;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SingeltonServer {

	public static double WORLD_WIDTH = 800;
	public static double WORLD_HEIGHT = 600;
	ServerSocket server;
	int PORT = 444;
	private boolean gameOn= false;
	private static int playersReady = 0;

	static ArrayList<ServerClient> clients = new ArrayList<ServerClient>();

	private static SingeltonServer ME = null;

	public static void playerReady(){
		playersReady++;
	}
	
	public static void main(String[] args) {
		getInstance();
	}

	private SingeltonServer() {


		try {
			server = new ServerSocket(PORT);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Runnable connection_listener = () -> {
			while (true) {
				try {

					ServerClient client = new ServerClient(server.accept());
					clients.add(client);
					System.out.println("A CLIENT CONNECTED");		
					
					
				} catch (IOException e) {
					System.out.println("Conection Error");
				}

			}

		};

		
		Runnable test = () -> {
			while (true) {
if(!gameOn){				
System.out.println(playersReady);
}
				if(playersReady == clients.size() && clients.size() > 0 && !(gameOn)){
					
					for (int i = 0; i < clients.size(); i++) {
						clients.get(i).startGame();
						System.out.println("kmsa");
					}
					gameOn = true;
					
				}
				
			}

		};
		
		

		new Thread(connection_listener).start();
		new Thread(test).start();
		
		System.out.println("SERVER STARTED ON PORT " + PORT);

	}

	

	public static SingeltonServer getInstance(){

		if(ME == null){
			ME = new SingeltonServer();
		}

		return ME;

	}


}
