package com.iba.edu.ci;



import java.util.ArrayList;
import java.util.Random;

public class State {
	
	char [][]state=new char[3][3];
	public final int Dim=3;
	
	String uniqueId;
	
	double tou;
	
	int weight;
	
	public State(char [][]newstate){
		
		uniqueId = "";
		
		for(int i=0; i<10; i++){
			
			if(new Random().nextInt(9) < 6)
				uniqueId += ""+ new Random().nextInt(9);
			else
				uniqueId += ""+ new Random().nextInt(20);
		}
		
		tou = 0;
		
		for(int i=0;i<Dim;i++)
		{
			for(int j=0;j<Dim;j++)
			{
				state[i][j]=newstate[i][j];
			}
		}
	}
	
	public State(State s)
	{
		
		uniqueId = "";
		
		for(int i=0; i<10; i++){
			
			if(new Random().nextInt(9) < 6)
				uniqueId += ""+ new Random().nextInt(9);
			else
				uniqueId += ""+ new Random().nextInt(20);
		}
		
		tou = 0;
		
		for(int i=0;i<Dim;i++)
		{
			for(int j=0;j<Dim;j++)
			{
				this.state[i][j]=s.state[i][j];
			}
			
		}
	}
	
	public void setTou(double tow){
		this.tou = tow;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public String getId(){
		return uniqueId;
	}
	
	public double getTou(){
		return tou;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public Boolean isFull()
	{
		int count = 0;
		for(int i=0;i<Dim;i++)
		{
			for(int j=0;j<Dim;j++)
			{
				if(this.state[i][j] == '.')
					count++;
			}
		}
		
		return (count > 0) ? false : true;
	}
	
	public ArrayList<State> getNextstate(char sign)
	{
		ArrayList<State> nextStates=new ArrayList<State>();
		State temp;
		
		
		for(int i=0;i<Dim;i++)
		{
			for(int j=0;j<Dim;j++)
			{
				if(this.state[i][j]=='.')
					
				{	temp=new State(this);
					temp.state[i][j]=sign;
					nextStates.add(temp);
				}
					
			}
			
		}
		return nextStates;
		
	}
	
	public boolean isWinningState(){
	
		
			
		return checkMatch(0,0, 0,1, 0,2, 'X') || checkMatch(1,0, 1,1, 1,2, 'X') || checkMatch(2,0, 2,1, 2,2, 'X') || // check all rows
				  checkMatch(0,0, 1,0, 2,0, 'X') || checkMatch(0,1, 1,1, 2,1, 'X') || checkMatch(0,2, 1,2, 2,2, 'X') || // check all columns
				  checkMatch(0,0, 1,1, 2,2, 'X') || checkMatch(2,0, 1,1, 0,2, 'X') ||							 // check both diagonals
				  
				  checkMatch(0,0, 0,1, 0,2, 'O') || checkMatch(1,0, 1,1, 1,2, 'O') || checkMatch(2,0, 2,1, 2,2, 'O') || // check all rows
				  checkMatch(0,0, 1,0, 2,0, 'O') || checkMatch(0,1, 1,1, 2,1, 'O') || checkMatch(0,2, 1,2, 2,2, 'O') || // check all columns
				  checkMatch(0,0, 1,1, 2,2, 'O') || checkMatch(2,0, 1,1, 0,2, 'O');							 // check both diagonals 
	}
	
	public boolean isMyWinningState(char mySign){
		
		return checkMatch(0,0, 0,1, 0,2, mySign) || checkMatch(1,0, 1,1, 1,2, mySign) || checkMatch(2,0, 2,1, 2,2, mySign) || // check all rows
				  checkMatch(0,0, 1,0, 2,0, mySign) || checkMatch(0,1, 1,1, 2,1, mySign) || checkMatch(0,2, 1,2, 2,2, mySign) || // check all columns
				  checkMatch(0,0, 1,1, 2,2, mySign) || checkMatch(2,0, 1,1, 0,2, mySign);							 // check both diagonals 
	}
	
	public boolean isMyLosingState(char mySign){
		
		if(mySign=='X')
			mySign='O';
		else
			mySign='X';
		
		return checkMatch(0,0, 0,1, 0,2, mySign) || checkMatch(1,0, 1,1, 1,2, mySign) || checkMatch(2,0, 2,1, 2,2, mySign) || // check all rows
				  checkMatch(0,0, 1,0, 2,0, mySign) || checkMatch(0,1, 1,1, 2,1, mySign) || checkMatch(0,2, 1,2, 2,2, mySign) || // check all columns
				  checkMatch(0,0, 1,1, 2,2, mySign) || checkMatch(2,0, 1,1, 0,2, mySign);							 // check both diagonals 
	}
	
	public boolean isStateGoodForOpponentWinning(char mySign){
		
		if(mySign=='X')
			mySign='O';
		else
			mySign='X';
		
		return checkMatchForTwoCells(0,0, 0,1, 0,2, mySign) || checkMatchForTwoCells(1,0, 1,1, 1,2, mySign) || checkMatchForTwoCells(2,0, 2,1, 2,2, mySign) || // check all rows
				checkMatchForTwoCells(0,0, 1,0, 2,0, mySign) || checkMatchForTwoCells(0,1, 1,1, 2,1, mySign) || checkMatchForTwoCells(0,2, 1,2, 2,2, mySign) || // check all columns
				checkMatchForTwoCells(0,0, 1,1, 2,2, mySign) || checkMatchForTwoCells(2,0, 1,1, 0,2, mySign);							 // check both diagonals 
	}
	
	public boolean isDraw(char mySign){
		
		
		if(isFull() && !this.isMyLosingState(mySign) && !this.isMyWinningState(mySign))
			return true;
			
		return false;
	}
	
	private boolean checkMatch(int space1, int space1_1, int space2, int space2_1, 
			int space3, int space3_1, char moveType){		
		
		if(state[space1][space1_1] == moveType && state[space2][space2_1] == moveType && 
				state[space3][space3_1] == moveType)
			return true;
		
		return false;
	}
	
	private boolean checkMatchForTwoCells(int space1, int space1_1, int space2, int space2_1, 
			int space3, int space3_1, char moveType){		
		char mySign;
		
		if(moveType=='X')
			mySign='O';
		else
			mySign='X';
		
		if(state[space1][space1_1] == moveType && state[space2][space2_1] == moveType && 
				state[space3][space3_1] == mySign)
			return true;
		if(state[space1][space1_1] == moveType && state[space2][space2_1] == mySign && 
				state[space3][space3_1] == moveType)
			return true;
		if(state[space1][space1_1] == mySign && state[space2][space2_1] == moveType && 
				state[space3][space3_1] == moveType)
			return true;
		
		return false;
	}
	
	public String toString(){
		String result="\n";
		for(int i=0;i<Dim;i++)
		{
			for(int j=0;j<Dim;j++)
			{
				result+=state[i][j]+"\t";
			}
			result+="\n";
		}
		return result;
	}
}
