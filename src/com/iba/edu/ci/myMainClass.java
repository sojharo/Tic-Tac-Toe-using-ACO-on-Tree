package com.iba.edu.ci;


import java.util.ArrayList;

import com.gaurav.tree.ArrayListTree;
import com.gaurav.tree.NodeNotFoundException;
import com.gaurav.tree.Tree;

public class myMainClass {
	

	public static final char SYMBOL_X = 'X';
	public static final char SYMBOL_O = 'O';
	static int totalGames=500;
	static int totalWins=0;
	static int totalLosses=0;
	static int totalTies=0;
	

	
	public static void main(String[] args) {
		
		char[][] firstState={{'O','.','.'},{'.','.','.'},{'.','.','.'}};
		State s=new State(firstState);
		
		RandomAgent randomAgent=new RandomAgent('X');
		
		ACOagent acoAgent=new ACOagent('O');
		
		HumanAgent humanAgent=new HumanAgent('X');
		
		//Games g=new Game(s);
		Game g[]=new Game[totalGames];
		
		for(int i=0;i<g.length;i++)
		{
			g[i]=new Game(s);
			
			g[i].initialiseAgents(randomAgent,acoAgent);
			g[i].play();
			totalLosses+=g[i].losses;
			totalTies+=g[i].ties;
			totalWins+=g[i].wins;
			System.out.println("ACO agent "+g[i].toString());	
			
		}
		
		System.out.println("Results \n Total Games: "+totalGames+" Wins: "+totalWins+" Lost: "+totalLosses+" Draw: "+totalTies+"\n");
		
		
		/*Game g=new Game(s);
		
		RandomAgent randomAgent=new RandomAgent('X');
		ACOagent acoAgent=new ACOagent('O');

		g.initialiseAgents(randomAgent,acoAgent);
		g.play();
		System.out.println("ACO agent "+g.toString());
		*/

	}

}
