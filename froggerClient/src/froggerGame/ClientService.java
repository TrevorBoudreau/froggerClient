package froggerGame;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;


//processing routine on server (B)
public class ClientService implements Runnable {
	final int CLIENT_PORT = 5555;

	private Socket s;
	private Scanner in;
	
	private frogSprite frog;
	private logSprite log[][];
	private carSprite car[][];
	private JLabel frogLabel, carLabel[][], logLabel[][];
	private JButton restartBtn;
	private JLabel scoreLabel;
	int score = 0;
	
	public ClientService() {}

	public ClientService (Socket Socket, frogSprite frog, logSprite[][] log, carSprite[][] car, 
			int score, JLabel frogLabel, JLabel[][] carLabel, JLabel[][] logLabel, JButton restartButton ) {
		this.s = Socket;
		this.frog = frog;
		this.log = log;
		this.car = car;
		this.score = score;
		this.frogLabel = frogLabel;
		this.carLabel = carLabel;
		this.logLabel = logLabel;
	}
	
	public void run() {
		
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
			
		} catch (IOException e){
			e.printStackTrace();
			
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//processing the requests
	public void processRequest () throws IOException {
		
		//if next request is empty then return
		while(true) {
			
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
		
	}
	
	public void executeCommand(String command) throws IOException{
		
		if ( command.equals("GETFROG") ) {
			
			int x = in.nextInt();
			int y = in.nextInt();
			
			System.out.println("X: " + x + "Y: " + y );
			
			frog.setX(x);
			frog.setY(y);
			
			frogLabel.setLocation( frog.getX() , frog.getY() );
			
			return;
			
		} else if ( command.equals("STARTGAME") ) {
			
			//check the start game function in gameprep for reference
			
			return;
			
			
		} else if ( command.equals("WINGAME") ) {
				
			//check the WIN game function in gameprep for reference
				
			return;
		
			
		} else if ( command.equals("LOSEGAME") ) {
			
			//check the LOSE game function in gameprep for reference
				
			return;
				
		} else if ( command.equals("GETCAR") ) {
			
			for ( int i = 0; i < car.length; i++ ) {
				for ( int j = 0; j < car[i].length; j++ ) {
					
					if (!in.hasNextInt()) {
						//To skip the GETCAR part of the command string when looping through,
						//otherwise the nextInt() below will hit a string and errors out
						//This took an hour to figure out
						String skip = in.next();
					}
					
					int x = in.nextInt();
					int y = in.nextInt();
					
					car[i][j].setX(x);
					car[i][j].setY(y);
					
					carLabel[i][j].setLocation( car[i][j].getX(), car[i][j].getY() );
					
				}
			}
			
			//car[i][j]
			
			return;
			
		} else if ( command.equals("GETLOG") ) {
			
			//open a socket to client
			//.....
			
			return;
			
		}
		
		
		
		/*
		if ( command.equals("PLAYER")) {
			
			int playerNo = in.nextInt();
			String playerAction = in.next();
			System.out.println("Player "+playerNo+" moves "+playerAction);
			
			
			//send a response
			Socket s2 = new Socket("localhost", CLIENT_PORT);
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String commandOut = "PLAYER "+playerNo+" POSTION 500 400\n";
			System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();
				
			s2.close();

		}
		*/
	}
	
}