package laughing_wight.participants;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.Card;
import laughing_wight.model.Hand;
import laughing_wight.model.Round;

public class ThresholdPlayer extends PhaseIIPlayer {
	private static final double CALL_THRESHOLD_MEAN = 0.15;
	private static final double BET_THRESHOLD_MEAN = 0.6;
	private static final double VARIANCE = 0.1;
	
	private double CALL_THRESHOLD;
	private double BET_THRESHOLD;
	private static Logger logger = LoggerFactory.getLogger(ThresholdPlayer.class);

	public ThresholdPlayer(String name) {
		super(name);
		double scaleFactor = getScaleFactor();
		CALL_THRESHOLD = CALL_THRESHOLD_MEAN + VARIANCE*scaleFactor;
		scaleFactor = getScaleFactor();
		BET_THRESHOLD = BET_THRESHOLD_MEAN + VARIANCE*scaleFactor;
		logger.info("{} chose bet threshold {} and call threshold {}.", this,BET_THRESHOLD,CALL_THRESHOLD);
		
	}

	private double getScaleFactor() {
		Random random = new Random();
		double scaleFactor = random.nextGaussian();
		while(scaleFactor > 1){scaleFactor -= 1;}
		while(scaleFactor < 0){scaleFactor += 1;}
		return scaleFactor;
	}

	@Override
	public Action getAction(GameState state) {
		if(state.getRound() == Round.PRE_FLOP){
			double prob = rollout.winRatio(state.getActivePlayerCount(), holeCard1, holeCard2);
			if(prob > BET_THRESHOLD){
				return Action.BET;
			}else if(prob > CALL_THRESHOLD){
				return Action.CALL;
			}else{
				return Action.FOLD;
			}
		}
		Card[] hole = new Card[]{holeCard1,holeCard2};
		Card[] communalCards = state.getCommunalCards();
		Hand myHand = Hand.getBestHand(hole, communalCards);
		double handStrength = HandStrengthCalculator.calculateHandStrength(hole,communalCards, myHand, state.getActivePlayerCount());
		logger.trace("Hand strength with hand {} was {}",myHand,handStrength);
		if(handStrength > BET_THRESHOLD){
			return Action.BET;
		}else if(handStrength > CALL_THRESHOLD){
			return Action.CALL;
		}else{
			return Action.FOLD;
		}
	}

}
