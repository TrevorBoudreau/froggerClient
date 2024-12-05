//Trevor Boudreau, w0483725
//prog2200/3288 2024

package froggerGame;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//Note: due to the way this game implements collision detection with key release, some unexpected errors
//		may occur if key is tapped too quickly, or if the key is held in.

public class gamePrepClient extends JFrame implements KeyListener, ActionListener {
	
	final int SERVER_PORT = 5556;
	final int CLIENT_PORT = 5555;
	
	//GUI elements
	//sprites, labels, icons
	private Container content;
	private frogSprite frog;
	private logSprite log[][];
	private carSprite car[][];
	private JLabel backgroundLabel, frogLabel, carLabel[][], logLabel[][];
	private ImageIcon frogImage, carImage, carImageFlipped, logImage, backgroundImage;
	//button
	private JButton restartBtn;
	//score label
	private JLabel scoreLabel;
	//score class
	//private scoreSQL scoreDB;
	int score = 0;
	
	public gamePrepClient() throws IOException {
		
		//set up screen display
		setSize(gameProperties.SCREEN_WIDTH, gameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		content.setBackground(Color.gray);
		setLayout(null);

		//display background graphic
		backgroundImage = new ImageIcon( getClass().getResource(gameProperties.BACKGROUND_IMAGE ) );
		backgroundLabel = new JLabel();
		backgroundLabel.setIcon( backgroundImage );
		backgroundLabel.setSize( gameProperties.SCREEN_WIDTH, gameProperties.SCREEN_HEIGHT );
		backgroundLabel.setLocation( 0, 0 );
		
		//set up frog sprite
		frog = new frogSprite(400, 800, 100, 90, gameProperties.FROG_IMAGE);
		frogLabel = new JLabel();
		frogImage = new ImageIcon( getClass().getResource( frog.getImage() ) );
		frogLabel.setIcon( frogImage ); 
		frogLabel.setSize( frog.getWidth(), frog.getHeight() );
		frogLabel.setLocation( frog.getX(), frog.getY() );
		
		//set up a multi-array of car sprites
		carImage = new ImageIcon( getClass().getResource(gameProperties.CAR_IMAGE) ) ;
		carImageFlipped = new ImageIcon( getClass().getResource(gameProperties.CAR_IMAGE_FLIPPED) );
		car = new carSprite[4][3];
		carLabel = new JLabel[4][3];
			//will loop through all the cars
		for ( int i = 0; i < car.length; i++ ) {
			int temp = 300;//temp local variable for adjusting height during car initialization
			
			for ( int j = 0; j < car[i].length; j++ ) {
				
				car[i][j] = new carSprite( (i * 300), gameProperties.SCREEN_HEIGHT - temp, 100, 100, gameProperties.CAR_IMAGE, false);
				car[i][j].setFrog(frog);
				carLabel[i][j] = new JLabel();
				
				if (j != 1 ) {
					car[i][j].setStepSpeed(gameProperties.STEP_FAST);
					car[i][j].setStepDirection(1);
					carLabel[i][j].setIcon(carImage);
				} else {
					car[i][j].setStepSpeed(gameProperties.STEP_SLOW);
					car[i][j].setStepDirection(2);
					carLabel[i][j].setIcon(carImageFlipped);
				}
				
				carLabel[i][j].setSize( car[i][j].getWidth(), car[i][j].getHeight() );
				carLabel[i][j].setLocation( car[i][j].getX(), car[i][j].getY() );
				
				car[i][j].setCarLabel( carLabel[i][j] );
				car[i][j].setFrogLabel(frogLabel);
				
				temp += 100;
			}
		}	
		
		//set up a multi-array of log sprites
		log = new logSprite[4][3];
		logLabel = new JLabel[4][3];
		logImage = new ImageIcon(getClass().getResource(gameProperties.LOG_IMAGE));
		//loop through all logs
		for ( int i = 0; i < log.length; i++ ) {
			int temp = 700;//temp local variable for adjusting height during log initialization
			
			for ( int j = 0; j < log[i].length; j++ ) {
				log[i][j] = new logSprite( (i * 300), gameProperties.SCREEN_HEIGHT - temp, 100, 100, gameProperties.LOG_IMAGE, false);
				log[i][j].setFrog(frog);
				logLabel[i][j] = new JLabel();
				if (j != 1 ) {
					log[i][j].setStepSpeed(gameProperties.STEP_FAST);
					log[i][j].setStepDirection(1);
					logLabel[i][j].setIcon(logImage);
				} else {
					log[i][j].setStepSpeed(gameProperties.STEP_SLOW);
					log[i][j].setStepDirection(2);
					logLabel[i][j].setIcon(logImage);
				}
				
				logLabel[i][j].setSize(log[i][j].getWidth(), log[i][j].getHeight());
				logLabel[i][j].setLocation(log[i][j].getX(), log[i][j].getY());
				
				log[i][j].setLogLabel(logLabel[i][j]);
				log[i][j].setFrogLabel(frogLabel);
				
				temp += 100;
			}
		}
		
		//set up restart button
		restartBtn = new JButton("Continue?");
		restartBtn.setSize(100,100);
		restartBtn.setLocation(gameProperties.SCREEN_WIDTH - 150, gameProperties.SCREEN_HEIGHT - 175);
		restartBtn.setBackground(Color.BLACK);
		restartBtn.setForeground(Color.GREEN);
		restartBtn.setFocusable(false);
		restartBtn.setVisible(false);
		restartBtn.addActionListener(this);
		
		//set up score label
		//score = scoreDB.getScore();
		scoreLabel = new JLabel("Score: ");
		scoreLabel.setSize( 100, 50 );
		scoreLabel.setOpaque(true);
		scoreLabel.setForeground(Color.GREEN);
		scoreLabel.setBackground(Color.BLACK);
		scoreLabel.setLocation(gameProperties.SCREEN_WIDTH - 200, gameProperties.SCREEN_HEIGHT - 990 );
		
		
		content.addKeyListener(this);
		content.setFocusable(true);
		
		//populate game screen with sprites
		// !!ORDER MATTERS!!
		add(frogLabel);
		for ( int i = 0; i < car.length; i++ ) {
			for ( int j = 0; j < car[i].length; j++ ) {
				add( carLabel[i][j] );
			}
		}
		for ( int i = 0; i < log.length; i++ ) {
			for ( int j = 0; j < log[i].length; j++ ) {
				add( logLabel[i][j] );
			}
		}
		add(restartBtn);
		add(scoreLabel);
		add(backgroundLabel);

		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				
				synchronized (this) {
					
					ServerSocket client;
					
					try {
						
						client = new ServerSocket(CLIENT_PORT);
						
						while (true) {
							
							Socket s2;
							
							try {
								
								s2 = client.accept();
								ClientService myService = new ClientService (s2, frog, log, car,
										score, frogLabel, carLabel, logLabel, restartBtn, content, scoreLabel);
								Thread t2 = new Thread(myService);
								t2.start();
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Waiting for server responses...");

				}
			}
			
		});
		t1.start( );
		
		Thread frogThread = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized (this) {
					try {

						while (true) {
							
							try {
								Socket s = new Socket("localhost", SERVER_PORT);
								//Initialize data stream to send data out
								OutputStream outstream = s.getOutputStream();
								PrintWriter out = new PrintWriter(outstream);
								
								String command = "GETFROG\n";
								out.println(command);
								out.flush();
								s.close();
								
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Thread.sleep(500);
							
						}
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

				}
			}
		});
		frogThread.start();
		
		Thread carThread = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized (this) {

					try {

						while (true) {

							try {
								Socket s = new Socket("localhost", SERVER_PORT);
								//Initialize data stream to send data out
								OutputStream outstream = s.getOutputStream();
								PrintWriter out = new PrintWriter(outstream);
								
								String command = "GETCAR\n";
								out.println(command);
								out.flush();
								s.close();
								
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Thread.sleep(500);
							
						}
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

				}
			}
		});
		carThread.start();
		
