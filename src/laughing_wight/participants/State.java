package laughing_wight.participants;

import laughing_wight.model.Card;
import laughing_wight.model.Round;

public class State {
	private int pot;
	private Card flop1, flop2, flop3;
	private Card turn;
	private Card river;
	private Round round;
	
	public State(){
		pot = 0;
		round = Round.PRE_FLOP;
	}
	
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

	public void setRound(Round round) {
		this.round = round;
	}

	
	
}
