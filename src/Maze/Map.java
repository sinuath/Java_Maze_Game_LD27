package Maze;

import java.awt.Image;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
	
	private int Y_Tiles = 29;
	private Scanner m;
	private String Map[] = new String[Y_Tiles]; // however many tiles going down
	
	private Image grass,
					finish,
					score,
					wall;
	
	public Map(){
		
		ImageIcon img = new ImageIcon("Images/grass2.png");
		grass = img.getImage();
	    img = new ImageIcon("Images/wall2.png");
		wall = img.getImage();
		img = new ImageIcon("Images/finish2.png");
		finish = img.getImage();
		img = new ImageIcon("Images/score.png");
		score = img.getImage();
		openFile();
		readFile();
		closeFile();
	}
	
	public Image getGrass(){
		return grass;
	}
	
	public Image getWall(){
		return wall;
	}
	
	public Image getScore(){
		return score;
	}
	
	public Image getFinish(){
		return finish;
	}
	
	public String getMap(int x, int y){
		String index = Map[y].substring(x, x + 1);
		return index;
	}
	
	public void openFile(){
		
		try{
		m = new Scanner(new File("Images/Map.txt"));
		}catch(Exception e){
			System.out.println("error loading map");
		}
	}
	public void readFile(){
		while(m.hasNext()){
			for(int i=0;i<Y_Tiles; i++){
				Map[i] = m.next();
			}
		}
	}
	public void closeFile(){
		m.close();
	}
}