		Thread logThread = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized (this) {

					try {
						
						while (true) {

							try {
								Socket s = new Socket("localhost", SERVER_PORT);
								//Initialize data stream to send data out
								OutputStream outstream = s.getOutputStream();
								PrintWriter out = new PrintWriter(outstream);
								
								String command = "GETLOG\n";
								out.println(command);
								out.flush();
								s.close();
								
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Thread.sleep(500);
							
						}
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

				}
			}
		});
		logThread.start();

		gameStart();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		gamePrepClient newGame = new gamePrepClient();
		newGame.setVisible(true);
		// KEEP MAIN TO THESE 2 LINES!! //
	}
	
	public void gameStart() {
		
		//set up a communication socket
		Socket s;
		
		try {
			s = new Socket("localhost", SERVER_PORT);
			//Initialize data stream to send data out
			OutputStream outstream = s.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);
			
			String command = "STARTGAME\n";
			System.out.println("Sending: " + command);
			out.println(command);
			out.flush();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//set up a communication socket
		Socket s;
			
		//new x or y for each direction key (UP, DOWN, LEFT, RIGHT)
		if ( e.getKeyCode()==KeyEvent.VK_UP) {
				
			//MOVEFROG UP\n
			
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "MOVEFROG\n";
				String direction = "UP\n";
				System.out.println("Sending: " + command + direction);
				out.println(command + direction);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		} else if ( e.getKeyCode()==KeyEvent.VK_DOWN) {
				
			//MOVEFROG DOWN\n
			
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "MOVEFROG\n";
				String direction = "DOWN\n";
				System.out.println("Sending: " + command + direction);
				out.println(command + direction);
				out.flush();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		} else if ( e.getKeyCode()==KeyEvent.VK_LEFT) {
		
			//MOVEFROG LEFT/n
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "MOVEFROG\n";
				String direction = "LEFT\n";
				System.out.println("Sending: " + command + direction);
				out.println(command + direction);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		} else if ( e.getKeyCode()==KeyEvent.VK_RIGHT) {
				
			//MOVEFROG RIGHT/n 
			
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "MOVEFROG\n";
				String direction = "RIGHT\n";
				System.out.println("Sending: " + command + direction);
				out.println(command + direction);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		} else {

			return;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	
		Socket s;
		
		//END GAME WHEN ENDZONE IS REACHED
		if (frog.getY() < 100) {
			
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "WINGAME\n";
				System.out.println("Sending: " + command);
				out.println(command);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		//isMoving is calculated on server side and passed back to the client in GETCAR
		for ( int i = 0; i < car.length; i++ ) {
			for ( int j = 0; j < car[i].length; j++ ) {
			
				if ( car[i][j].getIsMoving() == false ) {
					try {
						s = new Socket("localhost", SERVER_PORT);
						//Initialize data stream to send data out
						OutputStream outstream = s.getOutputStream();
						PrintWriter out = new PrintWriter(outstream);
						
						String command = "LOSEGAME\n";
						System.out.println("Sending: " + command);
						out.println(command);
						out.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		}
		
		//IF FROG IS NOT INTERSECTING WITH LOG, END GAME
		//isIntersecting is calculated on server side and passed back to the client in GETLOG\n
		//temp variable to break out of nested loop
		boolean breakOut = false;
		//temp variable to flag if one log is intersecting
		boolean intersect = false;
		for ( int i = 0; i < log.length; i++ ) {
			for ( int j = 0; j < log[i].length; j++ ) {
						
						
				if (log[i][j].isIntersecting == true ) {	
					intersect = true;
					breakOut = true;
					break;
				}
				if (breakOut == true) { break; }
			}
		}	
		if (intersect != true) {
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "LOSEGAME\n";
				System.out.println("Sending: " + command);
				out.println(command);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("btn clicked");
		
		if (e.getSource() == restartBtn){
			
			try {
				Socket s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				String command = "STARTGAME\n";
				out.println(command);
				out.flush();
				s.close();
					
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}
}
 