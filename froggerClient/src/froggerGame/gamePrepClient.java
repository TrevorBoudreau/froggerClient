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
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
	private scoreSQL scoreDB;
	int score = 0;
	
	public gamePrepClient() {
		
		//set up screen display
		setSize(gameProperties.SCREEN_WIDTH, gameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		content.setBackground(Color.gray);
		setLayout(null);
		
		//set up score db
		scoreDB = new scoreSQL();
		scoreDB.createDB();
		
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
		score = scoreDB.getScore();
		scoreLabel = new JLabel("Score: " + scoreDB.getScore(), SwingConstants.CENTER);
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
		
		//start car and log threads
		
		/*
		for ( int i = 0; i < car.length; i++ ) {
			for ( int j = 0; j < car[i].length; j++ ) {
				car[i][j].runThread();
			}
		}
		for ( int i = 0; i < log.length; i++ ) {
			for ( int j = 0; j < log[i].length; j++ ) {
				log[i][j].runThread();
			}
		}
		
		*/
		
		
		//client listening server that passes off all variables
		//to client service (arrays, label arrays, frog, frog label, restart button, ...etc.)
		//anything needs to be manipulated based on feedback from server.
		
		//set up server
		//create listening thread with infinite while loop
		
		//set up threads for:
		//requests for GETFROG, GETCAR, GETLOG
		
		Thread t1 = new Thread ( new Runnable () {
			
			public void run ( ) {
				
				synchronized(this) {
					
					ServerSocket client;
					
					try {
						
						client = new ServerSocket(CLIENT_PORT);
						
						while (true) {
							
							Socket s2;
							try {
								
								s2 = client.accept();
								ClientService myService = new ClientService (s2, frog, log, car,
										CLIENT_PORT, backgroundLabel, carLabel, carLabel, restartBtn);
								Thread t2 = new Thread(myService);
								t2.start();
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							System.out.println("client connected");
							
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
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		gamePrepClient newGame = new gamePrepClient();
		newGame.setVisible(true);
		// KEEP MAIN TO THESE 2 LINES!! //
	}
	
	public void gameWin() {
		
		/*
		
		System.out.println("GAME WIN");
		
		//stop ongoing threads
		for ( int i = 0; i < car.length; i++ ) {
			for ( int j = 0; j < car[i].length; j++ ) {
				car[i][j].stopThread();
			}
		}
		for ( int i = 0; i < log.length; i++ ) {
			for ( int j = 0; j < log[i].length; j++ ) {
				log[i][j].stopThread();
			}
		}
		
		//prevent player from moving
		content.setFocusable(false);
		
		//show visibility button
		restartBtn.setVisible(true);
		
		//update score
		scoreDB.addScore();
		
		 
		 */
	}
	
	public void gameLose() {
		
		/*
		
		System.out.println("GAME LOSE");
		
		//stop ongoing threads
		for ( int i = 0; i < car.length; i++ ) {
			for ( int j = 0; j < car[i].length; j++ ) {
				car[i][j].stopThread();
			}
		}
		for ( int i = 0; i < log.length; i++ ) {
			for ( int j = 0; j < log[i].length; j++ ) {
				log[i][j].stopThread();
			}
		}
		
		this.frogLabel.setIcon( new ImageIcon( getClass().getResource(gameProperties.FROG_DEAD_IMAGE) ) );	
				
		//prevent player from moving
		content.setFocusable(false);
				
		//show visibility button
		restartBtn.setVisible(true);
		
		//update score
		scoreDB.minusScore();
		
		*/
	}
	
	public void gameStart() {
		
		/*
		
		//let frog be controllable
		content.setFocusable(true);
		content.requestFocusInWindow();  //DOES NOT WORK WITHOUT THIS LINE!!
		
		//hide visibility button
		restartBtn.setVisible(false);
		
		//reset frogs position to start
		frog.setX(400);
		frog.setY(800);
		frogLabel.setLocation(frog.getX(), frog.getY());
		
		//restart threads for cars and logs
		for ( int i = 0; i < car.length; i++ ) {
			int temp = 300;//temp local variable for adjusting height during car initialization
			
			for ( int j = 0; j < car[i].length; j++ ) {
				car[i][j].setX(i * 300);
				car[i][j].setY(gameProperties.SCREEN_HEIGHT - temp);
				car[i][j].setFrog(frog);
				car[i][j].setFrogLabel(frogLabel);
				
				carLabel[i][j].setLocation( car[i][j].getX(), car[i][j].getY() );
				
				car[i][j].runThread();
				
				temp += 100;
			}
		}
		for ( int i = 0; i < log.length; i++ ) {
			int temp2 = 700;//temp local variable for adjusting height during log initialization
			
			for ( int j = 0; j < car[i].length; j++ ) {
				log[i][j].setX(i * 300);
				log[i][j].setY(gameProperties.SCREEN_HEIGHT - temp2);
				log[i][j].setFrog(frog);
				log[i][j].setFrogLabel(frogLabel);
				log[i][j].setIntersecting(true);
				
				logLabel[i][j].setLocation( log[i][j].getX(), log[i][j].getY() );
				
				log[i][j].runThread();
				
				temp2 += 100;
			}
		}
		
		frogLabel.setIcon( frogImage );
		
		score = scoreDB.getScore();
		scoreLabel.setText("Score: " + score);
		
		*/
		
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		//current x and y of frog before step
		int x = frog.getX();
		int y = frog.getY();
			
		//new x or y for each direction key (UP, DOWN, LEFT, RIGHT)
		if ( e.getKeyCode()==KeyEvent.VK_UP) {
				
			//MOVEFROG UP\n
				
		} else if ( e.getKeyCode()==KeyEvent.VK_DOWN) {
				
			//MOVEFROG DOWN\n
				
		} else if ( e.getKeyCode()==KeyEvent.VK_LEFT) {
		
			//MOVEFROG LEFT/n
				
		} else if ( e.getKeyCode()==KeyEvent.VK_RIGHT) {
				
			//MOVEFROG RIGHT/n 
				
		} else {
				
			
			return;
		}
			
		//move the frog to new spot with new x and y
		//frog.setX(x);
		//frog.setY(y);
			
		//System.out.println("frog x: " + frog.getX() + " hitbox x: " + frog.getHitboxX() + "frog y: " + frog.getY() + " hitbox y: " + frog.getHitboxY());	
			
		//move the label with it
		//frogLabel.setLocation( frog.getX() , frog.getY() );
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	
		/*
		
		//END GAME WHEN ENDZONE IS REACHED
		if (frog.getY() < 100) {
			
			gameWin();
			
		} else {
			
			//IF ANd CAR HAS STOPPED, END GAME
			//temp variable to break out of nested loop
			boolean breakOut = false;
			//temp variable to flag if one log is intersecting
			boolean collision = false;
			//temp variable to flag if one log is intersecting
			boolean intersect = false;
			
			for ( int i = 0; i < car.length; i++ ) {
				for ( int j = 0; j < car[i].length; j++ ) {
						
					if ( car[i][j].getIsMoving() == false ) {
						collision = true;
						breakOut = true;
						break;
					}
				
							
					if (breakOut == true) { break; }
				}
			}
					
			//IF FROG IS NOT INTERSECTING WITH LOG, END GAME
			for ( int i = 0; i < log.length; i++ ) {
				for ( int j = 0; j < log[i].length; j++ ) {
							
							
					if (log[i][j].isIntersecting() == true ) {
								
						intersect = true;
								
						breakOut = true;
						break;
					}
						
					if (breakOut == true) { break; }
				}
			}
					
			if (intersect != true) {
				gameLose();
			}
			
			if (collision == true) {
				gameLose();
			}
			
		}
		
		*/
			
			
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("btn clicked");
		
		if (e.getSource() == restartBtn){
			
			//STARTGAME\n
			
			
			
			
			
		}
		
	}
		
	

}
 