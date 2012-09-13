package laughing_wight.participants;

import laughing_wight.model.Card;

public class State {
	private int pot;
	private Card flop1, flop2, flop3;
	private Card turn;
	private Card river;
	
	public int getPot() {
		return pot;
	}
	
	public Card[] getCommunalCards(){
		return new Card[]{flop1,flop2,flop3,turn,river};
	}

}
