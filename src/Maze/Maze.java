package Maze;

import java.awt.Color;

import javax.swing.JFrame;

public class Maze{

	public static void main(String[] args){
		new Maze();
	
	}
	
	public Maze(){
		int X_Tiles = 28;
		int Y_Tiles = 29;
		
		JFrame f = new JFrame();
		f.setTitle("Maze Game");
		f.setBackground(Color.black);
		f.add(new Board());
		f.setSize(X_Tiles * 16 +6, Y_Tiles * 16 + 28);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
