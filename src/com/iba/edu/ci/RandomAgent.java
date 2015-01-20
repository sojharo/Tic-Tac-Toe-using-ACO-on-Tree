package com.iba.edu.ci;
import java.util.ArrayList;
import java.util.Random;


public class RandomAgent extends Agent {
	char mySign;
	
	
	public RandomAgent(char sign){
		mySign=sign;
		
	}
	public State pickMove(State currentBoard){
		ArrayList<State> nextPossibleList=new ArrayList<State>();
		nextPossibleList=currentBoard.getNextstate(mySign);
		
		Random rand=new Random();
		int randIndex=rand.nextInt(nextPossibleList.size());
		State nextState=nextPossibleList.get(randIndex);
		
		return nextState;}

	
/*	public ArrayList<State> getSuccessorStates(State currentBoard) {
		
		
		return null; 
	
	
	}
	
	*/
}
