package laughing_wight.participants;

import laughing_wight.model.Card;

public class Player {
	protected Card holeCard1, holeCard2;
	private Strategy strategy;
	
	public Player(){
	}
	
	public void dealHoleCards(Card holecard1, Card holecard2){
		this.holeCard1 = holecard1;
		this.holeCard2 = holecard2;
	}
	
	public void setStrategy(Strategy strategy){
		this.strategy = strategy;
	}
	
	public Action getAction(GameState state){
		if(strategy == null){
			return Action.FOLD; //Players with no strategy default to folding ASAP.
		}else{			
			return strategy.selectAction(state);
		}
	}

	public void reset() {
		holeCard1 = null;
		holeCard2 = null;
		strategy.reset();
	}

}
