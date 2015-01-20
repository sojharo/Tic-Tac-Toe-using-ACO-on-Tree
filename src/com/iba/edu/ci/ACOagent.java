package com.iba.edu.ci;
import java.util.ArrayList;
import java.util.Random;

import com.ci.sojharo.aco.Colony;
import com.gaurav.tree.ArrayListTree;
import com.gaurav.tree.NodeNotFoundException;
import com.gaurav.tree.Tree;


public class ACOagent extends Agent {
	
	public static final char SYMBOL_X = 'X';
	public static final char SYMBOL_O = 'O';
	
	
	public ACOagent(char sign){
	
		
	}
	
		
	// Given a current game state, pick a move.
	public State pickMove(State state){
		
		Tree<State> tree = new ArrayListTree<State>();

		try {

			tree.add(new State(state));

			ArrayList<State> tempStateList = new ArrayList<State>();
			tempStateList.add(tree.root());
			int count = 0;
			
			Boolean allLevelFull = false;
			
			while (!allLevelFull) {
				count++;

				ArrayList<State> tempList = new ArrayList<State>();

				for (int j = 0; j < tempStateList.size(); j++) {

					ArrayList<State> childrenNodes;
					
					if (!tempStateList.get(j).isWinningState()) {
						
						if (count % 2 == 0)
							childrenNodes = tempStateList.get(j).getNextstate(
									SYMBOL_X);
						else
							childrenNodes = tempStateList.get(j).getNextstate(
									SYMBOL_O);

						// System.out.println("Children are "+
						// childrenNodes.size() +" at level "+ count);

						for (int i = 0; i < childrenNodes.size(); i++)
							tree.add(tempStateList.get(j), childrenNodes.get(i));

						tempList.addAll(childrenNodes);
					}

				}

				tempStateList.clear();

				for (int i = 0; i < tempList.size(); i++)
					tempStateList.add(tempList.get(i));

				tempList.clear();

				//System.out.println(tempStateList.get(0).isFull());
				//System.out.println(tempStateList.get(0).toString());
				
				int sumOfFull = 0;
				for(int i=0; i<tempStateList.size(); i++)
					if(tempStateList.get(i).isFull())
						sumOfFull++;
				
				if(sumOfFull == tempStateList.size())
					allLevelFull = true;
						
			}
			
			//System.out.println("TREE SIZE "+ tree.size());
			//System.out.println(tree.leaves());         // Show the leaves of the Tree
			Colony colony = new Colony(20, tree, SYMBOL_O);
			//System.out.println(colony.toString());
			
			for(int i=0; i<7; i++){
	        	
		        colony.computeTou();
		        
		        colony.advanceAnts();
		        
		        //System.out.println(colony.toString());
	        }
			
			//System.out.println(colony.treeSearchSpace);
			
			//System.out.println(ant.toString());
			//System.out.println("finish");
			
			//System.out.println(colony.getBestAntForMove().getNextStateFromRoot());
			return colony.getBestAntForMove().getNextStateFromRoot();
			
		} catch (NodeNotFoundException e) {
			System.out.println("EXCEPTION "+ e);
		}
		
		return null;
		
	}
}
