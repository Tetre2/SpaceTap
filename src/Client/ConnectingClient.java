package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class ConnectingClient{


	private BufferedReader from_server;
	private PrintStream to_server;
	private Socket connection;
	
	public ConnectingClient(){
		try {

			connection = new Socket("localhost", 444);
			from_server = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			to_server = new PrintStream(connection.getOutputStream());

		} catch (IOException e) {

			e.printStackTrace();
		}

		Runnable output_listener = () -> {
			while (true) {
				try {

					String input = from_server.readLine();
System.out.println(input);
					if(input.contains("(") && input.contains(")")){

						System.out.println(input);
						
						int startIndex = input.indexOf("(") + 1;
						int endIndex = input.indexOf(")");
						String cmd = input.substring(startIndex, endIndex);

						System.out.println(cmd);
						
						if (cmd.equals("startgame")) {
							Game.startGame();
						}

					}


				} catch (IOException e) {
					System.out.println("DISCONNETED FROM SERVER");
					connection = null;
					break;
				}
			}
		};

		new Thread(output_listener).start();
	}
	
	public void sendReady(){
		to_server.println("[r]");
	}
	
}
