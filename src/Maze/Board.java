package Maze;

import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;



public class Board extends JPanel implements ActionListener{
	private Timer timer;
	
	private Map m;
	
	private String Debug = "        W=Up A=Left S=Down D=Right            ~ Get the Cheese!";
	
	private boolean win = false;
	private boolean ingame = true;
	private boolean cat = false;
	private boolean[] OpenDirections = new boolean[4];
	private int[] ViableOptionsArray = new int[3];
	private int ViableOptions = 0;
	private int FrontCat = 2;
	private int BackCat = 0;
	private int OpenDirectionsCount;
	private int GameTime = 0;
	private int GameTick = 0;
	private int X_Tiles = 28;
	private int Y_Tiles = 29;
	private int GameTheme = 10;

	
	private Player p;
	private Cat c;
	
	private Font font = new Font("Serif", Font.BOLD, 30);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public Board(){
		m = new Map();
		p = new Player();
		c = new Cat();
		addKeyListener(new Al());
		setFocusable(true);
		

		timer = new Timer(25, this);
		timer.start();
	}
	
	
	public void actionPerformed(ActionEvent e){
		GameTick++;
		
		if((GameTime < GameTheme) && ((GameTick % 40) == 1)){
			GameTime++;
			if(GameTime == GameTheme)
			{
				cat=true;
				c.move(1, 0);
				if((p.getTileX() == c.getTileX()) && ( p.getTileY() == c.getTileY())){
					ingame = false;
				}
			}
		}
		
		if((cat)&&((GameTick % 10) == 1)){
			MoveCat();
		}
		 
		if(m.getMap(p.getTileX(), p.getTileY()).equals("f")){
			win = true;
			ingame = false;
		}else if((p.getTileX() == c.getTileX()) && ( p.getTileY() == c.getTileY())){
			ingame = false;
		}
		
		repaint();
		
		
		
	}


	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
		if(ingame){
			for(int y=0; y<Y_Tiles; y++){
				for(int x=0; x<X_Tiles; x++){
					if(m.getMap(x,y).equals("f")){
						g.drawImage(m.getFinish(), x * 16, y * 16, null);
					}
					if(m.getMap(x, y).equals(".")){
						g.drawImage(m.getGrass(), x*16, y*16, this);
					}
					if(m.getMap(x, y).equals("x")){
						g.drawImage(m.getWall(), x*16, y*16, null);
					}
					if(m.getMap(x, y).equals("s")){
						g.drawImage(m.getScore(), x*16, y*16, null);
					}
					
				}
			}

			g.drawImage(c.getCat(), c.getTileX() * 16, c.getTileY() * 16, null);	

			
			g.drawImage(p.getPlayer(), p.getTileX() * 16, p.getTileY() * 16, null);	

			g.drawString("Head Start: "+String.valueOf(GameTheme-GameTime)+" "+Debug, 21, 12);

		}else
		{
		if(!win){
			g.setColor(Color.red);
			setBackground(Color.black);
			g.setFont(font);
			g.drawString("Fatality!", 140, 140);
		}
		if(win){
			g.setColor(Color.green);
			setBackground(Color.black);
			g.setFont(font);
			g.drawString("Congratulations! You Won!", 60, 140);
		}
		}
		
		
	}
	
	public void MoveCat(){
		OpenDirectionsCount = 0;
		
		for(int i=0;i<4;i++){
			OpenDirections[i]=false;
		}
		
		if(!m.getMap(c.getTileX(), c.getTileY() - 1).equals("x")){  // Up = 1
			OpenDirections[1]=true;
			OpenDirectionsCount++;
		}
		if(!m.getMap(c.getTileX(), c.getTileY() + 1).equals("x")){ // Down = 3
			OpenDirections[3]=true;
			OpenDirectionsCount++;
		}
		if(!m.getMap(c.getTileX() - 1, c.getTileY()).equals("x")){ // Left = 0
			OpenDirections[0]=true;
			OpenDirectionsCount++;
		}
		if(!m.getMap(c.getTileX() + 1, c.getTileY()).equals("x")){ // Right = 2
			OpenDirections[2]=true;
			OpenDirectionsCount++;
		}
		

		/*
		 ** HERE LIES THE CAT BRAIN **
		 * */


		if(OpenDirectionsCount == 1){
			for(int i=0;i<4;i++){
				if(OpenDirections[i]==true)
				{
					FrontCat = i;
					MoveCat(FrontCat);
				}
			}
		}else if((OpenDirections[FrontCat]) && (OpenDirectionsCount < 3)){
			
			MoveCat(FrontCat);
		}else{
			TurnCat();
			MoveCat(FrontCat);
		}
	}
	
	public void MoveCat(int direction){ // the only reason this function has a parameter was so i could use the same name.
		switch(direction){
		case 0://left
			c.move(-1, 0);
			break;
		case 1: // Up
			c.move(0, -1);
			break;
		case 2: // Right
			c.move(1, 0);
			break;
		case 3: // Down
			c.move( 0, 1);
			break;
		}
		
	}
	
	public void TurnCat(){
		
		ViableOptions = 0;
		
		switch(FrontCat){ // Calculates the back of the cat(we need it for later)
		case 0://left
			BackCat=2;
			break;
		case 1: // Up
			BackCat=3;
			break;
		case 2: // Right
			BackCat=0;
			break;
		case 3: // Down
			BackCat=1;
			break;
		}
		
		
		for(int i=0;i<4;i++){ // Goes through every open direction and saves them to an array
			if(OpenDirections[i]==true)
			{
				if(i != BackCat){ // Making sure the Cat doesn't go the way it came
					ViableOptionsArray[ViableOptions] = i;
					ViableOptions++;
				}
			}
		}
		

		if(ViableOptions != 0)
		{
			Random u = new Random(System.currentTimeMillis()); // For the actual game
			FrontCat = ViableOptionsArray[u.nextInt(ViableOptions)]; // Makes a random number between 0 and how many options the cat has.

			repaint();
		}else{
			FrontCat = ViableOptionsArray[ViableOptions];

		}
		
	}
	
	
	public class Al extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			
			if(keycode == KeyEvent.VK_W){ // UP
				if(!m.getMap(p.getTileX(), p.getTileY() - 1).equals("x")){
					p.move(0, -1);
				}
			}
			if(keycode == KeyEvent.VK_S){ // Down
				if(!m.getMap(p.getTileX(), p.getTileY() + 1).equals("x")){
					p.move( 0, 1);
				}
			}
			if(keycode == KeyEvent.VK_A){ // Left
				if(!m.getMap(p.getTileX() - 1, p.getTileY()).equals("x")){
					p.move(-1, 0);
				}
			}
			if(keycode == KeyEvent.VK_D){ // Right
				if(!m.getMap(p.getTileX() + 1, p.getTileY()).equals("x")){
					p.move(1, 0);
				}
			}
		}
		
		public void keyReleased(KeyEvent e){
			
		}
		public void keyTyped(KeyEvent e){
			
		}
	}
}
