package laughing_wight.participants;

import laughing_wight.model.Card;

public abstract class Player {
	protected Card holeCard1, holeCard2;
	private String name;
	
	public Player(String name){
		this.name = name;
	}
	
	public void dealHoleCards(Card holecard1, Card holecard2){
		this.holeCard1 = holecard1;
		this.holeCard2 = holecard2;
	}
	
	
	public abstract Action getAction(GameState state);

	public void reset() {
		holeCard1 = null;
		holeCard2 = null;
	}

	@Override
	public String toString() {
		return name;
	}
}
