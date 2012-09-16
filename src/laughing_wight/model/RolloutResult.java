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
}
