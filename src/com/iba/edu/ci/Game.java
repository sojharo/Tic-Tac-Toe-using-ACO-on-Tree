package com.iba.edu.ci;

public class Game {
	//first state
	//char[][] firstState={{'O',',',','},{',','.','.'},{',',',','.'}};

	//current board
	State currentBoard;
	
	// each match consists of a number of games between two agents.
	//private int numGames;
	private Agent humanAgent;
	private Agent randomAgent;
			
	// Store gameResults for data post-processing
	private int score=0;
			
	// game results
	public int wins=0;
	public int losses=0;
	public int ties=0;
	int turn=0;//my turn
			
	public static final char SYMBOL_X = 'X';
	public static final char SYMBOL_O = 'O';
		
	public Game(State initialState)
	{
		currentBoard=initialState;
	}
	
	public void play(){
		
		
	System.out.print(currentBoard.toString());
		
		while(!currentBoard.isWinningState()&&!currentBoard.isFull()){
		
			System.out.println();
			advance(turn);
			System.out.print(currentBoard.toString());
			System.out.println();
		
		
		}
		
			
		if(currentBoard.isDraw(ACOagent.SYMBOL_O)){ties++;}
		if(currentBoard.isMyLosingState(ACOagent.SYMBOL_O)){losses++;}
		if(currentBoard.isMyWinningState(ACOagent.SYMBOL_O)){wins++;}
		
	//	System.out.println("ACO agent: "+toString());
		
		
	}
	
	public void initialiseAgents(Agent humanAgent,Agent randomAgent)
	{
		this.randomAgent=randomAgent;
		this.humanAgent=humanAgent;
	}
	
	public void advance(int turn)
	{
		State nextGameState;
		if(turn==0)
			nextGameState=new State(humanAgent.pickMove(currentBoard));
			else
				nextGameState=new State(randomAgent.pickMove(currentBoard));
		
		currentBoard=new State(nextGameState);
		
		flipTurn();
		
	}
	
	public void upDateScore(){
		
	}
	
	public void flipTurn(){
		if(turn==0)
			turn=1;
		else
			turn=0;
	}
	
	
	
	public String toString(){
		
		
		
		return "Wins: "+wins+" Lost: "+losses+" Draw: "+ties+"\n";
	}
	
	
	
	
}
