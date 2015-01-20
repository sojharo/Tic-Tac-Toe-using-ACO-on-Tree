package com.ci.sojharo.aco;

import java.util.ArrayList;

import com.gaurav.tree.NodeNotFoundException;
import com.gaurav.tree.Tree;
import com.iba.edu.ci.State;

public class Colony {
	
	ArrayList<Ant> colony;
	
	public final double P = 0.6;
	
	public Tree<State> treeSearchSpace;
	
	char sign;
	
	int citycount;
	
	public Colony(int size, Tree<State> treeSearchSpace, char sign){
		
		colony = new ArrayList<Ant>();
		
		this.sign = sign;
		
		this.treeSearchSpace = treeSearchSpace;
		
		for(int i=0; i<size; i++){
			colony.add(new Ant(i, treeSearchSpace, sign));
			colony.get(i).computeFitness();
		}
		
	}
	
	public void advanceAnts(){
		
		for(int i=0; i<colony.size(); i++){
			
			colony.get(i).updateTour(treeSearchSpace);
			
		}
		
	}
	
	public void computeTou(){
		
		ArrayList<State> tempStateList = new ArrayList<State>();
		tempStateList.add(treeSearchSpace.root());
		
		int count = 0;
		Boolean allLevelFull = false;
		
		try{
			
			while (!allLevelFull) {
				count++;
				ArrayList<State> tempList = new ArrayList<State>();

				for (int j = 0; j < tempStateList.size(); j++) {

					ArrayList<State> childrenNodes = new ArrayList<State>();
				
					childrenNodes.addAll(treeSearchSpace.children(tempStateList.get(j)));

					for (int i = 0; i < childrenNodes.size(); i++){
						
						double tou = 0;
						for(int k=0; k<colony.size(); k++){
							
							if(colony.get(k).haveYouVisited(tempStateList.get(j), childrenNodes.get(i))){
								tou += 1/(double)colony.get(k).getFitness();
							}
							
						}
						
						childrenNodes.get(i).setTou(tou);
						
						if(childrenNodes.get(i).isMyWinningState(sign)){
							childrenNodes.get(i).setWeight(2);
			    		}
			    		else if(childrenNodes.get(i).isMyLosingState(sign)){
			    			childrenNodes.get(i).setWeight(40);
			    		}
			    		else if(childrenNodes.get(i).isDraw(sign))
			    			childrenNodes.get(i).setWeight(4);
			    		else if(childrenNodes.get(i).isStateGoodForOpponentWinning(sign))
			    			childrenNodes.get(i).setWeight(1);
			    		else
			    			childrenNodes.get(i).setWeight(3);
						
						
					}

					tempList.addAll(childrenNodes);

				}

				tempStateList.clear();

				for (int i = 0; i < tempList.size(); i++)
					tempStateList.add(tempList.get(i));

				tempList.clear();
				
				int sumOfFull = 0;
				for(int i=0; i<tempStateList.size(); i++)
					if(tempStateList.get(i).isFull())
						sumOfFull++;
				
				if(sumOfFull == tempStateList.size())
					allLevelFull = true;
				
			}
			
		}catch(NodeNotFoundException e){}
	}
	
	public Ant getBestAntForMove(){
		
		int minimumFitness = 100000;
		int indexMinima = -1;
		for(int i=0; i<colony.size(); i++)
		{
			if(colony.get(i).amIWinningAnt() && colony.get(i).fitness < minimumFitness){
				minimumFitness = colony.get(i).fitness;
				indexMinima = i; 
			}
		}
		
		if(indexMinima > -1)
			return colony.get(indexMinima);
		
		for(int i=0; i<colony.size(); i++)
		{
			if(colony.get(i).amIDrawAnt())
				return colony.get(i); 
		}
		
		return colony.get(0);
		
	}
	
	public String toString(){
		String result = "Colony: \n";
		
		for(int i=0; i<colony.size(); i++)
			result += colony.get(i).toString() +"\n";
		
		return result;
	}
}
