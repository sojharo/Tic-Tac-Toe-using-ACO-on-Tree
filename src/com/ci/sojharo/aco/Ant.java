package com.ci.sojharo.aco;

import java.util.ArrayList;
import java.util.Random;

import com.gaurav.tree.NodeNotFoundException;
import com.gaurav.tree.Tree;
import com.iba.edu.ci.State;

public class Ant{
    
    
    int antId;
    ArrayList<State> tour;
    
    Tree<State> treeSearchSpace;
    
    char mySign;
    
    double alpha;
    double beta;
    
    int fitness;
    
    public Ant(int id, Tree<State> treeSearchSpace, char sign){
        
    	tour = new ArrayList<State>();
    	
    	alpha = beta = 0.8;
    	
    	mySign = sign;
    	
    	this.treeSearchSpace = treeSearchSpace;
    	 
        this.antId = id;
       
        State root = (State) treeSearchSpace.root();
        tour.add(root);
        
        try{
        	
	        while(treeSearchSpace.children(root).size() > 0){
	    		
	        	ArrayList<State> children = new ArrayList<State>();
	    		
	    		children.addAll(treeSearchSpace.children(root));
	    		
	    		State randomChild = children.get(new Random().nextInt(children.size())); 
	    		
	    		tour.add(randomChild);
	    		
	    		root = randomChild;	
	        	
	        }
	        
        }catch(NodeNotFoundException e){}
        
    }
    
    
    public int computeFitness(){
    	
    	int length = 0;
    	
    	for(int i=0; i<tour.size()-1; i++){
    		if(tour.get(i+1).isMyWinningState(mySign)){
    			length += 2;
    		}
    		else if(tour.get(i+1).isMyLosingState(mySign)){
    			length += 40;
    		}
    		else if(tour.get(i+1).isDraw(mySign))
    			length += 4;
    		else if(tour.get(i+1).isStateGoodForOpponentWinning(mySign))
    			length += 1;
    		else
    			length += 3;
    	}
    	
    	this.fitness = length;
    	return length;
    	
    }
    
    public int getFitness(){
    	return fitness;
    }
    
    public Boolean haveYouVisited(State city1, State city2){
    	
    	for(int k=0; k<tour.size()-1; k++){
    		if((tour.get(k).getId().equals(city1.getId()) && tour.get(k+1).getId().equals(city2.getId()))
    				|| (tour.get(k).getId().equals(city2.getId()) && tour.get(k+1).getId().equals(city1.getId())))
    			
    			return true;
    	}
    	
    	return false;
    }
    
    
    public void updateTour(Tree<State> treeWithTou){
    	
    	ArrayList<State> tabuList = new ArrayList<State>();
    	
    	tabuList.add(tour.get(0));
    	
    	for(int i=0; i<tabuList.size(); i++){
    	
    		//tabuList.add(tour[i]); // tabu list
    		
    		State currentCity = tabuList.get(i);
    		
    		double sumOfDenominator = 0;
    		
    		ArrayList<State> childrenNodes = new ArrayList<State>();
			
    		try{
    			childrenNodes.addAll(treeSearchSpace.children(currentCity));
    		}catch(NodeNotFoundException e){
    			System.out.println("EXCEPTION: "+ e);
    		}
    		
    		for(int j=0; j<childrenNodes.size(); j++){
    			//if(!tabuList.contains(childrenNodes.get(j))){
    				try{
	    				sumOfDenominator += Math.pow(childrenNodes.get(j).getTou(), alpha) * 
	    						Math.pow(1.0/(double)childrenNodes.get(j).getWeight(), beta);
    				}
    				catch(ArithmeticException e){
    					sumOfDenominator += 0;
    				}
    			//}
    		}
    		
    		//System.out.println("Ant "+ this.antId +" current city: "+ currentCity +" denom:" + sumOfDenominator);
    		
    		ArrayList<Double> P = new ArrayList<Double>();
    		ArrayList<Integer> P_CityNumber = new ArrayList<Integer>();    		
    		
    		for(int j=0; j<childrenNodes.size(); j++){
    			//if(!tabuList.contains(childrenNodes.get(j))){
    				try{
    					double toww = childrenNodes.get(j).getTou();
    					double weighttt = (double) childrenNodes.get(j).getWeight();
	    				P.add((Math.pow(childrenNodes.get(j).getTou(), alpha) * 
	    						Math.pow(1.0/(double)childrenNodes.get(j).getWeight(), beta))/sumOfDenominator);
	    				P_CityNumber.add(j);
    				}catch(ArithmeticException e){
    					P.add(0.0);
	    				P_CityNumber.add(j);
    				}
    			//}
    		}
    		
    		double probabilitySum= 0.0;
    	    for(int g=0; g<P.size(); g++){
    	    	probabilitySum += P.get(g);
    	    }
    	    
    	    ArrayList<Double> normalizedP = new ArrayList<Double>();
    	    for(int g=0; g<P.size(); g++){
    	      normalizedP.add(P.get(g)/probabilitySum);
    	    }
    	    
    	    ArrayList<Double> lowerBound = new ArrayList<Double>();
    	    ArrayList<Double> upperBound = new ArrayList<Double>();
    	    for(int g=0; g<normalizedP.size(); g++){
    	      if(g==0){
    	    	  lowerBound.add(0.0);
    	    	  upperBound.add(0.0 + normalizedP.get(g));
    	      }
    	      else{
    	    	  lowerBound.add(upperBound.get(g-1));
    	    	  upperBound.add(upperBound.get(g-1) + normalizedP.get(g));
    	      }
    	      
    	    }
    	    
    	    Double rand = new Random().nextDouble();
    	    for(int g=0; g<normalizedP.size(); g++){
    	    	if(rand >= lowerBound.get(g) && rand < upperBound.get(g)){
    	    		tabuList.add(childrenNodes.get(P_CityNumber.get(g)));
    	    		//System.out.println("Ant : "+ this.antId +" Next City is "+ tour[P_CityNumber.get(g)]);
    	    	}
    	    }
    	    
    	}
    	
    	/*for(int i=0; i<tour.size(); i++){
    		System.out.println(tour.get(i).toString());
		}*/
    	
    	tour.clear();
    	
    	for(int i=0; i<tabuList.size(); i++){
    		tour.add(tabuList.get(i));
    	}
    	
    	//System.out.println("*****************************************************************************");
    	
    	/*for(int i=0; i<tabuList.size(); i++){
    		System.out.println(tabuList.get(i).toString());
		}*/
    	
    	this.computeFitness();
    
    	//System.out.println("new fitness "+ this.computeFitness());
    
    }
    
    public Boolean amIWinningAnt(){
    	return tour.get(tour.size()-1).isMyWinningState(mySign);
    }
    
    public Boolean amIDrawAnt(){
    	return tour.get(tour.size()-1).isDraw(mySign);
    }
    
    public State getNextStateFromRoot(){
    	try{
    		return tour.get(1);
    	}catch(IndexOutOfBoundsException e){
    		return tour.get(0);
    	}
    }
    
    public String toString(){
    	
    	String result = "";
    	
    	/*for(int i=0; i<tour.size(); i++){
    		result += tour.get(i).toString();
    	}*/
    	
        return result +"Ant "+ this.antId +" has fitness: "+ this.computeFitness() +" and the winning is "+ 
        tour.get(tour.size()-1).isMyWinningState(mySign);
    }
}