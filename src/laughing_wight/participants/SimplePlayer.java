package laughing_wight.participants;

import java.util.Random;

import laughing_wight.model.Card;
import laughing_wight.model.Hand;
import laughing_wight.model.HandType;
import laughing_wight.model.Round;

/** A simplistic player which only bets on certain kinds of hand or better.*/
public class SimplePlayer extends Player {
	private static HandType MINIMUM_CALL = HandType.PAIR;
	private static HandType MINIMUM_BET = HandType.TWO_PAIR;
	private Random random;

	public SimplePlayer(String name){
		super(name);
		random = new Random();
	}
	
	@Override
	public Action getAction(GameState state) {
		if(state.getActivePlayerCount()==1){
			return Action.CALL;
		}
		if(state.getRound() == Round.PRE_FLOP){
			if(random.nextFloat() >= 0.5f){
				return Action.CALL;
			}else{
				return Action.FOLD;
			}
		}
		else{
			Hand best = Hand.getBestHand(new Card[]{holeCard1,holeCard2}, state.getCommunalCards());
			if(best.getType().ordinal() >= MINIMUM_BET.ordinal()){
				return Action.BET;
			}else if(best.getType().ordinal() >= MINIMUM_CALL.ordinal()){
				return Action.CALL;
			}else{
				return Action.FOLD;
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	

}
