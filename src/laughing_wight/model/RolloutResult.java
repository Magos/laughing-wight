package laughing_wight.model;

import java.io.Serializable;

public class RolloutResult implements Serializable{
	private static final long serialVersionUID = 6339862034904119706L;
	
	private double[][][] suited;
	private double[][][] unSuited;
	
	public RolloutResult(double[][][] suited, double[][][] unSuited){
		this.suited = suited;
		this.unSuited = unSuited;
	}
	
	/** Get a win ratio of a player with these hole cards for this table size, averaged over many rounds. */
	public double winRatio(int tableSize, Card holeCard1, Card holeCard2){
		if(tableSize > 10 || tableSize < 2 || holeCard1 == null || holeCard2 == null) return -1;
		double[][][] applicable = (holeCard1.getSuit() == holeCard2.getSuit() ? suited : unSuited);
		return applicable[tableSize-2][holeCard1.getValue()-2][holeCard2.getValue()-2];
	}
}
