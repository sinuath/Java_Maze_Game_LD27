package Maze;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Cat {
	
	private int tileX, tileY;
	
	private Image cat; 
	
	public Cat(){	
	
	ImageIcon img = new ImageIcon("Images/cat.png");
	cat = img.getImage();
	
	tileX = 0;
	tileY = 2;
	}
	
	public Image getCat(){
		return cat;
	}
	
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}
	
	public void move(int dx, int dy){
		
		tileX += dx;
		tileY += dy;
	}
}
