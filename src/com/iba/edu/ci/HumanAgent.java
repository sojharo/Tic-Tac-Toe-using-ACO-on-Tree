package com.iba.edu.ci;
import java.util.Scanner;


public class HumanAgent extends Agent {
	
	char mySign;

	public HumanAgent(char sign){
		mySign=sign;
		
	}
	public State pickMove(State currentBoard){
		
		State nextState=new State(currentBoard.state);
		Scanner x=new Scanner(System.in);
		
		int position=x.nextInt();
		boolean validMove=false;
		while(!validMove){
		while(position>9){
			System.out.println("Invalid move");
			position=x.nextInt();
		}
	
		switch(position)
		{
		case 1: if(nextState.state[0][0]=='.') nextState.state[0][0]=mySign;validMove=true;break;
		case 2: if(nextState.state[0][1]=='.') nextState.state[0][1]=mySign;validMove=true;break;
		case 3: if(nextState.state[0][2]=='.') nextState.state[0][2]=mySign;validMove=true;break;
		case 4: if(nextState.state[1][0]=='.') nextState.state[1][0]=mySign;validMove=true;break;
		case 5: if(nextState.state[1][1]=='.') nextState.state[1][1]=mySign;validMove=true;break;
		case 6: if(nextState.state[1][2]=='.') nextState.state[1][2]=mySign;validMove=true;break;
		case 7: if(nextState.state[2][0]=='.') nextState.state[2][0]=mySign;validMove=true;break;
		case 8: if(nextState.state[2][1]=='.') nextState.state[2][1]=mySign;validMove=true;break;
		case 9: if(nextState.state[2][2]=='.') nextState.state[2][2]=mySign;validMove=true;break;
		default: System.out.println("Invalid move");break;
		}
		}
		
				return nextState;
		
		
	}
}
