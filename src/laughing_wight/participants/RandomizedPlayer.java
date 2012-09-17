package laughing_wight.participants;

import java.util.Random;

import laughing_wight.model.Card;
import laughing_wight.model.Hand;
import laughing_wight.model.Round;

/** A phase II player using hand strengths and */
public class RandomizedPlayer extends PhaseIIPlayer {
	private Random random;

	public RandomizedPlayer(String name) {
		super(name);
		random = new Random();
	}

	@Override
	public Action getAction(GameState state) {
		if(state.getRound() == Round.PRE_FLOP){
			double prob = rollout.winRatio(state.getActivePlayerCount(), holeCard1, holeCard2);
			if(prob >= random.nextDouble()){
				return Action.BET;
			}else{
				return Action.FOLD;
			}
		}
		Card[] hole = new Card[]{holeCard1,holeCard2};
		Card[] communalCards = state.getCommunalCards();
		Hand myHand = Hand.getBestHand(hole, communalCards);
		double handStrength = HandStrengthCalculator.calculateHandStrength(hole,communalCards, myHand, state.getActivePlayerCount());
		if(handStrength >= random.nextDouble()){
			return Action.BET;
		}else{
			return Action.FOLD;
		}
	}

}
