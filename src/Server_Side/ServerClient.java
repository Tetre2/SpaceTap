package Server_Side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClient {

	private BufferedReader from_client;
	private PrintStream to_client;

	public ServerClient(Socket connection) {

		try {

			from_client = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			to_client = new PrintStream(connection.getOutputStream());

		} catch (IOException e) {

			e.printStackTrace();
		}

		Runnable input_listener = () -> {
			while (true) {
				try {

					String input = from_client.readLine();

					if(input.contains("[") && input.contains("]")){
						
						int startIndex = input.indexOf("[") + 1;
						int endIndex = input.indexOf("]");
						String cmd = input.substring(startIndex, endIndex);
						
						if (cmd.equals("r")) {
							SingeltonServer.playerReady();
						}

					}


				} catch (IOException e) {
					System.out.println("DISCONNETED FROM SERVER");
					break;
				}
			}
		};

		new Thread(input_listener).start();

	}

	public void startGame(){
		to_client.println("(startgame)");
		System.out.println("startgame");
	}


}
