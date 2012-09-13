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
	
	void dealFlop(Card flop1, Card flop2, Card flop3){
		this.flop1 = flop1;
		this.flop2 = flop2;
		this.flop3 = flop3;
	}
	
	void dealTurn(Card turn){
		this.turn = turn;
	}
	
	void dealRiver(Card river){
		this.river = river;
	}
	
	public Card[] getCommunalCards(){
		return new Card[]{flop1,flop2,flop3,turn,river};
	}

	
}
